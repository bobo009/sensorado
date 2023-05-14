package sk.uniza.fri.sensorado.app.cameras.detail

import android.content.Context
import android.graphics.Rect
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.params.BlackLevelPattern
import android.hardware.camera2.params.DynamicRangeProfiles
import android.hardware.camera2.params.MandatoryStreamCombination
import android.hardware.camera2.params.StreamConfigurationMap
import android.os.Build
import android.util.Range
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import sk.uniza.fri.sensorado.R
import sk.uniza.fri.sensorado.additional.camelCaseRegex
import sk.uniza.fri.sensorado.assets.DetailButtonModel
import java.util.Locale

/**
 * ID of the currently selected camera
 */
var currentCameraId: String? = null

/**
 * List of strings to always render lowercase in the sections with information about camera
 */
val forceLowerCaseList =
    listOf("Hot", "Map", "Max", "Min", "Pre", "Big", "Pro", "App", "Far", "Num", "Is")

/**
 * List of strings to always render uppercase in the sections with information about camera
 */
val forceUpperCaseList = listOf("Jpeg")

/**
 * Generates list of categorized information about camera functions ready to be used with custom detail button.
 * @return list of categorized information about camera functions for custom detail button
 */
@Composable
fun generateCameraDetail(): List<DetailButtonModel> {
    val cameraDetailList: MutableList<DetailButtonModel> = mutableListOf()
    val context: Context = LocalContext.current
    val cameraManager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val keyData: List<KeyData> = getKeyData(
        keys = cameraManager.getCameraCharacteristics(currentCameraId!!).keys
    )
    for (singleKeyData in keyData) {
        cameraDetailList.add(
            DetailButtonModel(
                headline = singleKeyData.section, dataValues = singleKeyData.data
            )
        )
    }
    return cameraDetailList
}

/**
 * Data model for section of data determined from the system using API keys.
 */
data class KeyData(
    val section: String, val data: MutableList<String> = mutableListOf()
)

/**
 * Generates list of sections with information about the camera by parsing name to readable format and determining the information from the system API.
 * (Ignores entries where information exceeds 300 characters to maintain clarity and readability.)
 * @param keys list of camera API keys
 * @return list of sections with information about the camera
 */
@Composable
fun getKeyData(
    keys: List<CameraCharacteristics.Key<*>>
): List<KeyData> {
    val context: Context = LocalContext.current
    val cameraManager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val keyData: MutableList<KeyData> = mutableListOf()
    for (key in keys) {
        val keyList: MutableList<String> =
            key.name.drop("android.".length).split(".").toMutableList()
        val section = parseToDividedCamelCaseWords(keyList[0])
        val label = parseToDividedCamelCaseWords(keyList[keyList.size - 1])
        var finalKeyData = String()
        val keyDataToParse = cameraManager.getCameraCharacteristics(
            currentCameraId!!
        ).get(key)
        if (keyDataToParse is List<*>) {
            for (element in keyDataToParse) {
                finalKeyData += "$keyDataToParse, "
            }
            finalKeyData.dropLast(2)
        } else if (keyDataToParse is IntArray) {
            finalKeyData = keyDataToParse.contentToString().drop(1).dropLast(1)
        } else if (keyDataToParse is FloatArray) {
            finalKeyData = keyDataToParse.contentToString().drop(1).dropLast(1)
        } else if (keyDataToParse is BooleanArray) {
            finalKeyData = keyDataToParse.contentToString().drop(1).dropLast(1)
        } else if (keyDataToParse is Rect) {
            finalKeyData = keyDataToParse.toShortString()
        } else if (keyDataToParse is BlackLevelPattern) {
            finalKeyData =
                keyDataToParse.toString().drop("BlackLevelPattern".length + 1).dropLast(1)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && keyDataToParse is Range<*>) {
            finalKeyData = keyDataToParse.toString().drop(1).dropLast(1).replace(", ", "..")
        } else if (keyDataToParse is Array<*>) {
            if (keyDataToParse.isNotEmpty()) {
                if (keyDataToParse[0] is Range<*>) {
                    for (keyDataToParseElement in keyDataToParse) {
                        finalKeyData =
                            keyDataToParseElement.toString().drop(1).dropLast(1).replace(", ", "..")
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && keyDataToParse[0] is MandatoryStreamCombination) {
                    for (keyDataToParseElement in keyDataToParse) {
                        finalKeyData =
                            "${(keyDataToParseElement as MandatoryStreamCombination).description}"
                    }
                } else {
                    finalKeyData = keyDataToParse.contentToString().drop(1).dropLast(1)
                }
            }
        } else if (keyDataToParse is StreamConfigurationMap) {
            finalKeyData =
                keyDataToParse.toString().drop("StreamConfiguration".length + 1).dropLast(1)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && keyDataToParse is DynamicRangeProfiles) {
            for (keyDataToParseElement in keyDataToParse.supportedProfiles) {
                finalKeyData += "$keyDataToParseElement, "
            }
            finalKeyData.dropLast(2)
        } else {
            finalKeyData += keyDataToParse.toString()
        }
        finalKeyData = finalKeyData.replace(":", " = ")
        if (finalKeyData.contains("@")) {
            finalKeyData = stringResource(R.string.unknown_lowercase)
        }
        if (finalKeyData.length <= 300) {
            val data = "$label: $finalKeyData"
            if (keyData.isEmpty() || keyData.last().section != section) {
                keyData.add(KeyData(section = section))
            }
            keyData.last().data.add(data)
        }
    }
    return keyData
}

/**
 * Parses the defined string to divided words in camel case.
 * @param string string to parse
 * @return parsed string in divided words in camel case
 */
fun parseToDividedCamelCaseWords(
    string: String
): String {
    val stringList: MutableList<String> = string.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }.replace(camelCaseRegex, ".$0").split(".", "-", "_").toMutableList()
    for (index in stringList.indices) {
        if ((stringList[index].length <= 3 && !forceLowerCaseList.contains(stringList[index])) || forceUpperCaseList.contains(
                stringList[index]
            )
        ) {
            stringList[index] = stringList[index].uppercase(
                Locale.getDefault()
            )
        }
    }
    return stringList.joinToString(separator = " ")
}