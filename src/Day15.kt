private typealias Input15 = List<String>

fun main() {
    fun List<String>.parse(): Input15 {
        return this
    }

    fun Input15.part1(): Int {
        TODO()
    }

    fun Input15.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day15").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
