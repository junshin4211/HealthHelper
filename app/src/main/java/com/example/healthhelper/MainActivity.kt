package com.example.healthhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthhelper.signuplogin.LoginMain

import com.example.healthhelper.ui.theme.HealthHelperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthHelperTheme {
                LoginMain()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthHelperTheme {
        LoginMain()
    }
}
