package day3

import java.io.File

fun readLines(filename: String) = File(filename).readLines()

data class Slope(val right: Int, val down: Int)

data class Position(val x: Int, val y: Int) {
  fun move(s: Slope) = Position(x + s.right, y + s.down)
}

class Area(private val lines: List<String>) {
  private val width = lines[0].length
  val height = lines.size
  fun isTree(x: Int, y: Int) = lines[y][x % width] == '#'
  fun isTree(p: Position) = isTree(p.x, p.y)
  fun contains(p: Position) = p.y < height

  fun numberOfTrees(s: Slope): Long {
    var p = Position(0, 0)
    var count = 0
    do {
      if (isTree(p)) {
        count++
      }
      p = p.move(s)
    } while (contains(p))
    return count.toLong()
  }
}

fun main(args: Array<String>) {
  val area = Area(readLines(args[0]))

  println(area.numberOfTrees(Slope(3, 1)))

  println(area.numberOfTrees(Slope(1, 1)) *
            area.numberOfTrees(Slope(3, 1)) *
            area.numberOfTrees(Slope(5, 1)) *
            area.numberOfTrees(Slope(7, 1)) *
            area.numberOfTrees(Slope(1, 2)))
}
