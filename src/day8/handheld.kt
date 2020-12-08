package day8

import java.io.File

enum class Operation {
  ACC, JMP, NOP
}

data class Instruction(val op: Operation, val arg: Int)

fun readLines(filename: String) = File(filename).readLines()

fun parseInstruction(instruction: String): Instruction {
  val (op, arg) = instruction.split("\\s+".toRegex())
  return Instruction(when (op) {
    "acc" -> Operation.ACC
    "jmp" -> Operation.JMP
    "nop" -> Operation.NOP
    else -> error("Invalid instruction $op")
  }, arg.toInt())
}

fun main(args: Array<String>) {
  val program = readLines(args[0]).map(::parseInstruction)
  var pc = 0
  var acc = 0
  val executed = mutableSetOf<Int>()

  while (pc !in executed) {
    executed.add(pc)
    val instruction = program[pc]
    println("pc=$pc acc=$acc -> $instruction")
    when (instruction.op) {
      Operation.ACC -> {acc += instruction.arg; pc++}
      Operation.JMP -> pc += instruction.arg
      Operation.NOP -> pc++
    }
  }
  println("Error! Loop detected. PC=$pc ACC=$acc")
}