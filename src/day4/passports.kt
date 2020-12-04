package day4

import java.io.File

fun collectFields(line: String) =
  line.split("\\s+".toRegex())
    .filter { it.isNotEmpty() }
    .map { it.split(":".toRegex()) }
    .map { it[0] to it[1] }.toMap()

val validators = mapOf(
  "byr" to { s: String -> s.matches("\\d{4}".toRegex()) && s.toInt() in 1920 .. 2002 },
  "iyr" to { s -> s.matches("\\d{4}".toRegex()) && s.toInt() in 2010 .. 2020 },
  "eyr" to { s -> s.matches("\\d{4}".toRegex()) && s.toInt() in 2020 .. 2030 },
  "hgt" to { s ->
    val match = "(\\d+)(cm|in)".toRegex().matchEntire(s)
    when (match?.groupValues?.get(2)) {
      "cm" -> match.groupValues[1].toInt() in 150..193
      "in" -> match.groupValues[1].toInt() in 59..76
      else -> false
    }
  },
  "hcl" to { s -> s.matches("#[0-9a-f]{6}".toRegex()) },
  "ecl" to { s -> s.matches("(amb|blu|brn|gry|grn|hzl|oth)".toRegex()) },
  "pid" to { s -> s.matches("\\d{9}".toRegex())}
)

fun main(args: Array<String>) {
  val records = File(args[0]).readText().split("\n\n").map(::collectFields)

  val validRecords = records.filter {
    validators.all { v -> it.containsKey(v.key) && v.value(it.getValue(v.key)) }
  }

  println(validRecords.size)
}