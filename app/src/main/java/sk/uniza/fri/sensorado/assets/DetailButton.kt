package sk.uniza.fri.sensorado.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import sk.uniza.fri.sensorado.additional.ifCondition

/**
 * Creates custom detail button object.
 * @param model model containing information to show
 */
@Composable
fun DetailButton(
    model: DetailButtonModel
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(
            shape = RoundedCornerShape(20.dp)
        )
        .background(
            Color(
                red = if (isSystemInDarkTheme()) 255 else 0,
                green = if (isSystemInDarkTheme()) 255 else 0,
                blue = if (isSystemInDarkTheme()) 255 else 0,
                alpha = (255 * 0.05).toInt()
            )
        )
        .ifCondition(model.onClick != null) {
            clickable {
                model.onClick?.invoke()
            }
        }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    25.dp, 17.dp, 25.dp, 20.dp
                ), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(0.dp, 0.dp, if (model.icon != null) 50.dp else 0.dp, 0.dp)
            ) {
                if (model.headline != null) {
                    Text(
                        text = model.headline,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        lineHeight = MaterialTheme.typography.titleMedium.fontSize * 1.25,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(
                            alignment = Alignment.Start
                        )
                    )
                    Spacer(
                        modifier = Modifier.padding(2.dp)
                    )
                }
                if (model.dataValues != null) {
                    for (index in model.dataValues!!.indices) {
                        Text(
                            text = model.dataValues!![index],
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            lineHeight = MaterialTheme.typography.bodySmall.fontSize * 1.25,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.alpha(0.5f)
                        )
                    }
                }
            }
            if (model.icon != null) {
                Icon(
                    imageVector = model.icon,
                    contentDescription = "${model.headline ?: ""} Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .alpha(0.3f)
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}

/**
 * Data model for custom detail button.
 */
data class DetailButtonModel(
    val headline: String? = null,
    val icon: ImageVector? = null,
    var dataValues: List<String>? = null,
    val onClick: (() -> Unit)? = null
)

/**
 * Creates lazy column containing custom detail buttons.
 * @param headline headline to show
 * @param list list of data for custom detail buttons
 */
@Composable
fun DetailButtonLazyColumn(
    headline: String? = null, list: List<DetailButtonModel>? = null
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp), contentPadding = PaddingValues(20.dp)
    ) {
        if (headline != null) {
            item(1) {
                Text(
                    text = headline,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    lineHeight = MaterialTheme.typography.headlineMedium.fontSize * 1.25,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
        if (list != null) {
            items(list) { model ->
                DetailButton(model = model)
            }
        }
    }
}