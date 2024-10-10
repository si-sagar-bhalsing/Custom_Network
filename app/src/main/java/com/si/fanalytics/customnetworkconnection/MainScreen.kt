package com.si.fanalytics.customnetworkconnection// MainScreen.kt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val networkObserver = remember { NetworkObserver(context) }

    // UI state to hold the current connectivity status
    val connectivityStatus = remember { mutableStateOf(ConnectivityStatus.DISCONNECTED) }

    // LaunchedEffect to start observing the network status
    LaunchedEffect(Unit) {
        networkObserver.startListening()

        // Collecting the connectivity status from the NetworkObserver
        networkObserver.connectivityStatus.collect { status ->
            connectivityStatus.value = status
        }
    }

    // DisposableEffect to clean up and stop observing when composable is removed
    DisposableEffect(Unit) {
        onDispose {
            networkObserver.stopListening()
        }
    }

    // Displaying connectivity status in the UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Connectivity Status: ${connectivityStatus.value}", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}
