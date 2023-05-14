package sk.uniza.fri.sensorado.app.sensors.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import sk.uniza.fri.sensorado.app.sensors.getBasicSensorInfo
import sk.uniza.fri.sensorado.assets.DetailButtonLazyColumn

/**
 * Creates sensor detail root screen.
 */
@Composable
fun SensorDetailScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Scaffold(content = {
            Box(
                modifier = Modifier.padding(it)
            ) {
                SensorDetailScreenView()
            }
        })
    }
}

/**
 * Creates inner sensor detail screen containing list of sensor data and categorized information.
 */
@Composable
fun SensorDetailScreenView() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        DetailButtonLazyColumn(
            headline = currentSensor?.let { getBasicSensorInfo(sensor = it).label },
            list = generateSensorDetail()
        )
    }
}