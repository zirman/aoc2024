private typealias Input13 = List<String>

fun main() {
    fun List<String>.parse(): Input13 {
        return this
    }

    fun Input13.part1(): Int {
        TODO()
    }

    fun Input13.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day13").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
