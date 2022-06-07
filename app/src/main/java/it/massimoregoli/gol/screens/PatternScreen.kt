package it.massimoregoli.gol.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun PatternRow() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Pattern(Color.Red, 50.dp)
        Text(text = "Starship n. 1",
        modifier = Modifier.padding(start=8.dp))
    }
}

@Composable
@Preview
fun Pattern(color: Color = Color.Red, dim: Dp = 100.dp) {
    Canvas(modifier = Modifier.size(dim)) {
        val height = size.height
        val width = size.width
        val dx = height/5
        val dy = width/5

        drawRect(
            color,
            Offset(2*dx, dy),
            Size(dx, dy)
        )
        drawRect(
            color,
            Offset(3*dx, 2*dy),
            Size(dx, dy)
        )
        drawRect(
            color,
            Offset(dx, 3*dy),
            Size(dx, dy)
        )
        drawRect(
            color,
            Offset(2*dx, 3*dy),
            Size(dx, dy)
        )
        drawRect(
            color,
            Offset(3*dx, 3*dy),
            Size(dx, dy)
        )
    }
}

@Composable
@Preview
fun Logo() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasSize = size
        val canvasWidth = size.width
        val canvasHeight = size.height
        withTransform({
            translate(left = canvasWidth / 5F)
            rotate(degrees = 45F)
        }) {
            drawRect(
                color = Color.Gray,
                topLeft = Offset(x = canvasWidth / 3F, y = canvasHeight / 3F),
                size = canvasSize / 3F
            )
        }
    }
}