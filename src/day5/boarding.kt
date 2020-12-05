package day5

import java.io.File

fun readLines(filename: String) = File(filename).readLines()

fun decodeSeat(seat: String): Pair<Int, Int> {
  val rowPart = seat.take(7).fold(0, {n, c -> (n shl 1) + (when (c) {'B' -> 1; else -> 0 } )})
  val seatPart = seat.drop(7).fold(0, {n, c -> (n shl 1) + (when (c) {'R' -> 1; else -> 0 } )})
  return Pair(rowPart, seatPart)
}

fun seatId(seat: Pair<Int, Int>) = seat.first * 8 + seat.second

fun main(args: Array<String>) {
  println(decodeSeat("FBFBBFFRLR") == Pair(44, 5))
  println(seatId(decodeSeat("BFFFBBFRRR")) == 567)
  println(seatId(decodeSeat("FFFBBBFRRR")) == 119)
  println(seatId(decodeSeat("BBFFBBFRLL")) == 820)

  println(readLines(args[0]).map(::decodeSeat).map(::seatId).max())
}