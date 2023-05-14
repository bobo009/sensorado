package sk.uniza.fri.sensorado.app.cameras

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Portrait
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material.icons.outlined.FilterCenterFocus
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import sk.uniza.fri.sensorado.R
import sk.uniza.fri.sensorado.app.cameras.detail.currentCameraId
import sk.uniza.fri.sensorado.assets.DetailButtonModel
import sk.uniza.fri.sensorado.navigation.Screen

/**
 * Data model for basic camera information.
 */
data class BasicCameraInfo(
    val label: String,
    val facing: Int?,
    val type: String,
    val physicalIds: Set<String>,
    val icon: ImageVector
)

/**
 * Generates array of camera IDs in the device.
 * @return array of camera IDs
 */
@Composable
fun getCameraIds(): Array<out String> {
    val context = LocalContext.current
    val cameraManager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    return cameraManager.cameraIdList
}

/**
 * Returns number of cameras in the device.
 * @return number of cameras
 */
@Composable
fun getCameraCount(): Int {
    return getCameraIds().size
}

/**
 * Generates basic camera information for the camera specified by ID.
 * @param cameraId ID of the camera to get the information for
 * @return basic information about the camera
 */
@Composable
fun getBasicCameraInfo(cameraId: String): BasicCameraInfo {
    val context: Context = LocalContext.current
    val cameraManager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraFacing: Int? =
        cameraManager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.LENS_FACING)
    val cameraLabel: String = (when (cameraFacing) {
        CameraCharacteristics.LENS_FACING_FRONT -> stringResource(
            R.string.frontCamera_capitalized
        )

        CameraCharacteristics.LENS_FACING_BACK -> stringResource(
            R.string.backCamera_capitalized
        )

        CameraCharacteristics.LENS_FACING_EXTERNAL -> stringResource(
            R.string.externalCamera_capitalized
        )

        else -> stringResource(
            R.string.unknownCamera_capitalized
        )
    }) + " $cameraId"
    val cameraIcon: ImageVector = (when (cameraFacing) {
        CameraCharacteristics.LENS_FACING_FRONT -> Icons.Filled.Portrait
        CameraCharacteristics.LENS_FACING_BACK -> Icons.Outlined.Image
        CameraCharacteristics.LENS_FACING_EXTERNAL -> Icons.Outlined.FilterCenterFocus
        else -> Icons.Outlined.BrokenImage
    })
    val physicalCameraIds: Set<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        cameraManager.getCameraCharacteristics(cameraId).physicalCameraIds
    } else {
        setOf()
    }
    val cameraType: String =
        if (physicalCameraIds.isNotEmpty()) stringResource(R.string.logical_lowercase) else stringResource(
            R.string.physical_lowercase
        )
    return BasicCameraInfo(
        label = cameraLabel,
        facing = cameraFacing,
        type = cameraType,
        physicalIds = physicalCameraIds,
        icon = cameraIcon
    )
}

/**
 * Generates list of cameras ready to be used with custom detail button.
 * @param navController object managing navigation
 * @return list of cameras for custom detail button
 */
@Composable
fun generateCameraModels(
    navController: NavHostController
): List<DetailButtonModel> {
    val cameraList: MutableList<DetailButtonModel> = mutableListOf()
    val cameraIds = getCameraIds()
    for (cameraIndex in 0..cameraIds.max().toInt()) {
        val cameraId: String = cameraIndex.toString()
        val cameraInfo: BasicCameraInfo = getBasicCameraInfo(cameraId = cameraId)
        val dataValues: MutableList<String> = mutableListOf(
            "Type" + ": " + cameraInfo.type
        )
        if (cameraInfo.physicalIds.isNotEmpty()) {
            dataValues.add(
                stringResource(
                    R.string.physicalIds_capitalized
                ) + ": " + cameraInfo.physicalIds.toString().drop(1).dropLast(1)
            )
        }
        cameraList.add(DetailButtonModel(
            headline = cameraInfo.label, icon = cameraInfo.icon, dataValues = dataValues
        ) {
            currentCameraId = cameraId
            navController.navigate(Screen.CameraDetail.route)
        })
    }
    return cameraList
}