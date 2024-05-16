package it.massimoregoli.gol

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import it.massimoregoli.gol.model.GoLViewModel
import it.massimoregoli.gol.screens.MainView
import it.massimoregoli.gol.ui.theme.GoLTheme

class MainActivity : ComponentActivity() {
    private lateinit var model: GoLViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this)[GoLViewModel::class.java]

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
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column {
                        MainView(
                            modifier = Modifier,
                            model = model, eta = state)
                    }
                }
            }
        }
    }

    override fun onStop() {
        model.pause()
        Log.w("GOL", "PAUSED")
        super.onStop()
    }

    override fun onStart() {
        if (model.isRunning()) {
            model.start()
            Log.w("GOL", "RESTARTED")
        }

        super.onStart()
    }

}
