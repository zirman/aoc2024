private typealias Input24 = List<String>

fun main() {
    fun List<String>.parse(): Input24 {
        return this
    }

    fun Input24.part1(): Int {
        TODO()
    }

    fun Input24.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day24").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
