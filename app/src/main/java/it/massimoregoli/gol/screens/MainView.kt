package it.massimoregoli.gol.screens

import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import it.massimoregoli.gol.model.GoLViewModel
import it.massimoregoli.gol.model.Universe

fun getOffset(universe: Universe, size: Size, radius: Float): Offset {
    val startX = (size.width -
            2 * universe.dimX.toFloat() * radius - radius) / 2
    val startY = (size.height -
            2 * universe.dimY.toFloat() * radius - radius) / 2

    return Offset(startX, startY)
}

fun getRadius(universe: Universe, size: Size): Float {
    val radX = size.width / (universe.dimX.toFloat() + 2)
    val radY = size.height / (universe.dimY.toFloat() + 2)

    return if (radX < radY) radX / 2 else radY / 2
}

@Composable
fun BottomBar(model: GoLViewModel) {
    val speed = rememberSaveable {
        mutableStateOf(model.speed.value)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        IconButton(onClick = {
            if (! model.isRunning()) {
                model.start()
            }
        }) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
                tint = Color.White
            )
        }
        IconButton(onClick = {
            if (! model.isRunning()) {
                model.step()
            }
        }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Step",
                tint = Color.White
            )
        }
        IconButton(onClick = {
            model.stop()
        }) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Stop",
                tint = Color.White
            )
        }
        IconButton(onClick = {
            model.speedDown()
            speed.value = model.speed.value
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Slow",
                tint = Color.White
            )
        }
        IconButton(onClick = {
            model.speedUp()
            speed.value = model.speed.value
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowUp,
                contentDescription = "Slow",
                tint = Color.White
            )
        }
        Text(modifier=Modifier.width(40.dp), text = "${speed.value}",
            textAlign = TextAlign.End)
    }
}

@Composable
fun TopBar() {
    TopAppBar(title = { Text("GOL", color = Color.White) },
        backgroundColor = Color.Gray,
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        })
}

@Composable
fun MainView(
    modifier: Modifier, model: GoLViewModel,
    eta: MutableState<Int>
) {
    Scaffold(modifier = modifier,
        backgroundColor = Color.LightGray,
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomAppBar(backgroundColor = Color.Gray) {
                BottomBar(model = model)
            }
        }) {
        DrawUniverse(model, eta, it)
    }
}

@Composable
fun DrawUniverse(model: GoLViewModel, eta: MutableState<Int>, padding: PaddingValues) {
    val u = model.getUniverse()
    Canvas(
        modifier = Modifier
            .padding(padding)
            .padding(top = 5.dp)
            .fillMaxSize()
    ) {
        val radius = getRadius(u, size)
        val offset = getOffset(u, size, radius)
        val dx = u.dimX.toFloat()
        val dy = u.dimY.toFloat()
        drawRoundRect(
            color=Color.Gray,
            topLeft = offset,
            size = Size(
                2 * dx * radius - radius + 12,
                2 * dy * radius - radius + 12
            ),
            cornerRadius = CornerRadius(2*radius, 2*radius),
            style = Fill
        )
        drawRoundRect(
            color=Color.Blue,
            topLeft = offset,
            size = Size(
                2 * dx * radius - radius + 12,
                2 * dy * radius - radius + 12
            ),
            cornerRadius = CornerRadius(2*radius, 2*radius),
            style = Stroke(radius)
        )
        u.map.forEachIndexed { i, row ->
            row.forEachIndexed { j, value ->
                if (value != 0) {
                    val position =
                        offset + Offset(
                            i.toFloat() * radius * 2,
                            j.toFloat() * radius * 2
                        )
                    drawCircle(
                        Color.White,
                        radius = radius / 2,
                        center = position,
                        style = Fill
                    )
                    drawCircle(
                        color = Color.Red,
                        radius = radius,
                        center = position,
                        style = Stroke(1.0f)
                    )
                }
            }
        }
        val paint = Paint().asFrameworkPaint().apply {
            this.textSize = 48f
            textAlign = android.graphics.Paint.Align.CENTER
            color = Color.Blue.toArgb()
            typeface = Typeface.MONOSPACE
        }
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawText(
                "eta: ${eta.value}",
                size.width / 2,
                100f,
                paint
            )
        }
    }
}