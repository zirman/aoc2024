private typealias Input17 = List<String>

fun main() {
    fun List<String>.parse(): Input17 {
        return this
    }

    fun Input17.part1(): Int {
        TODO()
    }

    fun Input17.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day17").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
