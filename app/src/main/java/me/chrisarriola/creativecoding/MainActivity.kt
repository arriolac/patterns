package me.chrisarriola.creativecoding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import me.chrisarriola.creativecoding.kochsnowflake.KochSnowflake
import me.chrisarriola.creativecoding.ui.theme.CreativeCodingTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreativeCodingTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    KochSnowflake(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.primary,
                        iterations = 4,
                        strokeWidth = 5f
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