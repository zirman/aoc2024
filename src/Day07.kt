private typealias Input7 = List<String>

fun main() {
    fun List<String>.parse(): Input7 {
        return this
    }

    fun Input7.part1(): Int {
        TODO()
    }

    fun Input7.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day07").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
