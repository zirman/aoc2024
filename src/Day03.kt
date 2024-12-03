private typealias Input3 = List<String>

fun main() {
    fun List<String>.parse(): Input3 = this

    fun Input3.part1(): Int {
        val mulRegex = """mul\((\d+),(\d+)\)""".toRegex()
        var sum = 0
        forEach { line ->
            var match = mulRegex.find(line)
            while (match != null) {
                val (a, b) = match.destructured
                match = match.next()
                sum += a.toInt() * b.toInt()
            }
        }
        return sum
    }

    fun Input3.part2(): Int {
        val mulRegex = """(mul|do|don't)\((?:(\d+),(\d+))?\)""".toRegex()
        var doMul = true
        var sum = 0
        forEach { line ->
            var match = mulRegex.find(line)
            while (match != null) {
                val (a, b, c) = match.destructured
                when (a) {
                    "do" -> {
                        doMul = true
                    }

                    "don't" -> {
                        doMul = false
                    }

                    "mul" -> {
                        if (doMul) {
                            sum += b.toInt() * c.toInt()
                        }
                    }

                    else -> {
                        throw IllegalStateException()
                    }
                }
                match = match.next()
            }
        }
        return sum
    }

    val testInput = """
        xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 161)
    val input = readInput("Day03").parse()
    input.part1().println()
    val testInput2 = """
        xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
    """.trimIndent().split('\n').parse()
    check(testInput2.part2() == 48)
    input.part2().println()
}
