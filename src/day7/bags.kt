package day7

import java.io.File

fun readLines(filename: String) = File(filename).readLines()

val bagsLine = """([a-z ]+) bags contain (no other bags|(\d+ ([a-z ]+?) bags?(, )?)+)\.""".toRegex()
val containsPart = """(\d+) ([a-z ]+?) bags?(, )?""".toRegex()

fun parseRule(rule: String): Pair<String, List<Pair<Int, String>>> {
  val m = bagsLine.matchEntire(rule)
  val parts = m?.groupValues!!
  val color = parts[1]
  val contain = containsPart.findAll(parts[2])
  return color to contain.map {
    val (count, color) = it.destructured
    count.toInt() to color
  }.toList()
}

fun main(args: Array<String>) {
  val rules = readLines(args[0]).map(::parseRule)

  val containment = rules.flatMap { container ->
    container.second.map { contained -> contained.second to container.first }
  }.groupingBy { it.first }.fold(setOf<String>()) {acc, (_, v) -> acc + v }

  fun containers(s: String): Set<String> = containment[s].orEmpty()
  fun allContainers(s: String): Set<String> = containers(s) + containers(s).flatMap { allContainers(it) }
  println(allContainers("shiny gold").size)

  val contains = rules.toMap()
  fun allContains(s: String): Int =
    1 + contains[s].orEmpty().fold(0) { total, (count, color) -> total + count * allContains(color) }
  println(allContains("shiny gold") - 1)
}