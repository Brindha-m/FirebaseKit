package com.jetpack.firebasekit.google_signin.view.remoteConfig

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jetpack.firebasekit.google_signin.data.remoteconfig.EventModel
import com.jetpack.firebasekit.google_signin.view.component.LoadingAnimation
import com.jetpack.firebasekit.google_signin.viewmodel.remoteConfig.EventViewModel
import com.jetpack.firebasekit.google_signin.viewmodel.remoteConfig.event.EventFetchState

@Composable
fun EventScreen(eventViewModel: EventViewModel = hiltViewModel()) {
    val eventsState = eventViewModel.events.collectAsState()

    when (val events = eventsState.value) {
        is EventFetchState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(events.events) { event ->
                    EventItem(event = event)
                }
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }

        is EventFetchState.Error -> {
            // Show error message
            // events.errorMessage contains the error message
            Text(text = "Error: ${events.errorMessage}", modifier = Modifier.padding(16.dp))
        }

        else -> {
            // Show loading indicator or other UI based on your needs
            LoadingAnimation()
        }
    }
}

@Composable
fun EventItem(event: EventModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Display event image
            AsyncImage(
                model = event.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Fit
            )

            // Display event name
            Text(
                text = "${event.eventName}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Display event date
            Text(
                text = "${event.date}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Display event description
            Text(
                text = "${event.eventDescription}",
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
