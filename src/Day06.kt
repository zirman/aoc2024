private typealias Input6 = List<String>

fun main() {
    fun List<String>.parse(): Input6 {
        return this
    }

    fun Input6.part1(): Int {
        TODO()
    }

    fun Input6.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day06").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
