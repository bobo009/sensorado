package sk.uniza.fri.sensorado.additional

import androidx.compose.ui.Modifier

/**
 * Function that applies or not specified modifier based on the result of the defined condition.
 * @param condition condition to check
 * @param modifier modifier to apply
 * @return final modifier
 */
fun Modifier.ifCondition(
    condition: Boolean, modifier: Modifier.() -> Modifier
): Modifier {
    return if (condition) {
        then(
            modifier(Modifier)
        )
    } else {
        this
    }
}