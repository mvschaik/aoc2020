package day16

import java.io.File

val fieldDesc = Regex("""(.*): (\d+)-(\d+) or (\d+)-(\d+)""")

data class Field(val name: String, val ranges: List<IntRange>)
typealias Ticket = List<Int>

fun main(args: Array<String>) {
  val (fieldsPar, yourPart, nearbyPart) = File(args[0]).readText().split("\n\n")

  val fields = fieldsPar.lines().map { line ->
    val (name, s1, e1, s2, e2) = fieldDesc.matchEntire(line)!!.destructured
    Field(name, listOf(s1.toInt()..e1.toInt(), s2.toInt()..e2.toInt()))
  }

  val your = yourPart.lines()[1].split(",").map { it.toInt() }
  val nearby = nearbyPart.lines().drop(1).map { it.split(",").map { it.toInt() } }

  println(nearby.flatMap {
      ticket -> ticket.filter {
        f: Int -> fields.none {
          fd: Field -> fd.ranges.any { it.contains(f) } } } }.sum())
}