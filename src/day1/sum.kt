package day1

import java.io.File

fun readLines(filename: String) = File(filename).readLines()

fun main(args: Array<String>) {
    val numbers = readLines(args[0]).map{it.toInt()}.toSet()
    val target = 2020

    findPair(numbers, target)?.let {
        println(it.toString() + " => " + it.first * it.second)
    }

    for (n in numbers) {
        findPair(numbers, target - n)?.let {
            println(n.toString() + ", " + it + " => " + n * it.first * it.second)
        }
    }
}

fun findPair(numbers: Set<Int>, target: Int): Pair<Int, Int>? {
    for (n in numbers) {
        if (target - n in numbers) {
            return Pair(n, target - n)
        }
    }
    return null
}