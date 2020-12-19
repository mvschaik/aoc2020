package day19

import java.io.File

val LINE = Regex("""(\d+): (.*)""")
val TOKEN = Regex(""""(\w)"""")

fun parse(patterns: Map<Int, String>, pattern: String): String {
    val tokenMatcher = TOKEN.matchEntire(pattern)
    if (tokenMatcher != null) {
        return tokenMatcher.groupValues[1]
    }
    var parts = pattern.split(Regex("""\s+"""))
    val r = mutableListOf<String>()
    do {
        val p = parts.takeWhile { it != "|" }.map { parse(patterns, patterns.getValue(it.toInt())) }
        r.add(p.joinToString(""))
        parts = parts.drop(p.size + 1)
    } while (parts.isNotEmpty())
    return "(" + r.joinToString("|") + ")"
}

fun main(args: Array<String>) {
    val (patPart, msgPart) = File(args.first()).readText().split("\n\n")

    val patterns = patPart.lines().map { LINE.matchEntire(it)!!.destructured }.map { (n, exp) -> n.toInt() to exp }.toMap()
    val r = Regex(parse(patterns, patterns.getValue(0)))
    println(msgPart.lines().filter { r.matches(it) }.size)
}
