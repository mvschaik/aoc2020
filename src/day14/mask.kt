package day14

import java.io.File

val setMask = Regex("""mask = ([X10]{36})""")
val assignment = Regex("""mem\[(\d+)] = (\d+)""")

fun write(mem: MutableMap<Long, Long>, mask: String, addr: Long, v: Long) {
  if ('X' in mask) {
    write(mem, mask.replaceFirst('X', '0'), addr, v)
    write(mem, mask.replaceFirst('X', '1'), addr, v)
  } else {
    val mask0 = mask.replace('.', '1').toLong(2)
    val mask1 = mask.replace('.', '0').toLong(2)
    mem[addr or mask1 and mask0] = v
  }
}

fun main(args: Array<String>) {
  val program = File(args[0]).readLines()
  val mem = mutableMapOf<Long, Long>()
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
      mem[addr.toLong()] = v.toLong() or mask1 and mask0
    }
  }
  println(mem.values.sum())

  mem.clear()
  var maskX = ""
  for (line in program) {
    val maskM = setMask.matchEntire(line)
    if (maskM != null) {
      val mask = maskM.groupValues[1]
      maskX = mask.replace('0', '.')
    }
    val assM = assignment.matchEntire(line)
    if (assM != null) {
      val (addr, v) = assM.destructured
      write(mem, maskX, addr.toLong(), v.toLong())
    }
  }
  println(mem.values.sum())
}