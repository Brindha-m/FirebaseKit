package com.jetpack.firebasekit.google_signin.view

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.jetpack.firebasekit.R
import com.jetpack.firebasekit.google_signin.data.AuthState
import com.jetpack.firebasekit.google_signin.data.DataProvider
import com.jetpack.firebasekit.google_signin.view.component.AnonymousSignIn
import com.jetpack.firebasekit.google_signin.view.component.GoogleSignIn
import com.jetpack.firebasekit.google_signin.view.component.OneTapSignIn
import com.jetpack.firebasekit.google_signin.view.component.SocialMediaLogin
import com.jetpack.firebasekit.google_signin.viewmodel.GoogleAuthViewModel
import com.jetpack.firebasekit.ui.theme.Black
import com.jetpack.firebasekit.ui.theme.FirebaseKitTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navHostController: NavHostController,
    authViewModel: GoogleAuthViewModel = hiltViewModel(),
    loginState: MutableState<Boolean>? = null
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credentials =
                        authViewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                    authViewModel.signInWithGoogle(credentials)
                } catch (e: ApiException) {
                    Log.e("LoginScreen:Launcher", "Login One-tap $e")
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Log.e("LoginScreen:Launcher", "OneTapClient Canceled")
            }
        }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        val imagePainter = if (isSystemInDarkTheme()) {
            painterResource(id = R.drawable.bgdark)
        } else {
            painterResource(id = R.drawable.bg)
        }

        /** Background Image */
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = imagePainter,
                contentDescription = "Main background Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(35.dp)
                .fillMaxSize()
                .wrapContentSize(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            val uiColor = if (isSystemInDarkTheme()) Color.White else Black
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Firebase Kit",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Exploring firebase features",
                fontSize = 14.sp,
                color = uiColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                modifier = Modifier
                    .size(155.dp)
                    .scale(2f),
                painter = painterResource(id = R.drawable.user_reg),
                contentDescription = "app_logo",
                contentScale = ContentScale.Fit,
            )

            Spacer(modifier = Modifier.height(35.dp))


            Text(
                text = "Continue With",
                style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF64748B))
            )


            // Social Media log in
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                SocialMediaLogin(
                    icon = R.drawable.google,
                    text = "Google",
                    modifier = Modifier
                        .weight(2f)

                ) {
                    // TODO: Sign in with google
                    authViewModel.oneTapSignIn()
                }
            }

            Spacer(modifier = Modifier.height(5.dp))


            if (DataProvider.authState == AuthState.SignedOut) {
                Button(
                    onClick = {
                        authViewModel.signInAnonymously()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .size(width = 125.dp, height = 50.dp)
                        .padding(horizontal = 6.dp),
                ) {
                    Text(
                        text = "Skip",
                        modifier = Modifier.padding(6.dp),
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }

        }

    }

    AnonymousSignIn()

    OneTapSignIn(
        launch = {
            launch(it)
        }
    )

    GoogleSignIn {
        // Dismiss LoginScreen
        loginState?.let {
            it.value = false
        }
    }
}


@Composable
fun learning() {
    Image(
        painter = painterResource(R.drawable.user_reg),
        contentDescription = "app_logo",
        modifier = Modifier
            .size(100.dp)
            .fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

}

@Preview
@Composable
fun LoginScreenPreview() {
    FirebaseKitTheme {
        learning()
    }
}