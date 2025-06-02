package org.example.project

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * The main composable function for the Sierpinski visualization app.
 *
 * Manages the overall UI state including selecting fractal type, number of
 * iterations, and the set of points to be rendered.
 *
 * Handles user interaction via selection controls and triggers fractal generation.
 */
@Composable
@Preview
fun App() {
    var selectedFractal by remember { mutableStateOf("Carpet") }
    var numIterations by remember { mutableStateOf(1) }
    var points by remember { mutableStateOf<List<Point>>(emptyList()) }

    // dynamically determine valid iteration options based on selected fractal
    // (based on tested performance)
    val iterationOptions = if (selectedFractal == "Carpet") {
        (1..5).toList()
    } else {
        (1..10).toList()
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // left side: controls
        Column(
            modifier = Modifier
                .width(250.dp)
                .fillMaxHeight()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Header()
            FractalSelectionButtonRow(
                selectedFractal = selectedFractal,
                onFractalSelected = { selectedFractal = it }
            )
            IterationSelector(
                numIterations = numIterations,
                onNumberSelected = { numIterations = it },
                options = iterationOptions
            )
            RunVisualizer(
                onClick = {
                    points = generateFractal(selectedFractal, numIterations)
                }
            )
        }

        // right size: canvas
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            FractalCanvas(points = points)
        }
    }
}

@Composable
fun Header() {
    Text("Please select a fractal object and number of iterations below", style = MaterialTheme.typography.h5)
}

@Composable
fun FractalSelectionButtonRow(selectedFractal: String?, onFractalSelected: (String) -> Unit) {
    Row {
        CarpetButton(
            isSelected = selectedFractal == "Carpet",
            onClick = { onFractalSelected("Carpet") }
        )
        Spacer(Modifier.width(8.dp))
        GasketButton(
            isSelected = selectedFractal == "Gasket",
            onClick = { onFractalSelected("Gasket") }
        )
    }
}

@Composable
fun CarpetButton(isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) Color.White else ButtonDefaults.buttonColors().backgroundColor(enabled = true).value
        )
    ) {
        Text("Carpet")
    }
}

@Composable
fun GasketButton(isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) Color.White else ButtonDefaults.buttonColors().backgroundColor(enabled = true).value
        )
    ) {
        Text("Gasket")
    }
}

/**
 * A dropdown selector composable for choosing the number of iterations.
 *
 * @param numIterations The currently selected number of iterations.
 * @param onNumberSelected Callback invoked when a new iteration number is selected.
 * @param options A list of valid iteration numbers to display in the dropdown.
 */
@Composable
fun IterationSelector(numIterations: Int, onNumberSelected: (Int) -> Unit, options: List<Int>) {
    var expanded by remember {mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text("Iterations: $numIterations")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { number ->
                DropdownMenuItem(
                    onClick = {
                        onNumberSelected(number)
                        expanded = false
                    }
                ) {
                    Text(number.toString())
                }
            }
        }
    }
}

/**
 * Button to trigger fractal generation and visualization.
 *
 * @param onClick Callback when the button is pressed.
 */
@Composable
fun RunVisualizer(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Run Visualizer")
    }
}

/**
 * A canvas composable that renders the fractal points
 *
 * @param points A list of points to be drawn to the canvas. Each point
 * is expected to be normalized between 0 and 1
 */
@Composable
fun FractalCanvas(points: List<Point>) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val squareSize = min(maxWidth, maxHeight)

        Canvas(
            modifier = Modifier.size(squareSize).background(Color.Black)
        ) {

            val scale = size.minDimension
            // map points to Offsets scaled to canvas size
            val offsets = points.map { point ->
                Offset(x = (point.x * scale).toFloat(), y = (point.y * scale).toFloat())
            }

            drawPoints(
                points = offsets,
                pointMode = PointMode.Points,
                color = Color.White,
                strokeWidth = 1f
            )
        }
    }

}