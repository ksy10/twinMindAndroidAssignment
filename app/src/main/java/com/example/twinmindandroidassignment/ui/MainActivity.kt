package com.example.twinmindandroidassignment.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.twinmindandroidassignment.ui.navigation.Routes
import com.example.twinmindandroidassignment.ui.screens.DashboardScreen
import com.example.twinmindandroidassignment.ui.screens.MeetingScreen
import com.example.twinmindandroidassignment.ui.screens.SummaryScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                AppNav()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.DASHBOARD
    ) {

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onMeetingClick = { meetingId ->
                    navController.navigate("${Routes.MEETING}/$meetingId")
                },
                onNewMeeting = {
                    navController.navigate(Routes.MEETING)
                }
            )
        }

        composable(
            route = "${Routes.MEETING}/{meetingId}",
            arguments = listOf(
                navArgument("meetingId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            MeetingScreen(
                onSummaryClick = { meetingId ->
                    navController.navigate("${Routes.SUMMARY}/$meetingId")
                }
            )
        }

        composable(
            route = "${Routes.SUMMARY}/{meetingId}",
            arguments = listOf(
                navArgument("meetingId") { type = NavType.StringType }
            )
        ) {
            SummaryScreen()
        }
    }
}