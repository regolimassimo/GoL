package it.massimoregoli.gol.model

import it.massimoregoli.gol.util.CONWAY
import it.massimoregoli.gol.util.GOSPEL
import kotlin.random.Random

class Universe {
    var map = emptyArray<Array<Int>>()
    var newMap = emptyArray<Array<Int>>()
    var dimX: Int = 0
    var dimY: Int = 0
    var rule: Rule = Rule("", "", "B3/S23")
    var eta: Int = 0

    fun setCell(i: Int, j: Int, s: Int) {
        map[i][j] = s
    }

    companion object {
        fun createUniverse(dx: Int, dy: Int, r: Rule): Universe {
            val ret = Universe()
            ret.dimX = dx
            ret.dimY = dy
            ret.rule = r
            ret.eta = 0
            ret.map = Array(dx) { Array(dy) { 0 } }
            ret.newMap = Array(dx) { Array(dy) { 0 } }
// Just for fun
            val glider = GOSPEL
            ret.addGlider(2,51, glider)
            val conway = CONWAY
            ret.addGlider(dx/2, dy-10, conway)

            return ret
        }
    }

    fun getCell(i: Int, j: Int): Int {
        return map[i][j]
    }

    fun next() {
        for (i in map.indices) {
            for (j in map[i].indices) {
                newMap[i][j] = 0
                val alive = countAliveNeighbors(i, j)
                newMap[i][j] = rule.intRule[map[i][j]][alive]
            }
        }

        for (i in map.indices) {
            for (j in map[i].indices) {
                map[i][j] = newMap[i][j]
            }
        }
        eta += 1
    }

    fun createRandomMap() {
        for (i in map.indices) {
            for (j in map[i].indices) {
                map[i][j] = if (Random.nextInt(0, 10) > 0) 0 else 1
            }
        }
    }

    fun addGlider(x: Int, y: Int, glider:Pattern) {
        for (i in 0 until glider.dimX) {
            for (j in 0 until glider.dimY) {
                map[x+i][y+j] = glider.map[i][j]
            }
        }
    }

    private fun countAliveNeighbors(xPos: Int, yPos: Int): Int {
        var alive = 0
        for (i in -1..1) {
            val x = xPos + i
            if (x in 0 until dimX) {
                for (j in -1..1) {
                    if (i == 0 && j == 0)
                        continue
                    val y = yPos + j
                    if (y in 0 until dimY) {
                        alive += if (map[x][y] == 0) 0 else 1
                    }
                }
            }
        }
        return alive
    }
}
