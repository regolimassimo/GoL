@file:Suppress("SpellCheckingInspection")

package it.massimoregoli.gol.util

import it.massimoregoli.gol.model.Pattern

val GOSPEL = Pattern("#N Gosper glider gun_synth\n" +
        "#C Glider synthesis of Gosper glider gun.\n" +
        "#C www.conwaylife.com/wiki/index.php?title=Gosper_glider_gun\n" +
        "x = 47, y = 14, rule = b3/s23\n" +
        "16bo30b\$16bobo16bo11b\$16b2o17bobo9b\$obo10bo21b2o10b\$b2o11b2o31b\$bo11b\n" +
        "2o32b3\$10b2o20b2o13b\$11b2o19bobo9b3o\$10bo21bo11bo2b\$27bo17bob\$27b2o18b\n" +
        "\$26bobo!")

val WINGS = Pattern("x = 12, y = 12, rule = B3/S23\n" +
        "7b3o\$7bo\$8bo\$10b2o\$9b2o2\$8bo\$3o5bo2bo\$o7bo\$bo2b5obo\$3bo\$4b2o!")
val CONWAY = Pattern("x = 10, y = 10, rule = B3/S23\n" +
        "2b3o\$2bobo\$2bobo\$3bo\$ob3o\$bobobo\$3bo2bo\$2bobo\$2bobo!")

val DINNER_TABLE = Pattern("x = 13, y = 13, rule = b3/s23\n" +
        "bo11b\$b3o7b2o\$4bo6bob\$3b2o4bobob\$9b2o2b\$6bo6b\$4b2obo5b2\$2bo3bo2bo3b\$bo\n" +
        "b2o4bo3b\$bo6bo4b\$2o7b3ob\$11bo!")
val FOUR_BOATS = Pattern("x = 8, y = 8, rule = B3/S23\n" +
        "3bo4b\$2bobo3b\$bob2o3b\$obo2b2ob\$b2o2bobo\$3b2obob\$3bobo2b\$4bo!")
val BICLOCK = Pattern("#N Bi-clock\n" +
        "#C A pure glider generator made up of two clocks.\n" +
        "#C www.conwaylife.com/wiki/index.php?title=Bi-clock\n" +
        "x = 7, y = 7, rule = B3/S23\n" +
        "2bo4b\$2o5b\$2b2o3b\$bo3bob\$3b2o2b\$5b2o\$4bo!")
val DEFAULT_GLIDER = BICLOCK
val GLIDERS = listOf(GOSPEL, WINGS, CONWAY, DINNER_TABLE, FOUR_BOATS, BICLOCK)

val ISATORO = true