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

package me.chrisarriola.creativecoding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.chrisarriola.creativecoding.kochsnowflake.KochSnowflake
import me.chrisarriola.creativecoding.ui.theme.CreativeCodingTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.rotate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreativeCodingTheme {

                var iterations by remember { mutableStateOf(0) }
                var rotation by remember { mutableStateOf(0f) }

                intTicker(1000)
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .onEach { iterations = it % 6 }
                    .launchIn(lifecycleScope)

                floatTicker(delayInMillis = 4, increment = 1f)
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .onEach { rotation = it }
                    .launchIn(lifecycleScope)

                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    KochSnowflake(
                            modifier = Modifier.fillMaxSize()
                                .rotate(rotation),
                            color = MaterialTheme.colors.primary,
                            iterations = iterations,
                            strokeWidth = 3f
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CreativeCodingTheme {
        KochSnowflake(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.primary
        )
    }
}