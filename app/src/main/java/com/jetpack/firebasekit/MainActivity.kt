package com.jetpack.firebasekit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetpack.firebasekit.google_signin.data.AuthState
import com.jetpack.firebasekit.google_signin.data.DataProvider
import com.jetpack.firebasekit.google_signin.di.wrapper.RemoteConfigWrapper
import com.jetpack.firebasekit.google_signin.view.LoginScreen
import com.jetpack.firebasekit.google_signin.view.navigation.MainBottomScreen
import com.jetpack.firebasekit.google_signin.view.navigation.Screens
import com.jetpack.firebasekit.google_signin.viewmodel.GoogleAuthViewModel
import com.jetpack.firebasekit.ui.theme.FirebaseKitTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var remoteConfigWrapper: RemoteConfigWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FirebaseKitTheme {
                remoteConfigWrapper.initRemoteConfig()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(remoteConfigWrapper)

                }
            }
        }
    }
}



@Composable
fun MainScreen(
    remoteConfigWrapper: RemoteConfigWrapper,
    authViewModel: GoogleAuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val currentUser = authViewModel.currentUser.collectAsState().value
    DataProvider.updateAuthState(currentUser)

    NavHost(
        startDestination = Screens.LoginScreen.route,
        navController = navController
    ) {

        composable(Screens.LoginScreen.route) {
            if (DataProvider.authState != AuthState.SignedOut) {
                MainBottomScreen(mainNavController = navController, remoteConfigWrapper)
            } else {
                LoginScreen(navHostController = navController, authViewModel)
            }
        }
    }
}