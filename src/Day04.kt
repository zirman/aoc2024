private typealias Input4 = List<String>

fun main() {
    fun List<String>.parse(): Input4 {
        return this
    }

    fun Input4.part1(): Int {
        TODO()
    }

    fun Input4.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day04").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
