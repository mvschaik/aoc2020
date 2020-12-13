package day13

import java.io.File

fun main(args: Array<String>) {
    val lines = File(args[0]).readLines()
    val t = lines[0].toInt()
    val buses = lines[1].split(",").filter { it != "x" }.map { it.toInt() }

    val min = buses.map { it to (t / it + 1) * it - t }.minByOrNull { it.second }
    println(min!!.first * min.second)
}
