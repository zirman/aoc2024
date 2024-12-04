private typealias Input8 = List<String>

fun main() {
    fun List<String>.parse(): Input8 {
        return this
    }

    fun Input8.part1(): Int {
        TODO()
    }

    fun Input8.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day08").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
