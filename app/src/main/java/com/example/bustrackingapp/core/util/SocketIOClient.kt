package com.example.bustrackingapp.core.util

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton


class SocketIOClient {

    private val logger = LoggerUtil(c = "SocketIOClient");
    private var socket: Socket?=null

    fun init(url : String){
        if(socket==null){
            socket = IO.socket(url)
        }
//        "${Constants.socketBaseUrl}/user?token=$token"
    }

    fun connect() = safeCall {
        socket?.connect()
    }

    fun disconnect() = safeCall {
        socket?.disconnect()
        socket?.close()
    }


    fun emit(eventName: String, payload: Map<String, Any> ) = safeCall {
        socket?.emit(eventName, JSONObject(payload))
        logger.info("Socket Emit Event : $eventName","emit")
    }

    fun on(eventName: String, callBack : (Any)->Unit ) = safeCall {
        socket?.on(eventName){
            try{
                val message = if(it.isNotEmpty()){
                    it[0]
                }else{
                    it
                }
                logger.info("OnEvent : $eventName, $message","listen")
                callBack(it)
            }catch(e : Exception){
                logger.error("$e, {${e.stackTrace}}","on : $eventName")
            }

        }
        logger.info("Socket Listening to Event : $eventName","on")
    }

    fun connected() : Boolean?{
        return socket?.connected()
    }


    private fun safeCall(callBack : ()->Unit){
        if(socket==null){
            throw Exception("Socket Not Initialized!!")
        }
        callBack()
    }


}
