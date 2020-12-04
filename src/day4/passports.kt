package day4

import java.io.File

fun collectFields(line: String) =
  line.split("\\s+".toRegex()).map { it.split(":".toRegex())[0] }.filter { it.isNotEmpty() }

val requiredFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" /*, "cid" */)

fun main(args: Array<String>) {
  val records = File(args[0]).readText().split("\n\n").map { collectFields(it) }
  val validRecords = records.filter { it.toSet().containsAll(requiredFields) }

  println(validRecords.size)
}