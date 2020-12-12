package day12

import java.io.File
import kotlin.math.absoluteValue

fun readFile(filename: String) = File(filename).readLines().map(::Instruction)

class Instruction(i: String) {
    val action = i[0]
    val value = i.drop(1).toInt()
}

val east = Vector(-1, 0)
val west = Vector(1, 0)
val north = Vector(0, -1)
val south = Vector(0, 1)

data class Vector(val x: Int, val y: Int) {
    operator fun times(i: Int) = Vector(x * i, y * i)
    operator fun plus(v: Vector) = Vector(x + v.x, y + v.y)

    fun left(degrees: Int): Vector = when (degrees) {
        90 -> Vector(-y, x)
        else -> left(degrees - 90).left(90)
    }

    fun right(degrees: Int): Vector = when (degrees) {
        90 -> Vector(y, -x)
        else -> right(degrees - 90).right(90)
    }
}

class Ship {
    var position = Vector(0, 0)
    var waypoint = Vector(-10, -1)

    fun process(instruction: Instruction) {
        when (instruction.action) {
            'F' -> position += waypoint * instruction.value
            'N' -> waypoint += north * instruction.value
            'S' -> waypoint += south * instruction.value
            'E' -> waypoint += east * instruction.value
            'W' -> waypoint += west * instruction.value
            'L' -> waypoint = waypoint.left(instruction.value)
            'R' -> waypoint = waypoint.right(instruction.value)
        }
    }

    fun manhattan() = position.x.absoluteValue + position.y.absoluteValue
}

fun main(args: Array<String>) {
    val instructions = readFile(args[0])

    val ship = Ship()
    for (i in instructions) {
        ship.process(i)
    }
    println(ship.manhattan())
}
