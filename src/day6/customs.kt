package day6

import java.io.File

fun main(args: Array<String>) {
  println(
    File(args[0]).readText().split("\n\n")
      .map{ it.toSet().filter(Char::isLetter) }
      .map{ it.size }
      .sum())
}