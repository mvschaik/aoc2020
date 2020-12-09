package day9

import java.io.File

fun readLines(filename: String) = File(filename).readLines()

const val WINDOW_SIZE = 25

fun main(args: Array<String>) {
  val numbers = readLines(args[0]).map{it.toLong()}

  val keys = numbers.windowed(WINDOW_SIZE).withIndex().filter { (i, ns) ->
    if (i + WINDOW_SIZE !in numbers.indices) return@filter false
    val c = numbers[i + WINDOW_SIZE]
    val nss = ns.toSet()
    for (n in ns) {
      if (c - n in nss) {
        return@filter false
      }
    }
    return@filter true
  }

  val key = numbers[keys.first().index + WINDOW_SIZE]
  println(key)

  val prefixSums = numbers.indices.map { numbers.drop(it).sum() }
  fun s(from: Int, to: Int) = prefixSums[from] - prefixSums.getOrElse(to + 1) { 0 }

  val indices = numbers.indices.flatMap { from -> numbers.indices.map { from to it } }.filter { it.first < it.second }
  val longestRange = indices.filter { s(it.first, it.second) == key }.maxBy { it.second - it.first }
  val solution = numbers.subList(longestRange!!.first, longestRange.second)
  println(solution.min()!! + solution.max()!!)
}