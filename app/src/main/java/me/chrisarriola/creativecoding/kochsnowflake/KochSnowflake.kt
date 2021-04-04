// Copyright (c) 2021 Chris Arriola
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of
// this software and associated documentation files (the "Software"), to deal in
// the Software without restriction, including without limitation the rights to
// use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
// the Software, and to permit persons to whom the Software is furnished to do so,
// subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
// FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
// COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
// IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
// CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package me.chrisarriola.creativecoding.kochsnowflake

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.cos
import kotlin.math.sin

private const val viewFactor = 0.75f

@Composable
fun KochSnowflake(
    modifier: Modifier = Modifier,
    color: Color,
    iterations: Int = 0,
    strokeWidth: Float = Stroke.HairlineWidth
) {
    Canvas(modifier = modifier) {
        // Initialize the equilateral triangle
        val length = viewFactor * size.width
        val vertex1 = Offset(
            (size.width - length) / 2,
            (size.height / 2) - (cos(60f.radians) * length) / 2
        )
        val vertex2 = Offset(vertex1.x + length, vertex1.y)
        val vertex3 = Offset(size.width / 2, vertex1.y + (length * sin(60f.radians)))
        var lines = listOf(
            KochLine(vertex1, vertex2),
            KochLine(vertex2, vertex3),
            KochLine(vertex3, vertex1),
        )

        // Run iterations
        repeat(iterations) {
            val newLines = mutableListOf<KochLine>()
            lines.forEach { newLines.addAll(it.expand()) }
            lines = newLines
        }

        // Draw
        lines.forEach {
            drawLine(
                color = color,
                start = it.start,
                end = it.end,
                strokeWidth = strokeWidth
            )
        }
    }
}

val Float.radians: Float
    get() = (this * (Math.PI / 180)).toFloat()

private data class KochLine(val start: Offset, val end: Offset) {
    val pointA: Offset = start

    val pointB: Offset by lazy {
        lerp(start, end, 0.33f)
    }

    val pointC: Offset by lazy {
        val intermediate = pointD - pointB
        pointB + Offset(
            (intermediate.x * cos((-60f).radians)) + (intermediate.y * -sin((-60f).radians)),
            (intermediate.x * sin((-60f).radians)) + (intermediate.y * cos((-60f).radians)),
        )
    }

    val pointD: Offset by lazy {
        lerp(start, end, 0.66f)
    }

    val pointE: Offset = end

    fun expand(): List<KochLine> {
        return arrayListOf(
            KochLine(pointA, pointB),
            KochLine(pointB, pointC),
            KochLine(pointC, pointD),
            KochLine(pointD, pointE),
        )
    }
}
