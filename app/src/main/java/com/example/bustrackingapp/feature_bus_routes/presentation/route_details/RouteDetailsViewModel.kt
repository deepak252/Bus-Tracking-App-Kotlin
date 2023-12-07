package com.example.bustrackingapp.feature_bus_routes.presentation.route_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.core.domain.models.Bus
import com.example.bustrackingapp.core.domain.repository.UserPrefsRepository
import com.example.bustrackingapp.core.util.Constants
import com.example.bustrackingapp.core.util.GsonUtil
import com.example.bustrackingapp.core.util.LoggerUtil
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.core.util.SocketEvent
import com.example.bustrackingapp.core.util.SocketIOClient
import com.example.bustrackingapp.feature_bus.domain.use_cases.GetBusesForRouteUseCase
import com.example.bustrackingapp.feature_bus_routes.domain.use_case.GetBusRouteUseCase
import com.example.bustrackingapp.feature_bus_routes.presentation.bus_routes.BusRoutesUiState
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class RouteDetailsViewModel @Inject constructor(
    private val getBusRouteUseCase: GetBusRouteUseCase,
    private val userPrefsRepository: UserPrefsRepository,
) : ViewModel(){
    private val logger = LoggerUtil(c = "RouteDetailsViewModel")
    var uiState by mutableStateOf(RouteDetailsUiState())
        private set

    private var socket: Socket?=null

    init {
//        connectSocket()
    }

    fun connectSocket(routeNo : String) {
        if(socket?.connected()==true){
            logger.info("Already Connected to Socket",fxn = "connectSocket")
            return
        }
        viewModelScope.launch {
            try{
                if(socket==null){
                    val token = userPrefsRepository.getToken.first()
                    logger.info("token = $token","connectSocket")
                    if(token.isNotEmpty()){
                        socket = IO.socket("${Constants.socketBaseUrl}/user?token=$token")
                    }
                }
                socket?.connect()
                listenEvent(Socket.EVENT_CONNECT){
//                    logger.info("Socket Connected : $it","connectSocket")
                    emitEvent(
                        SocketEvent.User.joinRoute,
                        payload = mapOf(
                            "routeNo" to routeNo
                        )
                    )
                }
                listenEvent(SocketEvent.Bus.locationUpdated){ data->
                    val result = GsonUtil.parse<Bus>(data.toString())
                    val buses = uiState.buses.toMutableList()
                    val index = buses.indexOfFirst { bus ->
                        bus.vehNo == result.vehNo
                    }
                    if (index >= 0) {
                        val updatedBus = buses[index].copy(location = result.location)
                        buses[index] = updatedBus
                        uiState = uiState.copy(buses = buses)
                    }
                    logger.info("index = $index, uiState = ${uiState.buses}", "locationUpdated")

                }
                listenEvent(SocketEvent.Bus.routeUpdated){
                    val result = GsonUtil.parseList<Bus>(it.toString())
                    uiState = uiState.copy(buses = result)
                }
                listenEvent(SocketEvent.User.routeJoined){
                    val result = GsonUtil.parseList<Bus>(it.toString())
                    uiState = uiState.copy(buses = result.toList())
//                    logger.info("apiResponseList = $result", "routeJoined")
                }

                listenEvent(SocketEvent.error){

                }

                listenEvent(Socket.EVENT_DISCONNECT){
//                    logger.info("Socket Disconnected : $it","connectSocket")
                }
                listenEvent(Socket.EVENT_CONNECT_ERROR){
//                    logger.info("Socket Connection Failed!! : $it","connectSocket")
                }

            }catch (e : Exception){
                logger.error("Exception:  $e","connectSocket")
            }
        }
    }

    private fun disconnectSocket() {
        socket?.disconnect()
        socket?.off(SocketEvent.User.joinRoute)
        socket?.off(SocketEvent.User.routeJoined)
        socket?.off(SocketEvent.Bus.locationUpdated)
        socket?.off(SocketEvent.Bus.routeUpdated)
        socket?.off(SocketEvent.error)
    }

    private fun emitEvent(eventName: String, payload: Map<String, Any>  ) {
        if(socket==null){
            logger.error("Socket Not Initialized!!","emitEvent")
        }else{
            socket?.emit(eventName, JSONObject(payload))
            logger.info("Socket Emit Event : $eventName","emitEvent")
        }
    }

    private fun listenEvent(eventName: String, callBack : (Any)->Unit ){
        if(socket==null){
            logger.error("Socket Not Initialized!!","listenEvent")
        }else{
            socket?.on(eventName){
                try{
                    val message = if(it.isNotEmpty()){
                        it[0]
                    }else{
                        it
                    }
                    logger.info("OnEvent : $eventName, $message","listenEvent")
                    callBack(message)
                }catch (e : Exception){
                    logger.error("$e, {${e.stackTrace}}","listenEvent : $eventName")
                }
            }
            logger.info("Socket Listening to Event : $eventName","listenEvent")
        }
    }


    fun toggleScheduleBottomSheet(){
        uiState = uiState.copy(showScheduleBottomSheet = !uiState.showScheduleBottomSheet)
    }

    fun getBusRouteDetails(routeNo : String, isLoading : Boolean = false, isRefreshing : Boolean = false){
        if(uiState.isLoading || uiState.isRefreshing){
            return
        }
        getBusRouteUseCase(routeNo).onEach { result->
            uiState = when(result){
                is Resource.Success ->{
                    uiState.copy(route = result.data, isLoading = false, isRefreshing = false, error = null)
                }
                is Resource.Error ->{
                    uiState.copy(error = result.message, isLoading = false, isRefreshing = false)
                }
                is Resource.Loading ->{
                    uiState.copy(isLoading = isLoading, isRefreshing = isRefreshing, error = null)
                }
            }
        }
            .launchIn(viewModelScope)
    }


    override fun onCleared() {
        super.onCleared()
        socket?.disconnect()
//        disconnectSocket()
    }
}



//
//
//@HiltViewModel
//class RouteDetailsViewModel @Inject constructor(
//    private val getBusRouteUseCase: GetBusRouteUseCase,
//    private val userPrefsRepository: UserPrefsRepository,
//) : ViewModel(){
//    private val logger = LoggerUtil(c = "RouteDetailsViewModel")
//    var uiState by mutableStateOf(RouteDetailsUiState())
//        private set
//
//        private var socket: Socket?=null
//
//    init {
////        connectSocket()
//    }
//
//    fun connectSocket(routeNo : String) {
//        if(socket?.connected()==true){
//            logger.info("Already Connected to Socket",fxn = "connectSocket")
//            return
//        }
//        viewModelScope.launch {
//            try{
//                if(socket==null){
//                    val token = userPrefsRepository.getToken.first()
//                    logger.info("token = $token","connectSocket")
//                    if(token.isNotEmpty()){
//                        socket = IO.socket("${Constants.socketBaseUrl}/user?token=$token")
//                    }
//                }
//                socket?.connect()
//                listenEvent(Socket.EVENT_CONNECT){
////                    logger.info("Socket Connected : $it","connectSocket")
//                    emitEvent(
//                        SocketEvent.User.joinRoute,
//                        payload = mapOf(
//                            "routeNo" to routeNo
//                        )
//                    )
//                }
//                listenEvent(SocketEvent.Bus.locationUpdated){ data->
//                    val result = GsonUtil.parse<Bus>(data.toString())
//                    val buses = uiState.buses.toMutableList()
//                    val index = buses.indexOfFirst { bus ->
//                        bus.vehNo == result.vehNo
//                    }
//                    if (index >= 0) {
//                        val updatedBus = buses[index].copy(location = result.location)
//                        buses[index] = updatedBus
//                        uiState = uiState.copy(buses = buses)
//                    }
//                    logger.info("index = $index, uiState = ${uiState.buses}", "locationUpdated")
//
//                }
//                listenEvent(SocketEvent.Bus.routeUpdated){
//                    val result = GsonUtil.parseList<Bus>(it.toString())
//                    uiState = uiState.copy(buses = result)
//                }
//                listenEvent(SocketEvent.User.routeJoined){
//                    val result = GsonUtil.parseList<Bus>(it.toString())
//                    uiState = uiState.copy(buses = result.toList())
////                    logger.info("apiResponseList = $result", "routeJoined")
//                }
//
//                listenEvent(SocketEvent.error){
//
//                }
//
//                listenEvent(Socket.EVENT_DISCONNECT){
////                    logger.info("Socket Disconnected : $it","connectSocket")
//                }
//                listenEvent(Socket.EVENT_CONNECT_ERROR){
////                    logger.info("Socket Connection Failed!! : $it","connectSocket")
//                }
//
//            }catch (e : Exception){
//                logger.error("Exception:  $e","connectSocket")
//            }
//        }
//    }
//
//    private fun disconnectSocket() {
//        socket?.disconnect()
//        socket?.off(SocketEvent.User.joinRoute)
//        socket?.off(SocketEvent.User.routeJoined)
//        socket?.off(SocketEvent.Bus.locationUpdated)
//        socket?.off(SocketEvent.Bus.routeUpdated)
//        socket?.off(SocketEvent.error)
//    }
//
//    private fun emitEvent(eventName: String, payload: Map<String, Any>  ) {
//        if(socket==null){
//            logger.error("Socket Not Initialized!!","emitEvent")
//        }else{
//            socket?.emit(eventName, JSONObject(payload))
//            logger.info("Socket Emit Event : $eventName","emitEvent")
//        }
//    }
//
//    private fun listenEvent(eventName: String, callBack : (Any)->Unit ){
//        if(socket==null){
//            logger.error("Socket Not Initialized!!","listenEvent")
//        }else{
//            socket?.on(eventName){
//                try{
//                    val message = if(it.isNotEmpty()){
//                        it[0]
//                    }else{
//                        it
//                    }
//                    logger.info("OnEvent : $eventName, $message","listenEvent")
//                    callBack(message)
//                }catch (e : Exception){
//                    logger.error("$e, {${e.stackTrace}}","listenEvent : $eventName")
//                }
//            }
//            logger.info("Socket Listening to Event : $eventName","listenEvent")
//        }
//    }
//
//
//    fun toggleScheduleBottomSheet(){
//        uiState = uiState.copy(showScheduleBottomSheet = !uiState.showScheduleBottomSheet)
//    }
//
//    fun getBusRouteDetails(routeNo : String, isLoading : Boolean = false, isRefreshing : Boolean = false){
//        if(uiState.isLoading || uiState.isRefreshing){
//            return
//        }
//        getBusRouteUseCase(routeNo).onEach { result->
//            uiState = when(result){
//                is Resource.Success ->{
//                    uiState.copy(route = result.data, isLoading = false, isRefreshing = false, error = null)
//                }
//                is Resource.Error ->{
//                    uiState.copy(error = result.message, isLoading = false, isRefreshing = false)
//                }
//                is Resource.Loading ->{
//                    uiState.copy(isLoading = isLoading, isRefreshing = isRefreshing, error = null)
//                }
//            }
//        }
//            .launchIn(viewModelScope)
//    }
//
//
//    override fun onCleared() {
//        super.onCleared()
//        disconnectSocket()
//    }
//}
