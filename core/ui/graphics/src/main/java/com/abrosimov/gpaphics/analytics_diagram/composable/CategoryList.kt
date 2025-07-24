package com.abrosimov.gpaphics.analytics_diagram.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrosimov.gpaphics.analytics_diagram.models.AnalyticsChartItem
import java.util.Locale

@Composable
fun CategoryList(
    items: List<AnalyticsChartItem>,
    baseTextSize: Float,
    modifier: Modifier = Modifier
) {
    val textSize = baseTextSize
    val density = LocalDensity.current
    val maxItemsDisplayed = 4
    val itemHeight = with(density) { (textSize.sp.toDp() + 8.dp).toPx() }
    val maxHeight = with(density) { (itemHeight * maxItemsDisplayed).toDp() }
    val maxWidth = 120.dp

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .height(maxHeight)
                .width(maxWidth),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                val displayName = if (item.name.length > 25) "${item.name.take(25)}..." else item.name
                androidx.compose.foundation.layout.Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp)
                ) {
                    Canvas(modifier = Modifier.size(with(density) { textSize.sp.toDp() })) {
                        drawCircle(
                            color = item.color,
                            radius = textSize
                        )
                    }
                    Text(
                        text = "${String.format(Locale.getDefault(), "%.0f", item.percentage)}% $displayName",
                        style = TextStyle(
                            fontSize = textSize.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}