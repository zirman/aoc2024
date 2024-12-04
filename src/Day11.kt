private typealias Input11 = List<String>

fun main() {
    fun List<String>.parse(): Input11 {
        return this
    }

    fun Input11.part1(): Int {
        TODO()
    }

    fun Input11.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day11").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
