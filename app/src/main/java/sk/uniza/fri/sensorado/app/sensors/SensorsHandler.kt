package sk.uniza.fri.sensorado.app.sensors

import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import sk.uniza.fri.sensorado.R
import sk.uniza.fri.sensorado.app.sensors.data.getAccuracy
import sk.uniza.fri.sensorado.app.sensors.data.initSensorInfoMap
import sk.uniza.fri.sensorado.app.sensors.data.sensorInfo
import sk.uniza.fri.sensorado.app.sensors.detail.currentData
import sk.uniza.fri.sensorado.app.sensors.detail.currentSensor
import sk.uniza.fri.sensorado.app.sensors.detail.dataToShow
import sk.uniza.fri.sensorado.app.sensors.detail.dataUpdatePaused
import sk.uniza.fri.sensorado.app.sensors.detail.initSensorData
import sk.uniza.fri.sensorado.assets.DetailButtonModel
import sk.uniza.fri.sensorado.navigation.Screen
import java.util.Locale

/**
 * List of sensor listeners.
 */
var sensorListeners: MutableList<SensorEventListener> = mutableListOf()

/**
 * Data model for basic sensor information.
 */
data class BasicSensorInfo(
    val label: String, val description: String
)

/**
 * Generates list of sensors in the device.
 * @return list of sensors
 */
@Composable
fun getSensorList(): List<Sensor> {
    val context = LocalContext.current
    val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    if (sensorInfo.isEmpty()) {
        initSensorInfoMap(context = context)
    }
    return sensorManager.getSensorList(Sensor.TYPE_ALL)
}

/**
 * Returns number of sensors in the device.
 * @return number of sensors
 */
@Composable
fun getSensorCount(): Int {
    return getSensorList().size
}

/**
 * Generates basic sensor information for the sensor specified by parameter.
 * @param sensor sensor to get the information for
 * @return basic information about the sensor
 */
@Composable
fun getBasicSensorInfo(
    sensor: Sensor
): BasicSensorInfo {
    val label = sensor.stringType.drop(("android.sensor.").length).replace('_', ' ').split(" ")
        .joinToString(" ") { it ->
            it.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
        }
    val name = sensor.name
    return BasicSensorInfo(
        label = label, description = name
    )
}

/**
 * Generates list of sensors ready to be used with custom detail button.
 * @param navController object managing navigation
 * @return list of sensors for custom detail button
 */
@Composable
fun generateSensorModels(
    navController: NavHostController
): List<DetailButtonModel> {
    val context = LocalContext.current
    val contextActivity: Activity = LocalContext.current as Activity
    val sensorList: MutableList<DetailButtonModel> = mutableListOf()
    val sensors = getSensorList()
    val sensorManager: SensorManager =
        LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    unregisterSensorListeners(sensorManager)
    val sensorListenersGenerated = sensorListeners.size > 0
    for (sensor in sensors) {
        val sensorInformation: BasicSensorInfo = getBasicSensorInfo(sensor = sensor)
        var sensorDataValues: MutableList<String> = mutableListOf()
        if (!sensorListenersGenerated) {
            val sensorListener = object : SensorEventListener {
                override fun onSensorChanged(sensorEvent: SensorEvent?) {
                    val currentEvent: SensorEvent? = sensorEvent
                    if (currentEvent != null) {
                        sensorDataValues = mutableListOf()
                        for (index in currentEvent.values.indices) {
                            val label: String = if ((sensorInfo[sensor.type]?.eventData?.size
                                    ?: 0) > index
                            ) (sensorInfo[sensor.type]?.eventData?.get(index)
                                ?: "${context.getString(R.string.data_capitalized)} [$index]") else "${
                                context.getString(
                                    R.string.data_capitalized
                                )
                            } [$index]"
                            val unit: String =
                                if (label != "${context.getString(R.string.data_capitalized)} [$index]") (sensorInfo[sensor.type]?.unit?.completeSuffix()
                                    ?: "") else ""
                            sensorDataValues.add(
                                index, label + ": " + currentEvent.values[index] + unit
                            )
                        }
                        sensorDataValues.add(
                            "${context.getString(R.string.accuracy_capitalized)}: " + getAccuracy(
                                accuracy = currentEvent.accuracy, context = context
                            )
                        )
                        updateData()
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                    if (sensor != null && sensorDataValues.size > 1) {
                        sensorDataValues[sensorDataValues.size - 1] =
                            "${context.getString(R.string.accuracy_capitalized)}: " + getAccuracy(
                                accuracy = accuracy, context = context
                            )
                        updateData()
                    }
                }

                /**
                 * Updates current data of the sensor and applies them, if not paused.
                 */
                fun updateData() {
                    if (sensor == currentSensor) {
                        currentData = sensorDataValues
                        if (!dataUpdatePaused) {
                            dataToShow = mutableStateOf(currentData)
                        }
                    }
                }
            }
            sensorListeners.add(sensorListener)
            sensorManager.registerListener(
                sensorListeners[sensorListeners.size - 1],
                sensor,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
        sensorList.add(
            DetailButtonModel(headline = sensorInformation.label,
                icon = sensorInfo[sensor.type]?.icon ?: Icons.Default.Sensors,
                dataValues = listOf(
                    sensorInformation.description
                ).toMutableStateList(),
                onClick = {
                    currentSensor = sensor
                    initSensorData()
                    navController.navigate(Screen.SensorDetail.route)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && currentSensor != null && (currentSensor!!.type == Sensor.TYPE_STEP_DETECTOR || currentSensor!!.type == Sensor.TYPE_STEP_COUNTER)) {
                        if (ContextCompat.checkSelfPermission(
                                contextActivity, ACTIVITY_RECOGNITION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                contextActivity, arrayOf(ACTIVITY_RECOGNITION), 1
                            )
                        }
                    }
                })
        )
    }
    return sensorList
}

/**
 * Unregisters all sensor listeners.
 * @param sensorManager object managing sensors
 */
private fun unregisterSensorListeners(
    sensorManager: SensorManager
) {
    for (sensorListener in sensorListeners) {
        sensorManager.unregisterListener(sensorListener)
    }
    sensorListeners.clear()
}