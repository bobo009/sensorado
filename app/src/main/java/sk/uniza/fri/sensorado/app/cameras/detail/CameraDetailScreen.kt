package sk.uniza.fri.sensorado.app.cameras.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import sk.uniza.fri.sensorado.app.cameras.getBasicCameraInfo
import sk.uniza.fri.sensorado.assets.DetailButtonLazyColumn

/**
 * Creates camera detail root screen.
 */
@Composable
fun CameraDetailScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Scaffold(content = {
            Box(
                modifier = Modifier.padding(it)
            ) {
                CameraDetailScreenView()
            }
        })
    }
}

/**
 * Creates inner camera detail screen containing list of categorized camera information.
 */
@Composable
fun CameraDetailScreenView() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        DetailButtonLazyColumn(
            headline = currentCameraId?.let { getBasicCameraInfo(cameraId = it).label },
            list = generateCameraDetail()
        )
    }
}