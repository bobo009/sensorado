package sk.uniza.fri.sensorado.app.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import sk.uniza.fri.sensorado.R
import sk.uniza.fri.sensorado.app.cameras.getCameraCount
import sk.uniza.fri.sensorado.app.sensors.getSensorCount
import sk.uniza.fri.sensorado.navigation.BottomNavigationBar

/**
 * Creates overview root screen.
 * @param navController object managing navigation
 */
@Composable
fun OverviewScreen(
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
                HomeScreenView()
            }
        })
    }
}

/**
 * Creates overview inner screen containing counts of sensors and cameras in the device.
 */
@Composable
fun HomeScreenView() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val cameraCount = getCameraCount()
    val sensorCount = getSensorCount()
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        if (screenHeight > screenWidth) {
            Column {
                AppIcon(
                    modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    )
                )
                TotalSensorCount(
                    value = (cameraCount + sensorCount).toString(), modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    )
                )
                TotalSensorCountLabel(
                    value = cameraCount + sensorCount
                )
                Spacer(
                    modifier = Modifier.padding(vertical = 15.dp)
                )
                CategorizedSensorCountWithLabel(
                    values = listOf(cameraCount, sensorCount), modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    )
                )
            }
        } else {
            Row {
                AppIcon(
                    modifier = Modifier.align(
                        alignment = Alignment.CenterVertically
                    )
                )
                Spacer(
                    modifier = Modifier.padding(30.dp)
                )
                Column(
                    modifier = Modifier.align(
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    Row {
                        TotalSensorCount(
                            value = (cameraCount + sensorCount).toString(),
                            modifier = Modifier.align(
                                alignment = Alignment.CenterVertically
                            )
                        )
                        Spacer(
                            modifier = Modifier.padding(10.dp)
                        )
                        TotalSensorCountLabel(
                            value = cameraCount + sensorCount,
                        )
                    }
                    Spacer(
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                    CategorizedSensorCountWithLabel(
                        values = listOf(cameraCount, sensorCount), modifier = Modifier.align(
                            alignment = Alignment.CenterHorizontally
                        )
                    )
                }
            }
        }
    }
}

/**
 * Creates application icon.
 * @param modifier modifier to apply
 */
@Composable
fun AppIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.Sensors,
        contentDescription = stringResource(R.string.sensorsIcon_capitalized),
        modifier = modifier.size(150.dp)
    )
}

/**
 * Creates text with total sensor count.
 * @param value number of sensors
 * @param modifier modifier to apply
 */
@Composable
fun TotalSensorCount(
    value: String, modifier: Modifier = Modifier
) {
    Text(
        text = value, fontSize = 100.sp, fontWeight = FontWeight.Bold, modifier = modifier
    )
}

/**
 * Creates total sensor count label.
 * @param value number of sensors
 * @param modifier modifier to apply
 */
@Composable
fun TotalSensorCountLabel(
    value: Int, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = pluralStringResource(
                id = R.plurals.sensors_uppercase, value
            ), fontSize = 48.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(
                alignment = Alignment.CenterHorizontally
            )
        )
        Text(
            text = stringResource(
                id = R.string.found_uppercase
            ), fontSize = 66.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(
                alignment = Alignment.CenterHorizontally
            )
        )
    }
}

/**
 * Creates text with categorized sensor count with labels.
 * @param values number of sensors in categories
 * @param modifier modifier to apply
 */
@Composable
fun CategorizedSensorCountWithLabel(
    values: List<Int>, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = pluralStringResource(
                id = R.plurals.numberOfCameras_capitalized,
                if (values.isNotEmpty()) values[0] else 0,
                if (values.isNotEmpty()) values[0] else 0
            ),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(
                    alignment = Alignment.Start
                )
                .alpha(0.5f)
        )
        Text(
            text = pluralStringResource(
                id = R.plurals.numberOfOtherSensors_capitalized,
                if (values.size > 1) values[1] else 0,
                if (values.size > 1) values[1] else 0
            ),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(
                    alignment = Alignment.Start
                )
                .alpha(0.3f)
        )
    }
}