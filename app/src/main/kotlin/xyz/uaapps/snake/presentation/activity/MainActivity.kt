package xyz.uaapps.snake.presentation.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.uaapps.snake.domain.base.BaseActivity
import xyz.uaapps.snake.domain.navigation.Screen
import xyz.uaapps.snake.presentation.screen.AboutScreen
import xyz.uaapps.snake.presentation.screen.HighScoreScreen
import xyz.uaapps.snake.presentation.screen.MenuScreen
import xyz.uaapps.snake.presentation.screen.SettingScreen

class MainActivity : BaseActivity() {
    private lateinit var navController: NavHostController

    @Composable
    override fun Content() {
        navController = rememberNavController()
        SetupNavigation()
    }

    @Composable
    private fun SetupNavigation() {
        NavHost(navController = navController, startDestination = Screen.Menu.route) {
            composable(Screen.Menu.route) { MenuScreen(navController) }
            composable(Screen.HighScores.route) { HighScoreScreen() }
            composable(Screen.Settings.route) { SettingScreen(navController) }
            composable(Screen.About.route) { AboutScreen() }
        }
    }
}
