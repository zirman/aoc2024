private typealias Input18 = List<String>

fun main() {
    fun List<String>.parse(): Input18 {
        return this
    }

    fun Input18.part1(): Int {
        TODO()
    }

    fun Input18.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day18").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
