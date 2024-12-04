private typealias Input16 = List<String>

fun main() {
    fun List<String>.parse(): Input16 {
        return this
    }

    fun Input16.part1(): Int {
        TODO()
    }

    fun Input16.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day16").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
