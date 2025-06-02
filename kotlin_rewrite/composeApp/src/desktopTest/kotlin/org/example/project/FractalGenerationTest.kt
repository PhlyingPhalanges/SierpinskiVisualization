package org.example.project

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Unit tests for fractal generation functions in Fractals.kt
 */
class FractalGenerationTest {
    /**
     * Verify that generateCarpet with zero iterations returns the initial seed shape unchanged.
     */
    @Test
    fun testGenerateCarpet_zeroIterations_returnsStartingSquare() {
        val points = generateCarpet(0)
        assertEquals(startingSquare, points, "Zero iterations should return the starting square unchanged.")
    }

    /**
     * Verify that generateGasket with zero iterations returns the initial seed shape unchanged.
     */
    @Test
    fun testGenerateGasket_zeroIterations_returnsStartingTriangle() {
        val points = generateGasket(0)
        assertEquals(startingTriangle, points, "Zero iterations should return the starting triangle unchanged.")
    }

    /**
     * Verify that after one iteration, the number of points matches expected growth for Sierpiński Carpet.
     * Each point generates 8 new points due to the 8 functions in carpetIfs.
     */
    @Test
    fun testGenerateCarpet_oneIteration_pointsCount() {
        val points = generateCarpet(1)
        val expectedPointsCount = startingSquare.size * carpetIfs.size
        assertEquals(expectedPointsCount, points.size, "One iteration should multiply points by 8 for Carpet.")
    }

    /**
     * Verify that after one iteration, the number of points matches expected growth for Sierpiński Gasket.
     * Each point generates 3 new points due to the 3 functions in gasketIfs.
     */
    @Test
    fun testGenerateGasket_oneIteration_pointsCount() {
        val points = generateGasket(1)
        val expectedPointsCount = startingTriangle.size * gasketIfs.size
        assertEquals(expectedPointsCount, points.size, "One iteration should multiply points by 3 for Gasket.")
    }

    /**
     * Verify that generateFractal returns an empty list for unknown fractal names.
     */
    @Test
    fun testGenerateFractal_invalidSelection_returnsEmpty() {
        val points = generateFractal("InvalidFractal", 1)
        assertTrue(points.isEmpty(), "Unknown fractal selection should return empty point list.")
    }

    /**
     * Verify that generateFractal delegates correctly to generateCarpet when 'Carpet' is selected.
     */
    @Test
    fun testGenerateFractal_selectCarpet_matchesGenerateCarpet() {
        val fractalPoints = generateFractal("Carpet", 2)
        val carpetPoints = generateCarpet(2)
        assertEquals(carpetPoints, fractalPoints, "generateFractal should delegate to generateCarpet for 'Carpet'.")
    }

    /**
     * Verify that generateFractal delegates correctly to generateGasket when 'Gasket' is selected.
     */
    @Test
    fun testGenerateFractal_selectGasket_matchesGenerateGasket() {
        val fractalPoints = generateFractal("Gasket", 3)
        val gasketPoints = generateGasket(3)
        assertEquals(gasketPoints, fractalPoints, "generateFractal should delegate to generateGasket for 'Gasket'.")
    }

    /**
     * Basic sanity check to ensure points generated are within expected bounds [0,1].
     * This checks the transformations keep points in the unit square/triangle.
     */
    @Test
    fun testPointsWithinBounds_afterIterations() {
        val iterations = 3

        val carpetPoints = generateCarpet(iterations)
        assertTrue(carpetPoints.all { it.x in 0.0..1.0 && it.y in 0.0..1.0 }, "All carpet points should lie within the unit square.")

        val gasketPoints = generateGasket(iterations)
        assertTrue(gasketPoints.all { it.x in 0.0..1.0 && it.y in 0.0..1.0 }, "All gasket points should lie within the unit triangle bounding box.")
    }
}