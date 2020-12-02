package day2

import java.io.File

fun readLines(filename: String) = File(filename).readLines()

val lineFormat = """(\d+)-(\d+)\s(\w): (\w+)""".toRegex()

fun main(args: Array<String>) {
  println(readLines(args[0]).filter { line ->
    val matchResult = lineFormat.find(line)
    val (min, max, letter, password) = matchResult!!.destructured
    val count = password.count { it == letter.toCharArray()[0] }
    min.toInt() <= count && count <= max.toInt()
  }.count())

  println(readLines(args[0]).filter { line ->
    val matchResult = lineFormat.find(line)
    val (first, second, letter, password) = matchResult!!.destructured
    (password[first.toInt() - 1] == letter.toCharArray()[0]) xor (password[second.toInt() - 1] == letter.toCharArray()[0])
  }.count())
}