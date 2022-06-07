package it.massimoregoli.gol.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoLViewModel: ViewModel() {

    var universe = MutableLiveData(Universe())
    var eta = MutableLiveData(0)
    var paused = MutableLiveData(false)
    private val state = MutableLiveData(0)

    fun getUniverse(): Universe {
        return universe.value!!
    }

    fun createUniverse(dx: Int, dy: Int, r: Rule) {
        universe.value = Universe.createUniverse(dx, dy, r)
        eta.value = 0
    }

    fun next() {
        paused.value = false
        CoroutineScope(Dispatchers.IO).launch {
            if (universe.value != null) {
                universe.value!!.next()
            }
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    fun start(onStep: () -> Unit) {
        if (universe.value != null) {
            state.value = 1
            paused.value = false
            CoroutineScope(Dispatchers.IO).launch {
                while (state.value == 1 && paused.value == false) {
                    universe.value!!.next()
                    CoroutineScope(Dispatchers.Main).launch {
                        onStep()
                        eta.value = eta.value?.plus(1)
                    }
                    Thread.sleep(100)
                }
            }
        }
    }

    fun isRunning(): Boolean {
        return state.value == 1
    }

    fun stop() {
        state.value = 0
    }

    fun pause() {
        paused.value = true
    }
}