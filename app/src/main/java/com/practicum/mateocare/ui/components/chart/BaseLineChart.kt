package com.practicum.mateocare.ui.components.chart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.layer.continuous
import com.patrykandpatrick.vico.compose.cartesian.layer.point
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shape.toVicoShape
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer

@Composable
private fun BaseLineChart(
    model: ChartModel,
    modifier: Modifier,

    lineColor: Color,
    pointSize: Dp,
    lineThickness: Dp,
    pointSpacing: Dp,
    enableScroll: Boolean
) {
    val values = model.values

    if (values.isEmpty()) return

    val chartModelProducer = remember { CartesianChartModelProducer() }

    val rangeProvider = remember(model.maxY, model.minY) {
        CartesianLayerRangeProvider.fixed(
            minY = model.minY,
            maxY = model.maxY
        )
    }

    //data
    LaunchedEffect(values) {
        chartModelProducer.runTransaction {
            lineSeries { //одна линия
                series(values)
            }
        }
    }

    //point style
    val pointComponent = rememberShapeComponent(
        fill = fill(lineColor),
        shape = CircleShape.toVicoShape()
    )

    //line style
    val line = LineCartesianLayer.rememberLine(
        fill = LineCartesianLayer.LineFill.single(fill(lineColor)),
        stroke = LineCartesianLayer.LineStroke.continuous(
            thickness = lineThickness,
            cap = StrokeCap.Round
        ),
        pointProvider = LineCartesianLayer.PointProvider.single(
            LineCartesianLayer.point(
                component = pointComponent,
                size = pointSize
            )
        )
    )

    //chart - сборка графика
    val chart = rememberCartesianChart( //сам график
        rememberLineCartesianLayer(
            lineProvider = LineCartesianLayer.LineProvider.series(line),
            pointSpacing = pointSpacing,
            rangeProvider = rangeProvider
        )
    )

    //render - отрисовка, связывант что рисовать и как рисовать
    ProvideVicoTheme(
        theme = rememberM3VicoTheme(
            lineCartesianLayerColors = listOf(lineColor)
        )
    ) {
        CartesianChartHost(
            chart = chart,
            modelProducer = chartModelProducer,
            modifier = modifier
                .fillMaxSize()
                .padding(AppChartDefaults.ContentPadding),
            scrollState = rememberVicoScrollState(
                scrollEnabled = enableScroll
            )
        )
    }
}

@Composable
fun PrimaryChart(
    model: ChartModel,
    modifier: Modifier = Modifier,
    pointSpacing: Dp = AppChartDefaults.PointSpacing,
    enableScroll: Boolean = false
) {
    BaseLineChart(
        model = model,
        modifier = modifier,
        lineColor = AppChartDefaults.PrimaryColor,
        pointSize = AppChartDefaults.PrimaryPointSize,
        lineThickness = AppChartDefaults.PrimaryLineThickness,
        pointSpacing = pointSpacing,
        enableScroll = enableScroll
    )
}