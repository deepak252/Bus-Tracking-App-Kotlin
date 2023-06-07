package com.example.bustrackingapp.feature_profile.presentation.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bustrackingapp.core.presentation.components.CustomElevatedButton
import com.example.bustrackingapp.core.presentation.components.CustomImage
import com.example.bustrackingapp.core.presentation.components.CustomLoadingIndicator
import com.example.bustrackingapp.ui.theme.Gray100
import com.example.bustrackingapp.ui.theme.NavyBlue500
import com.example.bustrackingapp.ui.theme.Red400
import com.example.bustrackingapp.ui.theme.White
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileViewModel : ProfileViewModel = hiltViewModel(),
    snackbarState : SnackbarHostState = remember {
        SnackbarHostState()
    }
){

    LaunchedEffect(key1 = profileViewModel.uiState.error){
        Log.d("BTLogger","showSnackbar")
        if(profileViewModel.uiState.error!=null){
            snackbarState.showSnackbar(profileViewModel.uiState.error!!)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profile",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState,
            ){
                if(profileViewModel.uiState.error!=null){
                    Snackbar(
                        snackbarData = it,
                        containerColor = Red400,
                        contentColor = White,

                    )
                }else{
                    Snackbar(snackbarData = it)
                }
            }
        },

        ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            ProfileContainer(
                name = { profileViewModel.uiState.user?.name },
                email = { profileViewModel.uiState.user?.email },
                phone = {profileViewModel.uiState.user?.phone },
                isLoading = {profileViewModel.uiState.isLoading},
                isRefreshing = {profileViewModel.uiState.isRefreshing},
                onRefresh = {profileViewModel.getUser(isRefreshing = true)},
                onLogOutClick = profileViewModel::onLogOutClick,
            )
        }
    }

}

@Composable
private fun ProfileContainer(
    modifier: Modifier = Modifier,
    name : ()->String?,
    email : ()->String?,
    phone : ()-> String?,
    isLoading : ()-> Boolean,
    isRefreshing : ()-> Boolean,
    onRefresh : ()-> Unit,
    onLogOutClick : ()->Unit = {},

){
    if(isLoading()){
        return CustomLoadingIndicator()
    }

    return SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing()),
        onRefresh = onRefresh,
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomImage(
                    imageVector = Icons.Default.Person,
                    color = Gray100,
                    backgroundColor = NavyBlue500
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column() {
                    Text(
                        text = name()?:"User",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = email()?:"xyz@gmail.com",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = phone()?:"9876543210",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            CustomElevatedButton(
                onClick = onLogOutClick,
                text = "Log Out"
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProfileContainerPreview(){
    ProfileContainer(
        name = { "Deepak" },
        email = { "deepak@gmail.com" },
        phone = { "9876543210" },
        isLoading = {false},
        isRefreshing = {false},
        onRefresh = {},
        modifier = Modifier.background(White),
    )
}