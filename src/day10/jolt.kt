package day10

import java.io.File

fun readLines(filename: String) = File(filename).readLines()

fun main(args: Array<String>) {
  val input = readLines(args[0]).map { it.toInt() }.sorted()
  val numbers = listOf(0) + input + listOf(input.last() + 3)

  val diffs = numbers.zip(numbers.drop(1))
    .fold(mutableMapOf<Int, Int>().withDefault { 0 }) {
        diffs, (a, b) -> diffs[b - a] = diffs.getValue(b - a) + 1; diffs }
  println(diffs[3]!! * diffs[1]!!)

  val waysToGetTo = MutableList(numbers.size) { 0L }
  waysToGetTo[0] = 1
  for ((i, n) in numbers.withIndex()) {
    if (i > 0 && n - numbers[i-1] <= 3) waysToGetTo[i] += waysToGetTo[i-1]
    if (i > 1 && n - numbers[i-2] <= 3) waysToGetTo[i] += waysToGetTo[i-2]
    if (i > 2 && n - numbers[i-3] <= 3) waysToGetTo[i] += waysToGetTo[i-3]
  }
  println(waysToGetTo.last())
}
