package sk.uniza.fri.sensorado.app.sensors.detail

import android.hardware.Sensor
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.HistoryToggleOff
import androidx.compose.material.icons.filled.RunningWithErrors
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.res.stringResource
import sk.uniza.fri.sensorado.R
import sk.uniza.fri.sensorado.app.sensors.data.SIUnit
import sk.uniza.fri.sensorado.app.sensors.data.sensorInfo
import sk.uniza.fri.sensorado.assets.DetailButtonModel

/**
 * Currently selected sensor
 */
var currentSensor: Sensor? = null

/**
 * Current data of currently selected sensor
 */
var currentData: List<String> = listOf()

/**
 * Value containing information if the data rendered on the screen should be changed in real-time or paused.
 */
var dataUpdatePaused: Boolean by mutableStateOf(true)

/**
 * Data to render on the screen (current data if not paused or data from the time when the update was paused).
 */
var dataToShow: MutableState<List<String>> by mutableStateOf(mutableStateOf(listOf()))

/**
 * Generates list of data and categorized information from the sensor ready to be used with custom detail button.
 * @return list of data and categorized information from the sensor for custom detail button
 */
@Composable
fun generateSensorDetail(): List<DetailButtonModel> {
    val sensorDetailList: MutableList<DetailButtonModel> = mutableListOf()
    sensorDetailList.add(DetailButtonModel(headline = stringResource(R.string.realTimeData_capitalized),
        icon = if (dataToShow.value.isEmpty()) Icons.Default.RunningWithErrors else if (!dataUpdatePaused) Icons.Default.Schedule else Icons.Default.HistoryToggleOff,
        dataValues = dataToShow.value.ifEmpty { listOf(stringResource(R.string.noDataAvailable_capitalized)) }) {
        if (dataToShow.value.isNotEmpty()) {
            dataUpdatePaused = !dataUpdatePaused
            if (!dataUpdatePaused) {
                dataToShow = mutableStateOf(currentData)
            }
        }
    })
    sensorDetailList.add(
        DetailButtonModel(
            headline = stringResource(R.string.details_capitalized),
            icon = Icons.Outlined.Info,
            dataValues = listOf(
                stringResource(R.string.id_uppercase) + ": " + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) currentSensor?.id.toString() else stringResource(
                    R.string.unsupported_lowercase
                ),
                stringResource(R.string.name_capitalized) + ": " + currentSensor?.name.toString(),
                stringResource(R.string.vendor_capitalized) + ": " + currentSensor?.vendor.toString(),
                stringResource(R.string.type_capitalized) + ": " + currentSensor?.type.toString(),
                stringResource(R.string.version_capitalized) + ": " + currentSensor?.version.toString()
            ).toMutableStateList()
        )
    )
    sensorDetailList.add(
        DetailButtonModel(
            headline = stringResource(R.string.parameters_capitalized),
            icon = Icons.Outlined.Settings,
            dataValues = listOf(
                stringResource(R.string.delay_capitalized) + ": " + currentSensor?.minDelay.toString() + ".." + currentSensor?.maxDelay.toString() + SIUnit.Time.completeSuffix(),
                stringResource(R.string.maxRange_capitalized) + ": " + currentSensor?.maximumRange.toString() + (sensorInfo[currentSensor?.type]?.unit?.completeSuffix()
                    ?: ""),
                stringResource(R.string.resolution_capitalized) + ": " + currentSensor?.resolution.toString() + (sensorInfo[currentSensor?.type]?.unit?.completeSuffix()
                    ?: ""),
                stringResource(R.string.power_capitalized) + ": " + currentSensor?.power.toString() + SIUnit.Power.completeSuffix()
            ).toMutableStateList()
        )
    )
    sensorDetailList.add(
        DetailButtonModel(
            headline = stringResource(R.string.otherInformation_capitalized),
            icon = Icons.Default.HelpOutline,
            dataValues = listOf(
                stringResource(R.string.dynamicSensor_capitalized) + ": " + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) currentSensor?.isDynamicSensor.toString() else stringResource(
                    R.string.unsupported_lowercase
                ),
                stringResource(R.string.wakeUpSensor_capitalized) + ": " + currentSensor?.isWakeUpSensor.toString(),
                stringResource(R.string.reportingMode_capitalized) + ": " + currentSensor?.reportingMode.toString(),
                stringResource(R.string.highestDirectReportRateLevel_capitalized) + ": " + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) currentSensor?.highestDirectReportRateLevel.toString() else stringResource(
                    R.string.unsupported_lowercase
                ),
                stringResource(R.string.additionalInfoApi_other) + ": " + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && currentSensor?.isAdditionalInfoSupported == true) stringResource(
                    R.string.supported_lowercase
                ) else stringResource(R.string.unsupported_lowercase)
            ).toMutableStateList()
        )
    )
    sensorDetailList.add(
        DetailButtonModel(
            headline = stringResource(R.string.fifo_capitalized),
            icon = Icons.Outlined.Category,
            dataValues = listOf(
                stringResource(R.string.maxEventCount_capitalized) + ": " + currentSensor?.fifoMaxEventCount.toString(),
                stringResource(R.string.reservedEventCount_capitalized) + ": " + currentSensor?.fifoReservedEventCount.toString()
            ).toMutableStateList()
        )
    )
    val directChannelTypes: List<String> = listOf("0", "1 (MemoryFile)", "2 (HardwareBuffer)")
    var supportedDirectChannelType = String()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        for (index in directChannelTypes.indices) {
            if (currentSensor?.isDirectChannelTypeSupported(index) == true) {
                supportedDirectChannelType += (if (supportedDirectChannelType != "") ", " else "") + directChannelTypes[index]
            }
        }
    }
    sensorDetailList.add(
        DetailButtonModel(
            headline = stringResource(R.string.directChannelType_capitalized),
            icon = Icons.Default.ArrowRightAlt,
            dataValues = listOf(
                stringResource(R.string.supported_capitalized) + ": " + (if (supportedDirectChannelType == "") stringResource(
                    R.string.none_lowercase
                ) else supportedDirectChannelType)
            ).toMutableStateList()
        )
    )
    return sensorDetailList
}

/**
 * Initializes necessary values.
 */
fun initSensorData() {
    dataUpdatePaused = false
    currentData = listOf()
    dataToShow = mutableStateOf(listOf())
}