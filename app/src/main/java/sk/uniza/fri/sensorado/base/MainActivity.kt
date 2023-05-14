package sk.uniza.fri.sensorado.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import sk.uniza.fri.sensorado.navigation.NavigationRouter
import sk.uniza.fri.sensorado.ui.theme.SensoradoTheme

/**
 * Main activity of the application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContent {
            SensoradoTheme {
                NavigationRouter(
                    navController = rememberNavController()
                )
            }
        }
    }
}
