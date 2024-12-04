private typealias Input12 = List<String>

fun main() {
    fun List<String>.parse(): Input12 {
        return this
    }

    fun Input12.part1(): Int {
        TODO()
    }

    fun Input12.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day12").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
