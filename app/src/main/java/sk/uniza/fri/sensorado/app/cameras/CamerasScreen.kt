package sk.uniza.fri.sensorado.app.cameras

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import sk.uniza.fri.sensorado.R
import sk.uniza.fri.sensorado.assets.DetailButtonLazyColumn
import sk.uniza.fri.sensorado.navigation.BottomNavigationBar

/**
 * Creates cameras root screen.
 * @param navController object managing navigation
 */
@Composable
fun CamerasScreen(
    navController: NavHostController
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Scaffold(bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }, content = {
            Box(
                modifier = Modifier.padding(it)
            ) {
                CamerasScreenView(
                    navController = navController
                )
            }
        })
    }
}

/**
 * Creates cameras inner screen containing list of cameras with basic information.
 * @param navController object managing navigation
 */
@Composable
fun CamerasScreenView(
    navController: NavHostController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        DetailButtonLazyColumn(
            headline = stringResource(
                id = R.string.cameras_capitalized
            ), list = generateCameraModels(
                navController = navController
            )
        )
    }
}