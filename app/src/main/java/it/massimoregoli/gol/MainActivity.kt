package it.massimoregoli.gol

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.lifecycle.ViewModelProvider
import it.massimoregoli.gol.model.GoLViewModel
import it.massimoregoli.gol.model.Rule
import it.massimoregoli.gol.ui.theme.GoLTheme
import java.lang.Float.min

class MainActivity : ComponentActivity() {
    lateinit var model: GoLViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this)[GoLViewModel::class.java]

        if (model.universe.value?.dimX == 0)
            model.createUniverse(70, 140, Rule("", "", "B3/S23"))

        setContent {
            val state = rememberSaveable {
                mutableStateOf(0)
            }
            model.eta.observe(this) {
                state.value = model.universe.value!!.eta
                Log.w("GOL", "ETA")
            }
            GoLTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Greeting(u = model, eta = state)
                        Universe(model, state)
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (model.paused.value == false && model.isRunning()) {
            model.pause()
        }

    }

    override fun onStart() {
        super.onStart()
        if (model.paused.value == true && model.isRunning())
            model.start() {}
    }
}


@Composable
fun Universe(model: GoLViewModel, state: MutableState<Int>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val universe = model.getUniverse()
        val radX = size.width / (universe.dimX.toFloat()+2)
        val radY = size.height / (universe.dimY.toFloat()+2)
        val radius = min(radX, radY) / 2
        val startX = (size.width - 2*universe.dimX.toFloat()*radius-radius) / 2
        val startY = (size.height - 2*universe.dimY.toFloat()*radius-radius) / 2
        drawRect(Color.Blue,
            Offset(startX-6, startY-6),
            Size(2*universe.dimX.toFloat()*radius-radius+12,
                2*universe.dimY.toFloat()*radius-radius+12),
            style = Stroke(radius))


        universe.map.forEachIndexed { i, row ->
            row.forEachIndexed { j, value ->
                if (value != 0) {
                    drawCircle(Color.Gray,
                        radius = radius/2,
                        center = Offset(startX + i.toFloat()*radius*2,
                            startY + j.toFloat()*radius*2),
                        style = Fill
                    )
                    drawCircle(color = Color.Red,
                        radius = radius,
                        center = Offset(startX + i.toFloat()*radius*2,
                            startY + j.toFloat()*radius*2),
                        style = Stroke(1.0f)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(u: GoLViewModel, eta: MutableState<Int>) {
    Text(text = "Hello ${eta.value} ${u.universe.value!!.getCell(0, 0)}!",
    modifier = Modifier.clickable {
        if (u.isRunning()) {
            u.pause()
        } else {
//            u.createUniverse(70, 140, Rule("", "", "B3/S23"))

            u.start {
//                eta.value = u.universe.value!!.eta
                Log.w("GOL", "${u.universe.value?.dimX} ${u.universe.value?.dimY} ${eta.value} ${u.universe.value!!.getCell(0, 0)}")
            }
        }
    })
}

@Composable
fun Life(model: GoLViewModel) {
}