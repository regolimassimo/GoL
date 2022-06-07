package it.massimoregoli.gol.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun Splash(dim: Dp = 400.dp) {
    Canvas(modifier = Modifier.size(dim)) {
        val height = size.height
        val width = size.width
        val dx = height/5
        val dy = width/5

        drawRect(Color.Black,
            Offset(2*dx, dy),
            Size(dx, dy))
        drawRect(Color.Black,
            Offset(3*dx, 2*dy),
            Size(dx, dy))
        drawRect(Color.Black,
            Offset(dx, 3*dy),
            Size(dx, dy))
        drawRect(Color.Black,
            Offset(2*dx, 3*dy),
            Size(dx, dy))
        drawRect(Color.Black,
            Offset(3*dx, 3*dy),
            Size(dx, dy))
    }
}

//@Composable
//@Preview
//fun PatternElement() {
//    Row(verticalAlignment = Alignment.Bottom) {
//        Splash(60.dp)
//        Text(text = "Starship n.o 1")
//    }
//}