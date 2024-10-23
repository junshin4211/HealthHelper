package com.example.healthhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthhelper.dietary.test.MealsTimeRangeCategoryRepositoryTestUi
import com.example.healthhelper.screen.Main
import com.example.healthhelper.ui.theme.HealthHelperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthHelperTheme {
                //Main()
                MealsTimeRangeCategoryRepositoryTestUi()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthHelperTheme {
        Main()
    }
}
