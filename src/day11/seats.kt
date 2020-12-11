package day11

import java.io.File

const val floor = '.'
const val empty = 'L'
const val occupied = '#'

typealias Grid = List<List<Char>>

fun readGrid(filename: String): Grid {
  val grid = File(filename).readLines().map { "$floor$it$floor" }.map { it.toList() }
  val empty = List(grid[0].size) { floor }
  return listOf(empty) + grid + listOf(empty)
}

fun print(grid: Grid) {
  println(grid.joinToString("\n") { it.joinToString("") })
}

fun adjacent(grid: Grid, row: Int, col: Int) =
  listOf(grid[row-1][col-1], grid[row-1][col], grid[row-1][col+1],
         grid[row  ][col-1],                   grid[row  ][col+1],
         grid[row+1][col-1], grid[row+1][col], grid[row+1][col+1])

fun visible(grid: Grid, row: Int, col: Int): List<Char> {
  val dirs = listOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
                    Pair( 0, -1),              Pair( 0, 1),
                    Pair( 1, -1), Pair( 1, 0), Pair( 1, 1))
  return dirs.map {
    var r = row + it.first
    var c = col + it.second
    while (r in grid.indices && c in grid[0].indices) {
      if (grid[r][c] != floor) return@map grid[r][c]
      if (grid[r][c] != floor) return@map grid[r][c]
      r += it.first
      c += it.second
    }
    return@map floor
  }
}

fun next(grid: Grid, fn: (Grid, Int, Int) -> List<Char>, tooManyPeople: Int): Grid {
  val newGrid = List(grid.size) { MutableList(grid[0].size) { floor } }
  for (row in 1 until grid.size) {
    for (col in 1 until grid[0].size) {
      if (grid[row][col] == empty &&
        fn(grid, row, col).count { it == occupied } == 0) {
        newGrid[row][col] = occupied
      } else if (grid[row][col] == occupied &&
        fn(grid, row, col).count { it == occupied } >= tooManyPeople) {
        newGrid[row][col] = empty
      } else {
        newGrid[row][col] = grid[row][col]
      }
    }
  }
  return newGrid
}


fun main(args: Array<String>) {
  var grid = readGrid(args[0])
  do {
    val oldGrid = grid
    grid = next(grid, ::adjacent, tooManyPeople = 4)
  } while (oldGrid != grid)

  println(grid.sumBy { it.count { it == occupied } })

  grid = readGrid(args[0])
  do {
    val oldGrid = grid
    grid = next(grid, ::visible, tooManyPeople = 5)
  } while (oldGrid != grid)

  println(grid.sumBy { it.count { it == occupied } })
}