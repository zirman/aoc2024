private typealias Input23 = List<String>

fun main() {
    fun List<String>.parse(): Input23 {
        return this
    }

    fun Input23.part1(): Int {
        TODO()
    }

    fun Input23.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day23").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
