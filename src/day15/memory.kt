package day15

fun main() {
  val initial = listOf(20,0,1,11,6,3)
  val elements = mutableListOf(-1)
  val seen = mutableMapOf<Int, Int>()
  (1 .. 2020).forEach { i ->
    val lastNum = elements[i-1]
    var el = 0
    if (i-1 in initial.indices) {
      el = initial[i-1]
    } else if (lastNum in seen) {
      el = i - 1 - seen[lastNum]!!
    }
    seen[lastNum] = i - 1
    elements.add(el)
  }

  println(elements[2020])
}