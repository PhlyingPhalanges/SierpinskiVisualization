package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var selectedFractal by remember { mutableStateOf("Carpet") }
    var numIterations by remember { mutableStateOf(1) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        ButtonRow(
            selectedFractal = selectedFractal,
            onFractalSelected = { selectedFractal = it }
        )
        IterationSelector(
            numIterations = numIterations,
            onNumberSelected = { numIterations = it }
        )
        RunVisualizerButton(
            selectedFractal = selectedFractal,
            numIterations = numIterations,
            onRun = {
                // DO WORK
            }
        )
    }
}

@Composable
fun Header() {
    Text("Please select a fractal object and number of iterations below", style = MaterialTheme.typography.h5)
}

@Composable
fun ButtonRow(selectedFractal: String?, onFractalSelected: (String) -> Unit) {
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

@Composable
fun IterationSelector(numIterations: Int, onNumberSelected: (Int) -> Unit) {
    var expanded by remember {mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text("Iterations: $numIterations")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            (1..8).forEach { number ->
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

@Composable
fun RunVisualizerButton(selectedFractal: String, numIterations: Int, onRun: () -> Unit) {
    Button(
        onClick = {
            onRun()
        }
    ) {
        Text("Run Visualizer")
    }
}