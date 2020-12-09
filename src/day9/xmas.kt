package day9

import java.io.File

fun readLines(filename: String) = File(filename).readLines()

const val WINDOW_SIZE = 25

fun main(args: Array<String>) {
  val numbers = readLines(args[0]).map{it.toLong()}

  numbers.windowed(WINDOW_SIZE).withIndex().forEach { (i, ns) ->
    if (i + WINDOW_SIZE !in numbers.indices) return
    val c = numbers[i + WINDOW_SIZE]
    val nss = ns.toSet()
    var found = false
    for (n in ns) {
      if (c - n in nss) {
        found = true
        break
      }
    }
    if (!found) {
      println(c)
    }
  }
}