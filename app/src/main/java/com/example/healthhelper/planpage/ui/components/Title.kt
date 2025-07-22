package com.example.healthhelper.planpage.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthhelper.ui.theme.HealthHelperTheme

@Composable
fun Title(
    modifier: Modifier = Modifier,
    title: String,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    fontWeight: FontWeight? = FontWeight.Bold
) {
    Text(
        text = title,
        style = style,
        color = color,
        fontWeight = fontWeight,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TitlePreview() {
    HealthHelperTheme {
        Title(title = "My Title")
    }
}