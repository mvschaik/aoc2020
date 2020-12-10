package day10

import java.io.File

fun readLines(filename: String) = File(filename).readLines()

fun main(args: Array<String>) {
  val numbers = readLines(args[0]).map { it.toInt() }.sorted()

  val diffs = (listOf(0) + numbers).zip(numbers)
    .fold(mutableMapOf<Int, Int>().withDefault { 0 }) {
        diffs, (a, b) -> diffs[b - a] = diffs.getValue(b - a) + 1; diffs }
  diffs[3] = diffs.getValue(3) + 1
  println(diffs[3]!! * diffs[1]!!)
}
