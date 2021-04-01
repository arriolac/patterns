package me.chrisarriola.creativecoding.kochsnowflake

import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.core.graphics.rotationMatrix
import androidx.core.graphics.scaleMatrix
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

private const val viewFactor = 0.95f
private val scaleMatrix = Matrix(
    values = floatArrayOf(
        1f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 0f, 0f,
        0f, 0f, 0f, 0f,
    )
)


@Composable
fun KochSnowflake(
    modifier: Modifier = Modifier,
    color: Color,
    iterations: Int = 0,
    strokeWidth: Float = Stroke.HairlineWidth
) {
    val rotationProgress = animatedFloat(initVal = 0f)
    onActive {
        rotationProgress.animateTo(
            360f,
            anim = repeatable(AnimationConstants.Infinite, tween(30000, easing = LinearEasing))
        )
    }
    Canvas(modifier = modifier) {
        val length = viewFactor * size.width
        val vertex1 = Offset(
            size.width * (1 - viewFactor),
            (size.height / 2) - (cos(60f.radians) * length) / 2
        )
        val vertex2 = Offset(size.width * viewFactor, vertex1.y)
        val vertex3 = Offset(size.width / 2, vertex1.y + (length * sin(60f.radians)))
        var lines = listOf(
            KochLine(vertex1, vertex2),
            KochLine(vertex2, vertex3),
            KochLine(vertex3, vertex1),
        )

        scale(
            scale = 1f,
            pivot = vertex3
        ) {
        rotate(0f) {
                // Initialize the equilateral triangle

                // Run iterations
                repeat(iterations) {
                    lines = lines.flatMap { it.expand() }
                }

                // Draw
                lines.forEach {
                    drawLine(
                        color = color,
                        start = scaleMatrix.map(it.start),
                        end = scaleMatrix.map(it.end),
                        strokeWidth = strokeWidth
                    )
                }
            }
        }
    }
}

val Float.radians: Float
    get() = (this * (Math.PI / 180)).toFloat()

private data class KochLine(val start: Offset, val end: Offset) {

    private val rotationMatrix = Matrix(
        values = floatArrayOf(
            cos(300f.radians), -sin(300f.radians), 0f, 0f,
            sin(300f.radians), cos(300f.radians), 0f, 0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
        )
    )

    val pointA: Offset = start

    val pointB: Offset by lazy {
        lerp(start, end, 0.33f)
    }

    val pointC: Offset by lazy {
        val intermediate = pointD - pointB
        // Apply rotation
        pointB + rotationMatrix.map(intermediate)
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
