package com.jetpack.firebasekit.google_signin.view.remoteConfig

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.firebasekit.google_signin.di.wrapper.RemoteConfigWrapper

@Composable
fun SampleRemoteConfigScreen(remoteConfigWrapper: RemoteConfigWrapper) {

    var specialState by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(20.dp),

    ) {
        Text(
            text = specialState,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Button(onClick = {
            // Fetch and update the special state
            remoteConfigWrapper.fetchTodaySpecialState { newSpecialState ->
                specialState = newSpecialState
            }
        }) {
            Text("Fetch Today's Special State")
        }
    }
}
