private typealias Input22 = List<String>

fun main() {
    fun List<String>.parse(): Input22 {
        return this
    }

    fun Input22.part1(): Int {
        TODO()
    }

    fun Input22.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day22").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
