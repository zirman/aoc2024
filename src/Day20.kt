private typealias Input20 = List<String>

fun main() {
    fun List<String>.parse(): Input20 {
        return this
    }

    fun Input20.part1(): Int {
        TODO()
    }

    fun Input20.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day20").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
