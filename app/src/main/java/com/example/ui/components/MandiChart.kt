package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AgriGreenPrimaryLight
import com.example.ui.theme.HarvestGold
import com.example.ui.theme.HarvestGoldDark
import com.example.ui.theme.PriceGainGreen
import kotlin.math.max
import kotlin.math.min

enum class ChartTimeRange(val label: String, val days: Int) {
    SEVEN_DAYS("7D", 7),
    ONE_MONTH("1M", 30),
    THREE_MONTHS("3M", 90),
    SIX_MONTHS("6M", 180),
    ONE_YEAR("1Y", 365)
}

@Composable
fun MandiTrendChart(
    dataPoints: List<Double>,
    minBand: Double? = null,
    maxBand: Double? = null,
    arrivalsVolume: Double? = null,
    selectedRange: ChartTimeRange = ChartTimeRange.SEVEN_DAYS,
    onRangeSelected: (ChartTimeRange) -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (dataPoints.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "No historical price data available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        return
    }

    var selectedIndex by remember(dataPoints) { mutableStateOf<Int?>(null) }
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 650),
        label = "chart_progress"
    )

    val minPrice = (dataPoints.minOrNull() ?: 0.0) * 0.96
    val maxPrice = (dataPoints.maxOrNull() ?: 100.0) * 1.04
    val priceRange = max(1.0, maxPrice - minPrice)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        // Top Time Range Selector Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Modal Price Trend (₹/Qtl)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                ChartTimeRange.values().forEach { range ->
                    FilterChip(
                        selected = range == selectedRange,
                        onClick = { onRangeSelected(range) },
                        label = { Text(range.label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier
                            .height(28.dp)
                            .testTag("chart_range_${range.label}")
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Selected data point tooltip or peak/low summary
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedIndex != null && selectedIndex!! in dataPoints.indices) {
                val hoveredPrice = dataPoints[selectedIndex!!]
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(AgriGreenPrimaryLight, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Day ${selectedIndex!! + 1}: ₹${String.format("%,.0f", hoveredPrice)} / Qtl",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Current: ",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "₹${String.format("%,.0f", dataPoints.last())} / Qtl",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Low: ₹${String.format("%,.0f", dataPoints.minOrNull() ?: 0.0)}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "High: ₹${String.format("%,.0f", dataPoints.maxOrNull() ?: 0.0)}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = PriceGainGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Interactive Canvas Chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            val primaryColor = MaterialTheme.colorScheme.primary
            val gridColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)

            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .pointerInput(dataPoints) {
                        detectTapGestures { offset ->
                            val stepX = size.width / (dataPoints.size - 1).coerceAtLeast(1)
                            val tappedIndex = (offset.x / stepX).toInt().coerceIn(0, dataPoints.lastIndex)
                            selectedIndex = tappedIndex
                        }
                    }
            ) {
                val width = size.width
                val height = size.height
                val paddingBottom = 20.dp.toPx()
                val availableHeight = height - paddingBottom

                // Draw 3 horizontal grid lines (Max, Mid, Min)
                val gridSteps = 3
                for (i in 0..gridSteps) {
                    val y = (availableHeight / gridSteps) * i
                    drawLine(
                        color = gridColor,
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                if (dataPoints.size < 2) return@Canvas

                val stepX = width / (dataPoints.size - 1)

                // Build line path
                val linePath = Path()
                val fillPath = Path()

                dataPoints.forEachIndexed { index, price ->
                    val normalizedY = 1f - ((price - minPrice) / priceRange).toFloat()
                    val x = index * stepX
                    val y = (normalizedY * availableHeight) * progress

                    if (index == 0) {
                        linePath.moveTo(x, y)
                        fillPath.moveTo(x, availableHeight)
                        fillPath.lineTo(x, y)
                    } else {
                        // Smooth cubic bezier
                        val prevPrice = dataPoints[index - 1]
                        val prevNormY = 1f - ((prevPrice - minPrice) / priceRange).toFloat()
                        val prevX = (index - 1) * stepX
                        val prevY = (prevNormY * availableHeight) * progress
                        val controlX = (prevX + x) / 2f

                        linePath.cubicTo(controlX, prevY, controlX, y, x, y)
                        fillPath.cubicTo(controlX, prevY, controlX, y, x, y)
                    }
                }

                fillPath.lineTo(width, availableHeight)
                fillPath.close()

                // Draw subtle gradient fill underneath
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = 0.28f),
                            primaryColor.copy(alpha = 0.02f)
                        ),
                        startY = 0f,
                        endY = availableHeight
                    )
                )

                // Draw trend stroke
                drawPath(
                    path = linePath,
                    color = primaryColor,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw data points or selected indicator
                dataPoints.forEachIndexed { index, price ->
                    val normalizedY = 1f - ((price - minPrice) / priceRange).toFloat()
                    val x = index * stepX
                    val y = (normalizedY * availableHeight) * progress
                    val isHovered = selectedIndex == index
                    val isLast = index == dataPoints.lastIndex

                    if (isHovered || isLast) {
                        drawCircle(
                            color = primaryColor,
                            radius = if (isHovered) 6.dp.toPx() else 4.5.dp.toPx(),
                            center = Offset(x, y)
                        )
                        drawCircle(
                            color = Color.White,
                            radius = if (isHovered) 3.5.dp.toPx() else 2.5.dp.toPx(),
                            center = Offset(x, y)
                        )
                    }
                }
            }
        }

        // Arrivals volume & Corridor info footer
        if (minBand != null && maxBand != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Mandi Price Band: ₹${String.format("%,.0f", minBand)} - ₹${String.format("%,.0f", maxBand)}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (arrivalsVolume != null) {
                    Text(
                        text = "Arrivals: ${String.format("%,.0f", arrivalsVolume)} Qtl",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HarvestGoldDark
                    )
                }
            }
        }
    }
}
