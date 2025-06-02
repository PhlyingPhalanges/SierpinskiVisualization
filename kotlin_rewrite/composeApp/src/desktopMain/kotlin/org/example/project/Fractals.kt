package org.example.project

import kotlin.math.sqrt

/**
 * Represents a 2D point with x and y coordinates
 */
data class Point(val x: Double, val y: Double)

// Sierpiński Carpet IFS (Iterated Function System)
val carpetIfs: List<(Double, Double) -> Point> = listOf(
    { x, y -> Point(x / 3.0, y / 3.0) },
    { x, y -> Point(x / 3.0, y / 3.0 + 1.0 / 3.0) },
    { x, y -> Point(x / 3.0, y / 3.0 + 2.0 / 3.0) },
    { x, y -> Point(x / 3.0 + 1.0 / 3.0, y / 3.0) },
    { x, y -> Point(x / 3.0 + 2.0 / 3.0, y / 3.0) },
    { x, y -> Point(x / 3.0 + 1.0 / 3.0, y / 3.0 + 2.0 / 3.0) },
    { x, y -> Point(x / 3.0 + 2.0 / 3.0, y / 3.0 + 1.0 / 3.0) },
    { x, y -> Point(x / 3.0 + 2.0 / 3.0, y / 3.0 + 2.0 / 3.0) },
)

// Sierpiński Gasket IFS (Iterated Function System)
val gasketIfs: List<(Double, Double) -> Point> = listOf(
    { x, y -> Point(x / 2.0, y / 2.0) },
    { x, y -> Point(x / 2.0 + 1.0 / 2.0, y / 2.0) },
    { x, y -> Point(x / 2.0 + 1.0 / 4.0, y / 2.0 + sqrt(3.0) / 4.0) }
)

// starting square (carpet seed shape)
val startingSquare = listOf(
    Point(0.0, 0.0),
    Point(0.0, 1.0),
    Point(1.0, 1.0),
    Point(1.0, 0.0),
    Point(0.0, 0.0)
)

// starting triangle (gasket seed shape)
val startingTriangle = listOf(
    Point(0.0, 0.0),
    Point(1.0 / 2.0, sqrt(3.0) / 2.0),
    Point(1.0, 0.0),
    Point(0.0, 0.0)
)

/**
 * Generates the list of points representing the selected fractal at the specified
 * number of iterations.
 *
 * @param selectedFractal Name of the fractal to generate ("Carpet" or "Gasket").
 * @param numIterations Number of iterations/depth of fractal generation.
 * @return List of points after fractal generation.
 */
fun generateFractal(selectedFractal: String, numIterations: Int): List<Point> {
    return when (selectedFractal) {
        "Carpet" -> generateCarpet(numIterations)
        "Gasket" -> generateGasket(numIterations)
        else -> emptyList()
    }
}

/**
 * Generates the Sierpiński Carpet fractal points by iteratively applying the Carpet
 * IFS transformations.
 *
 * @param numIterations Number of iterations to apply.
 * @return List of points representing the Carpet fractal at the given iteration.
 */
fun generateCarpet(numIterations: Int): List<Point> {
    var points = startingSquare

    repeat(numIterations) {
        val newPoints = mutableListOf<Point>()
        for (point in points) {
            for (f in carpetIfs) {
                newPoints.add(f(point.x, point.y))
            }
        }
        points = newPoints
    }

    return points
}

/**
 * Generates the Sierpiński Gasket fractal points by iteratively applying the Gasket
 * IFS transformations.
 *
 * @param numIterations Number of iterations to apply.
 * @return List of points representing the Gasket fractal at the given iteration.
 */
fun generateGasket(numIterations: Int): List<Point> {
    var points = startingTriangle

    repeat(numIterations) {
        val newPoints = mutableListOf<Point>()
        for (point in points) {
            for (f in gasketIfs) {
                newPoints.add(f(point.x, point.y))
            }
        }
        points = newPoints
    }

    return points
}


