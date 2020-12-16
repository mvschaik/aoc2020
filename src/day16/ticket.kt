package day16

import java.io.File

val fieldDesc = Regex("""(.*): (\d+)-(\d+) or (\d+)-(\d+)""")

data class FieldDesc(val name: String, val ranges: List<IntRange>)

fun main(args: Array<String>) {
  val (fieldsPar, yourPart, nearbyPart) = File(args[0]).readText().split("\n\n")

  val fieldDescriptors = fieldsPar.lines().map { line ->
    val (name, s1, e1, s2, e2) = fieldDesc.matchEntire(line)!!.destructured
    FieldDesc(name, listOf(s1.toInt()..e1.toInt(), s2.toInt()..e2.toInt()))
  }

  val your = yourPart.lines()[1].split(",").map { it.toInt() }
  val nearby = nearbyPart.lines().drop(1).map { it.split(",").map { it.toInt() } }

  println(nearby.flatMap {
      ticket -> ticket.filter {
        f -> fieldDescriptors.none {
          fd -> fd.ranges.any { it.contains(f) } } } }.sum())

  val valid = nearby.filter {
      ticket -> ticket.none {
        f -> fieldDescriptors.none {
          fd -> fd.ranges.any { it.contains(f) } } } }

  val fields = arrayOfNulls<FieldDesc>(fieldDescriptors.size)
  var fieldValues = valid.fold(List(your.size) { emptyList<Int>() }) {
      acc, list -> acc.zip(list) { l, el -> l + el } }
    .withIndex()
  val descriptorsLeft = fieldDescriptors.toMutableSet()

  while (fields.contains(null)) {
    val matches = fieldValues.map { values ->
      values.index to descriptorsLeft.filter {
          fd -> values.value.all { value -> fd.ranges.any { range -> range.contains(value) } } }
    }.filter { it.second.size == 1 }.map { it.first to it.second.single() }

    if (matches.isEmpty()) break

    for ((index, desc) in matches) {
      fields[index] = desc
      fieldValues = fieldValues.filter { it.index != index }
      descriptorsLeft.remove(desc)
    }
  }

  println(fields.withIndex()
    .filter { it.value!!.name.startsWith("departure") }
    .map { your[it.index].toLong() }
    .reduce { a, b -> a * b })
}