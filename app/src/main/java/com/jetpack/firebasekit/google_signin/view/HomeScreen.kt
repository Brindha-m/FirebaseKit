package com.jetpack.firebasekit.google_signin.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.firebasekit.google_signin.di.wrapper.RemoteConfigWrapper
import com.jetpack.firebasekit.google_signin.view.remoteConfig.EventScreen
import com.jetpack.firebasekit.google_signin.view.remoteConfig.SampleRemoteConfigScreen

@Composable
fun HomeScreen(remoteConfigWrapper: RemoteConfigWrapper) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        HeaderText("Fetching a Simple string from Remote Config")
        Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(5.dp))
        SampleRemoteConfigScreen(remoteConfigWrapper)

        Spacer(modifier = Modifier.height(15.dp))

        HeaderText("Fetching Json from Remote Config")
        Divider(color = Color.LightGray, thickness = 2.dp)
        EventScreen()
    }
}

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(8.dp)
    )
}