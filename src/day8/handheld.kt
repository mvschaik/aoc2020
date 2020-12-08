package day8

import java.io.File
import java.lang.Exception
import java.lang.RuntimeException

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


fun execute(program: List<Instruction>): Int {
  var pc = 0
  var acc = 0
  val executed = mutableSetOf<Int>()

  while (pc in program.indices) {
    executed.add(pc)
    val instruction = program[pc]
    when (instruction.op) {
      Operation.ACC -> {acc += instruction.arg; pc++}
      Operation.JMP -> pc += instruction.arg
      Operation.NOP -> pc++
    }
    if (pc in executed) {
      throw RuntimeException("Error! Loop detected. PC=$pc ACC=$acc")
    }
  }
  if (pc == program.size) {
    // Normal termination
    return acc
  }
  throw RuntimeException("Error! PC out of bounds. PC=$pc ACC=$acc")
}

fun main(args: Array<String>) {
  val program = readLines(args[0]).map(::parseInstruction)

  try {
    execute(program)
  } catch (e: Exception) {
    println(e.message)
  }

  for (i in program.indices) {
    when(program[i].op) {
      Operation.ACC -> {}
      Operation.JMP -> {
        val p = program.toMutableList()
        p[i] = program[i].copy(op = Operation.NOP)
        try { println(execute(p)) } catch (e: Exception) {}
      }
      Operation.NOP -> {
        val p = program.toMutableList()
        p[i] = program[i].copy(op = Operation.JMP)
        try { println(execute(p)) } catch (e: Exception) {}
      }
    }
  }

}