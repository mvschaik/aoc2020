package day15

fun main() {
  val initial = listOf(20,0,1,11,6,3)
  val numWanted = 30000000

  val seen = mutableMapOf<Int, Int>()
  var lastNum = -1
  for (i in 0 until numWanted) {
    var el = 0
    if (i in initial.indices) {
      el = initial[i]
    } else if (lastNum in seen) {
      el = i - seen[lastNum]!!
    }
    seen[lastNum] = i
    lastNum = el
  }

  println(lastNum)
}