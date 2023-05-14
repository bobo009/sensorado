package sk.uniza.fri.sensorado.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import sk.uniza.fri.sensorado.R

/**
 * Creates bottom navigation bar.
 * @param navController object managing navigation
 */
@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar {
        val bottomNavigationBarEntries = generateBottomNavigationBarEntries()
        bottomNavigationBarEntries.forEach { item ->
            val isSelected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(selected = isSelected, onClick = {
                navController.navigate(item.route) {
                    popUpTo(item.popUpTo) {
                        inclusive = item.popUpToInclusive
                    }
                }
            }, label = {
                Text(
                    text = stringResource(
                        id = item.labelResource
                    ), fontWeight = FontWeight.SemiBold
                )
            }, icon = {
                Icon(
                    imageVector = item.icon, contentDescription = "${
                        stringResource(
                            id = item.labelResource
                        )
                    } ${stringResource(id = R.string.icon_capitalized)}"
                )
            })
        }
    }
}

/**
 * Generates bottom navigation entries.
 * @return list of bottom navigation entries
 */
private fun generateBottomNavigationBarEntries(): List<BottomNavigationBarEntry> {
    return listOf(
        BottomNavigationBarEntry.Overview,
        BottomNavigationBarEntry.Cameras,
        BottomNavigationBarEntry.Sensors
    )
}

/**
 * Creates bottom navigation bar entries.
 * @param labelResource label ID of the string resource
 * @param route route to navigate to
 * @param icon icon to show
 * @param popUpTo location to pop up to when pressing the back button
 * @param popUpToInclusive whether to forget previous navigation steps
 */
sealed class BottomNavigationBarEntry(
    @StringRes val labelResource: Int,
    val route: String,
    val icon: ImageVector,
    val popUpTo: String,
    val popUpToInclusive: Boolean
) {
    object Overview : BottomNavigationBarEntry(
        labelResource = R.string.overview_capitalized,
        route = Screen.Overview.route,
        icon = Icons.Default.Equalizer,
        popUpTo = Screen.Overview.route,
        popUpToInclusive = true
    )

    object Cameras : BottomNavigationBarEntry(
        labelResource = R.string.cameras_capitalized,
        route = Screen.Cameras.route,
        icon = Icons.Default.Camera,
        popUpTo = Screen.Overview.route,
        popUpToInclusive = false
    )

    object Sensors : BottomNavigationBarEntry(
        labelResource = R.string.sensors_capitalized,
        route = Screen.Sensors.route,
        icon = Icons.Default.Sensors,
        popUpTo = Screen.Overview.route,
        popUpToInclusive = false
    )
}