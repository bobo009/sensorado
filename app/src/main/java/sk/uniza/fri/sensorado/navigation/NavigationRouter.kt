package sk.uniza.fri.sensorado.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import sk.uniza.fri.sensorado.app.cameras.CamerasScreen
import sk.uniza.fri.sensorado.app.cameras.detail.CameraDetailScreen
import sk.uniza.fri.sensorado.app.home.OverviewScreen
import sk.uniza.fri.sensorado.app.sensors.SensorsScreen
import sk.uniza.fri.sensorado.app.sensors.detail.SensorDetailScreen

/**
 * Creates navigation router.
 * @param navController object managing navigation
 */
@Composable
fun NavigationRouter(
    navController: NavHostController
) {
    NavHost(
        navController = navController, startDestination = Screen.Overview.route
    ) {
        composable(
            route = Screen.Overview.route
        ) {
            OverviewScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.Cameras.route
        ) {
            CamerasScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.CameraDetail.route
        ) {
            CameraDetailScreen()
        }
        composable(
            route = Screen.Sensors.route
        ) {
            SensorsScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.SensorDetail.route
        ) {
            SensorDetailScreen()
        }
    }
}
