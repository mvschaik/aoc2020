package day14

import java.io.File

val setMask = Regex("""mask = ([X10]{36})""")
val assignment = Regex("""mem\[(\d+)] = (\d+)""")

fun main(args: Array<String>) {
  val program = File(args[0]).readLines()
  val mem = mutableMapOf<Int, Long>()
  var mask0 = 0L
  var mask1 = 0L
  for (line in program) {
    val maskM = setMask.matchEntire(line)
    if (maskM != null) {
      val mask = maskM.groupValues[1]
      mask0 = mask.replace('X', '1').toLong(2)
      mask1 = mask.replace('X', '0').toLong(2)
    }
    val assM = assignment.matchEntire(line)
    if (assM != null) {
      val (addr, v) = assM.destructured
      mem[addr.toInt()] = v.toLong() or mask1 and mask0
    }
  }
  println(mem.values.sum())
}