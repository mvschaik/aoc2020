package day11

import java.io.File

fun readLines(filename: String) = File(filename).readLines()

const val floor = '.'
const val empty = 'L'
const val occupied = '#'

fun print(grid: List<List<Char>>) {
  println(grid.map { it.joinToString("") }.joinToString("\n"))
}

fun adjacent(grid: List<List<Char>>, row: Int, col: Int) =
  listOf(grid[row-1][col-1], grid[row-1][col], grid[row-1][col+1],
         grid[row  ][col-1],                   grid[row  ][col+1],
         grid[row+1][col-1], grid[row+1][col], grid[row+1][col+1])


fun next(grid: List<List<Char>>): List<List<Char>> {
  val newGrid = List(grid.size) { MutableList(grid[0].size) { floor } }
  for (row in 1 until grid.size) {
    for (col in 1 until grid[0].size) {
      if (grid[row][col] == empty &&
        adjacent(grid, row, col).count { it == occupied } == 0) {
        newGrid[row][col] = occupied
      } else if (grid[row][col] == occupied &&
        adjacent(grid, row, col).count { it == occupied } >= 4) {
        newGrid[row][col] = empty
      } else {
        newGrid[row][col] = grid[row][col]
      }
    }
  }
  return newGrid
}


fun main(args: Array<String>) {
  var grid = readLines(args[0]).map { "$floor$it$floor" }.map { it.toList() }
  val empty = List(grid[0].size) { floor }
  grid = listOf(empty) + grid + listOf(empty)

  do {
    val oldGrid = grid
    grid = next(grid)
  } while (!oldGrid.equals(grid))

  println(grid.sumBy { it.count { it == occupied } })
}