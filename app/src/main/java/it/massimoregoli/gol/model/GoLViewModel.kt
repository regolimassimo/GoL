package it.massimoregoli.gol.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class GoLViewModel : ViewModel() {
//    val mapLiveData: LiveData<SnapshotStateList<Array<Int>>>
//        get() = map
//    private val map = MutableLiveData<SnapshotStateList<Array<Int>>>()
//    private var mapImpl = mutableStateListOf<Array<Int>>()
    var universe = MutableLiveData(Universe())
    var eta = MutableLiveData(0)
    private var paused = MutableLiveData(false)
    var speed = MutableLiveData(100L)
    private val state = MutableLiveData(0)

    fun getUniverse(): Universe {
        return universe.value!!
    }

//    private fun postUniverse() {
//        Log.w("GOL2", "START POST")
//        try {
//            mapImpl.clear()
//            for (ints in universe.value!!.map) {
//                mapImpl.add(ints)
//            }
//            map.postValue(mapImpl)
//        } catch (e: IndexOutOfBoundsException) {
//            Log.e("GOL", e.toString())
//        }
//        Log.w("GOL2", "END POST")
//    }

    private fun createUniverse(dx: Int, dy: Int, r: Rule) {
        universe.value = Universe.createUniverse(dx, dy, r)
//        postUniverse()
        eta.value = 0
    }

    fun speedDown() {
        speed.value = speed.value?.plus(10L)
    }

    fun speedUp() {
        if (speed.value!! > 10)
            speed.value = speed.value?.minus(10L)
    }

    fun start() {
        if (universe.value != null) {
            state.value = 1
            paused.value = false

            CoroutineScope(Dispatchers.Default).launch {

                while (state.value == 1 && paused.value == false) {
                    runBlocking {
                        evolve()
                    }
                    Thread.sleep(speed.value!!.toLong())
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

    private fun evolve() {
        universe.value!!.next()
//        postUniverse()
        CoroutineScope(Dispatchers.Main).launch {
            eta.value = eta.value?.plus(1)
        }
    }

    fun step() {
        if (universe.value != null) {
            CoroutineScope(Dispatchers.IO).launch {
                evolve()
            }
        }
    }

    init {
        createUniverse(60, 120, Rule("", "", "B3/S23"))
    }
}