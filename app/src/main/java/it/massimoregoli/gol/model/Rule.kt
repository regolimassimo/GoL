package it.massimoregoli.gol.model

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rule(
    var name: String,
    var description: String,
    var rule: String
) : Parcelable {
    @IgnoredOnParcel
    var intRule = arrayOf(arrayOf(0,0,0,0,0,0,0,0,0), arrayOf(0,0,0,0,0,0,0,0,0))

    init {
        val f = rule.split("/")
        if (f.size != 2) {
            Log.w("GOL", "Errore")
        } else {
            for (i in 0..1) {
                if (f[i][0] != 'B' && f[i][0] != 'S') {
                    Log.w("GOL", "Errore")
                }
                for (c in f[i].slice(1 until f[i].length)) {
                    if (f[i][0] == 'B')
                        intRule[0][c.digitToInt()] = 1
                    else
                        intRule[1][c.digitToInt()] = 1
                }
            }
        }
    }
}