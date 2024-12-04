private typealias Input19 = List<String>

fun main() {
    fun List<String>.parse(): Input19 {
        return this
    }

    fun Input19.part1(): Int {
        TODO()
    }

    fun Input19.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day19").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
