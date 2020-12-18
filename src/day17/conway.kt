package day17

import java.io.File

data class Position(val x: Int, val y: Int, val z: Int, val w: Int)
typealias World = Map<Position, Int>

fun surrounding(world: World, pos: Position) =
    (pos.x - 1..pos.x + 1).flatMap { x ->
        (pos.y - 1..pos.y + 1).flatMap { y ->
            (pos.z - 1..pos.z + 1).flatMap { z ->
                (pos.w - 1..pos.w + 1).map { w -> world.getValue(Position(x, y, z, w)) }
            }
        }
    }.sum() - world.getValue(pos)

fun next(world: World): World {
    fun range(selector: (Position) -> Int) =
        (world.keys.minOf(selector) - 1)..(world.keys.maxOf(selector) + 1)

    return range { it.x }.flatMap { x ->
        range { it.y }.flatMap { y ->
            range { it.z }.flatMap { z ->
                range { it.w }.map { w ->
                    val pos = Position(x, y, z, w)
                    val value = world.getValue(pos)
                    pos to when (value) {
                        1 -> if (surrounding(world, pos) in 2..3) 1 else 0
                        0 -> if (surrounding(world, pos) == 3) 1 else 0
                        else -> value
                    }
                }
            }
        }
    }
        .filter { it.second == 1 }
        .toMap().withDefault { 0 }
}

fun main(args: Array<String>) {
    var world = File(args.first()).readLines().withIndex().flatMap { (row, line) ->
        line.withIndex().map { (col, char) -> Position(col, row, 0, 0) to (if (char == '#') 1 else 0) }
    }
        .filter { it.second == 1 }
        .toMap()
        .withDefault { 0 }

    repeat(6) {
        world = next(world)
    }
    println(world.values.sum())
}
