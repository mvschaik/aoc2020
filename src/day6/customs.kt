package day6

import java.io.File

fun main(args: Array<String>) {
  println(
    File(args[0]).readText().split("\n\n")
      .map {
        it.lines()
          .fold(('a'..'z').toSet()) { r, s -> r.intersect(s.asIterable()) } }
      .map { it.size }
      .sum())
}