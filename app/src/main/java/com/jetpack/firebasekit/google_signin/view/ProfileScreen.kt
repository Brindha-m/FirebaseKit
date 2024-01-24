package com.jetpack.firebasekit.google_signin.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.jetpack.firebasekit.google_signin.data.AuthState
import com.jetpack.firebasekit.google_signin.data.DataProvider
import com.jetpack.firebasekit.google_signin.viewmodel.GoogleAuthViewModel

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    authViewModel: GoogleAuthViewModel = hiltViewModel()
) {
    val openLoginDialog = remember { mutableStateOf(false) }
    val authState = DataProvider.authState

    Scaffold(
        topBar = { HomeScreenTopBar() },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(10.dp)
                .fillMaxSize()
                .wrapContentSize(Alignment.TopCenter),
            Arrangement.spacedBy(6.dp),
            Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
//                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (authState == AuthState.SignedIn) {

                        if (DataProvider.user?.photoUrl != null) {
                            AsyncImage(
                                model = DataProvider.user!!.photoUrl.toString(),
                                contentDescription = "Profile picture",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = DataProvider.user?.displayName ?: "Name Placeholder",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary

                        )
                        Text(
                            text = DataProvider.user?.email ?: "Email Placeholder",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )


                    } else {
                        Text(
                            "Sign-in to view data!",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (authState != AuthState.SignedIn)
                        openLoginDialog.value = true
                    else
                        authViewModel.signOut()
                },
                modifier = Modifier
                    .size(width = 200.dp, height = 50.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Text(
                    text = if (authState != AuthState.SignedIn) "Sign in" else "Sign out",
                    modifier = Modifier.padding(6.dp),
                    color = MaterialTheme.colorScheme.background
                )
            }
        }

        AnimatedVisibility(visible = openLoginDialog.value) {
            Dialog(
                onDismissRequest = { openLoginDialog.value = false },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false // experimental
                )
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen(navHostController, authViewModel, openLoginDialog)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar() {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(top = 20.dp),
        title = {
            Text(
                "Your Profile",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}
