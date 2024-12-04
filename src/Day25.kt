private typealias Input25 = List<String>

fun main() {
    fun List<String>.parse(): Input25 {
        return this
    }

    fun Input25.part1(): Int {
        TODO()
    }

    fun Input25.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day25").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
