package sk.uniza.fri.sensorado.app.sensors.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Expand
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.PlayForWork
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.ScreenRotationAlt
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled._360
import androidx.compose.material.icons.filled._3dRotation
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector
import sk.uniza.fri.sensorado.R

/**
 * Map of sensor data information.
 */
val sensorInfo: MutableMap<Int, SensorData> = mutableMapOf()

/**
 * Adds sensor data information for each documented sensor to sensor data information map.
 * @param context handle to the system
 */
@Suppress("DEPRECATION")
fun initSensorInfoMap(
    context: Context
) {
    // Motion Sensors
    sensorInfo[Sensor.TYPE_ACCELEROMETER] = SensorData(
        eventData = listOf(
            context.getString(R.string.xInclGravity_other),
            context.getString(R.string.yInclGravity_other),
            context.getString(
                R.string.zInclGravity_other
            )
        ), unit = SIUnit.Acceleration, icon = Icons.Default.Speed
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        sensorInfo[Sensor.TYPE_ACCELEROMETER_UNCALIBRATED] = SensorData(
            eventData = listOf(
                context.getString(R.string.xNoBias_other),
                context.getString(R.string.yNoBias_other),
                context.getString(
                    R.string.zNoBias_other
                ),
                context.getString(R.string.xBias_capitalized),
                context.getString(
                    R.string.yBias_capitalized
                ),
                context.getString(
                    R.string.zBias_capitalized
                )
            ), unit = SIUnit.Acceleration, icon = Icons.Default.Speed
        )
    }
    sensorInfo[Sensor.TYPE_GRAVITY] = SensorData(
        eventData = listOf(
            context.getString(R.string.xForce_capitalized),
            context.getString(R.string.yForce_capitalized),
            context.getString(
                R.string.zForce_capitalized
            )
        ), unit = SIUnit.Acceleration, icon = Icons.Default.PlayForWork
    )
    sensorInfo[Sensor.TYPE_GYROSCOPE] = SensorData(
        eventData = listOf(
            context.getString(R.string.xRate_capitalized),
            context.getString(R.string.yRate_capitalized),
            context.getString(
                R.string.zRate_capitalized
            )
        ), unit = SIUnit.AngularVelocity, icon = Icons.Default.Balance
    )
    sensorInfo[Sensor.TYPE_GYROSCOPE_UNCALIBRATED] = SensorData(
        eventData = listOf(
            context.getString(R.string.xNoDrift_other),
            context.getString(R.string.yNoDrift_other),
            context.getString(
                R.string.zNoDrift_other
            ),
            context.getString(R.string.xDrift_capitalized),
            context.getString(
                R.string.yDrift_capitalized
            ),
            context.getString(R.string.zDrift_capitalized)
        ), unit = SIUnit.AngularVelocity, icon = Icons.Default.Balance
    )
    sensorInfo[Sensor.TYPE_LINEAR_ACCELERATION] = SensorData(
        eventData = listOf(
            context.getString(R.string.xExclGravity_other),
            context.getString(R.string.yExclGravity_other),
            context.getString(
                R.string.zExclGravity_other
            )
        ), unit = SIUnit.Acceleration, icon = Icons.Default.FastForward
    )
    sensorInfo[Sensor.TYPE_ROTATION_VECTOR] = SensorData(
        eventData = listOf(
            context.getString(R.string.xTimesSinThetaDividedBy2_other),
            context.getString(R.string.yTimesSinThetaDividedBy2_other),
            context.getString(
                R.string.zTimesSinThetaDividedBy2_other
            ),
            context.getString(R.string.scalarTimesCosThetaDividedBy2_other)
        ), unit = SIUnit.None, icon = Icons.Default._360
    )
    sensorInfo[Sensor.TYPE_SIGNIFICANT_MOTION] = SensorData(
        icon = Icons.Default.Animation
    )
    sensorInfo[Sensor.TYPE_STEP_COUNTER] = SensorData(
        eventData = listOf(
            context.getString(R.string.stepsSinceReboot_other)
        ), unit = SIUnit.StepCount, icon = Icons.Default.Pets
    )
    sensorInfo[Sensor.TYPE_STEP_DETECTOR] = SensorData(
        icon = Icons.Default.Pets
    )
    // Position Sensors
    sensorInfo[Sensor.TYPE_GAME_ROTATION_VECTOR] = SensorData(
        eventData = listOf(
            context.getString(R.string.xTimesSinThetaDividedBy2_other),
            context.getString(R.string.yTimesSinThetaDividedBy2_other),
            context.getString(
                R.string.zTimesSinThetaDividedBy2_other
            )
        ), unit = SIUnit.None, icon = Icons.Default.ScreenRotationAlt
    )
    sensorInfo[Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR] = SensorData(
        eventData = listOf(
            context.getString(R.string.xTimesSinThetaDividedBy2_other),
            context.getString(R.string.yTimesSinThetaDividedBy2_other),
            context.getString(
                R.string.zTimesSinThetaDividedBy2_other
            )
        ), unit = SIUnit.None, icon = Icons.Default._3dRotation
    )
    sensorInfo[Sensor.TYPE_MAGNETIC_FIELD] = SensorData(
        eventData = listOf(
            context.getString(R.string.xStrength_capitalized),
            context.getString(R.string.yStrength_capitalized),
            context.getString(
                R.string.zStrength_capitalized
            )
        ), unit = SIUnit.MagneticFluxDensity, icon = Icons.Default.Public
    )
    sensorInfo[Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED] = SensorData(
        eventData = listOf(
            context.getString(R.string.xNoIronBias_other),
            context.getString(R.string.yNoIronBias_other),
            context.getString(R.string.zNoIronBias_other),
            context.getString(R.string.xIronBias_capitalized),
            context.getString(R.string.yIronBias_capitalized),
            context.getString(R.string.zIronBias_capitalized)
        ), unit = SIUnit.MagneticFluxDensity, icon = Icons.Default.Public
    )
    sensorInfo[Sensor.TYPE_ORIENTATION] = SensorData(
        eventData = listOf(
            context.getString(R.string.azimuthZ_capitalized),
            context.getString(R.string.pitchX_capitalized),
            context.getString(
                R.string.rollY_capitalized
            )
        ), unit = SIUnit.Angle, icon = Icons.Default.ScreenRotation
    )
    sensorInfo[Sensor.TYPE_PROXIMITY] = SensorData(
        eventData = listOf(
            context.getString(R.string.distance_capitalized)
        ), unit = SIUnit.Length, icon = Icons.Default.Expand
    )
    // Environment Sensors
    sensorInfo[Sensor.TYPE_AMBIENT_TEMPERATURE] = SensorData(
        eventData = listOf(
            context.getString(R.string.airTemperature_capitalized)
        ), unit = SIUnit.Temperature, icon = Icons.Default.Thermostat
    )
    sensorInfo[Sensor.TYPE_LIGHT] = SensorData(
        eventData = listOf(
            context.getString(R.string.illuminance_capitalized)
        ), unit = SIUnit.Illuminance, icon = Icons.Outlined.Lightbulb
    )
    sensorInfo[Sensor.TYPE_PRESSURE] = SensorData(
        eventData = listOf(
            context.getString(R.string.pressure_capitalized)
        ), unit = SIUnit.Pressure, icon = Icons.Default.Compress
    )
    sensorInfo[Sensor.TYPE_RELATIVE_HUMIDITY] = SensorData(
        eventData = listOf(
            context.getString(R.string.humidity_capitalized)
        ), unit = SIUnit.Humidity, icon = Icons.Outlined.WaterDrop
    )
    sensorInfo[Sensor.TYPE_TEMPERATURE] = SensorData(
        eventData = listOf(
            context.getString(R.string.deviceTemperature_capitalized)
        ), unit = SIUnit.Temperature, icon = Icons.Default.DeviceThermostat
    )
    // Other Sensors
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        sensorInfo[Sensor.TYPE_HEART_BEAT] = SensorData(
            eventData = listOf(
                context.getString(R.string.heartBeatConfidence_capitalized)
            ), unit = SIUnit.Confidence, icon = Icons.Default.FavoriteBorder
        )
    }
    sensorInfo[Sensor.TYPE_HEART_RATE] = SensorData(
        eventData = listOf(
            context.getString(R.string.heartRate_capitalized)
        ), unit = SIUnit.HeartRate, icon = Icons.Default.FavoriteBorder
    )
}

/**
 * Sensor data information model.
 */
data class SensorData(
    val eventData: List<String> = listOf(),
    val unit: SIUnit = SIUnit.None,
    val icon: ImageVector? = null
)

/**
 * Creates unit to be used with sensors.
 * @param symbol unit symbol
 * @param delimiter delimiter between value and symbol
 */
sealed class SIUnit(
    val symbol: String, val delimiter: String = ""
) {
    object None : SIUnit(
        symbol = ""
    )

    object Acceleration : SIUnit(
        symbol = "m/s²"
    )

    object AngularVelocity : SIUnit(
        symbol = "rad/s"
    )

    object StepCount : SIUnit(
        symbol = "steps", delimiter = " "
    )

    object MagneticFluxDensity : SIUnit(
        symbol = "μT"
    )

    object Angle : SIUnit(
        symbol = "°"
    )

    object Length : SIUnit(
        symbol = "cm"
    )

    object Temperature : SIUnit(
        symbol = "°C"
    )

    object Illuminance : SIUnit(
        symbol = "lx"
    )

    object Pressure : SIUnit(
        symbol = "hPa (mbar)"
    )

    object Humidity : SIUnit(
        symbol = "%"
    )

    object Confidence : SIUnit(
        symbol = "%"
    )

    object HeartRate : SIUnit(
        symbol = "bpm"
    )

    object Time : SIUnit(
        symbol = "μs"
    )

    object Power : SIUnit(
        symbol = "mA"
    )

    /**
     * Generates complete suffix containing both delimiter and symbol.
     */
    fun completeSuffix(): String {
        return delimiter + symbol
    }
}

/**
 * Converts accuracy constant from number format to string.
 * @param accuracy accuracy to convert
 * @param context handle to the system
 */
fun getAccuracy(accuracy: Int, context: Context): String {
    return when (accuracy) {
        SensorManager.SENSOR_STATUS_NO_CONTACT -> context.getString(R.string.untrusted_lowercase)
        SensorManager.SENSOR_STATUS_UNRELIABLE -> context.getString(R.string.unreliable_lowercase)
        SensorManager.SENSOR_STATUS_ACCURACY_LOW -> context.getString(R.string.low_lowercase)
        SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> context.getString(R.string.medium_lowercase)
        SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> context.getString(R.string.high_lowercase)
        else -> context.getString(R.string.unknown_lowercase)
    }
}