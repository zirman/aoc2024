private typealias Input21 = List<String>

fun main() {
    fun List<String>.parse(): Input21 {
        return this
    }

    fun Input21.part1(): Int {
        TODO()
    }

    fun Input21.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day21").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
