package it.massimoregoli.gol.model

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pattern(val s: String): Parcelable {
    @IgnoredOnParcel
    var dimX: Int = 0
    @IgnoredOnParcel
    var dimY: Int = 0
    @IgnoredOnParcel
    private var rule: String = ""
    @IgnoredOnParcel
    private var string: String = ""
    @IgnoredOnParcel
    var map = emptyArray<Array<Int>>()

    init {
        val rows = s.split("\n")
        for (r in rows) {
            if (r[0] != '#') {
                if (r[0] == 'x') {
                    val cols = r.split(",")
                    for (c in cols) {
                        val (k, v) = c.trim().split("=")
                        when(k.trim())  {
                            "x" -> dimX = v.trim().toInt()
                            "y" -> dimY = v.trim().toInt()
                            "rule" -> rule = v.trim()
                        }
                    }
                } else {
                    string += r.trim()
                }
            }
        }
        map = Array(dimX) { Array(dimY) { 0 } }
        if (string != "") {
            createPattern(string)
        }
    }

    private fun createPattern(glider:String) {
        var dx = 0
        var dy = 0
        var rep = 0

        for (c in glider) {
            when(c) {
                'o' -> {
                    if (rep == 0)
                        rep = 1
                    repeat(rep) {
                        map[dx][dy] = 1
                        dx += 1
                    }
                    rep = 0
                }
                'b' ->  {
                    if (rep == 0)
                        rep = 1
                    dx += rep
                    rep = 0
                }
                in '0'..'9' -> {
                    rep = rep * 10 + c.digitToInt(10)
                }
                '$' -> {
                    if (rep == 0)
                        rep = 1
                    dy += rep
                    dx = 0
                    rep = 0
                }
            }
        }
        Log.w("GOL", "DONE GLIDER")
    }
}