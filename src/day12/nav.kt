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
    fun left(degrees: Int): Vector = when (degrees) {
        90 -> Vector(-y, x)
        else -> left(degrees - 90).left(90)
    }
    fun right(degrees: Int): Vector = when (degrees) {
        90 -> Vector(y, -x)
        else -> right(degrees - 90).right(90)
    }
}

data class Position(val x: Int, val y: Int) {
    operator fun plus(v: Vector) = Position(x + v.x, y + v.y)

}

class Ship {
    var position = Position(0, 0)
    var direction = Vector(-1, 0)

    fun process(instruction: Instruction) {
        when (instruction.action) {
            'F' -> position += direction * instruction.value
            'N' -> position += north * instruction.value
            'S' -> position += south * instruction.value
            'E' -> position += east * instruction.value
            'W' -> position += west * instruction.value
            'L' -> direction = direction.left(instruction.value)
            'R' -> direction = direction.right(instruction.value)
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
