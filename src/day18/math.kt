package day18

import java.io.File


class Parser(val expression: String) {
  var pos = 0

  fun expr() = mult()

  fun skipWhite() {
    while (pos in expression.indices && expression[pos].isWhitespace()) pos++
  }

  fun mult(): Long {
    val a_expr = add()
    skipWhite()
    if (pos in expression.indices && expression[pos] == '*') {
      pos++
      skipWhite()
      return a_expr * mult()
    }
    return a_expr
  }

  fun add(): Long {
    val n_expr = num()
    skipWhite()
    if (pos in expression.indices && expression[pos] == '+') {
      pos++
      skipWhite()
      return n_expr + add()
    }
    return n_expr
  }

  fun num(): Long {
    val start = pos
    while (pos in expression.indices && (expression[pos].isDigit() || expression[pos] == '(')) {
      pos++
      if (expression[pos-1] == '(') {
        val e_expr = expr()
        pos++
        return e_expr
      }
    }
    return expression.substring(start until pos).toLong()
  }
}

fun evaluate(expression: String) = Parser(expression).expr()

fun main(args: Array<String>) {
  println(File(args.first()).readLines().map(::evaluate).sum())
}