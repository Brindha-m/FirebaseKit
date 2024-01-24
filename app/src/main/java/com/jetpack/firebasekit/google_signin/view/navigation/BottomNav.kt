package com.jetpack.firebasekit.google_signin.view.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import com.jetpack.firebasekit.R
import com.jetpack.firebasekit.google_signin.view.HomeScreen
import com.jetpack.firebasekit.google_signin.view.ProfileScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainBottomScreen(
    mainNavController: NavHostController
) {
    val navigationBarItems = remember {
        NavigationBarItems.values()
    }

    // by default it will start with the spaces in bottom nav - value -> 1
    var selectedIndex by remember {
        mutableIntStateOf(1)
    }


    Scaffold(
        modifier = Modifier.padding(7.dp),
        bottomBar = {
            AnimatedNavigationBar(
                modifier = Modifier.height(60.dp),
                selectedIndex = selectedIndex,
                cornerRadius = shapeCornerRadius(cornerRadius = 30.dp),
                ballAnimation = Parabolic(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = Color(0xFF141413),
                ballColor = Color(0xFF67509F)
            ) {
                navigationBarItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable { selectedIndex = item.ordinal },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            painter = painterResource(id = item.icon),
                            contentDescription = "Bottom Bar",
                            tint = if (selectedIndex == item.ordinal) Color(0xFF7765A3)
                            else Color.LightGray
                        )
                    }
                }
            }
        }
    ) {
        // Display different content based on the selectedIndex
        when (navigationBarItems[selectedIndex]) {
            NavigationBarItems.Home -> HomeScreen()
            NavigationBarItems.Profile -> ProfileScreen(navHostController = mainNavController)
            else -> {}
        }
    }
}

enum class NavigationBarItems(val title: Int, val icon: Int, val route: String) {
    Home(R.string.Home, R.drawable.ic_home, Screens.HomeScreen.route),
    Profile(R.string.Profile, R.drawable.ic_profile, Screens.ProfileScreen.route),
}