package sk.uniza.fri.sensorado.app.sensors

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
 * Creates sensors root screen.
 * @param navController object managing navigation
 */
@Composable
fun SensorsScreen(
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
                SensorsScreenView(
                    navController = navController
                )
            }
        })
    }
}

/**
 * Creates sensors inner screen containing list of sensors with basic information.
 * @param navController object managing navigation
 */
@Composable
fun SensorsScreenView(
    navController: NavHostController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        DetailButtonLazyColumn(
            headline = stringResource(
                id = R.string.sensors_capitalized
            ), list = generateSensorModels(
                navController = navController
            )
        )
    }
}