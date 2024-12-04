private typealias Input5 = List<String>

fun main() {
    fun List<String>.parse(): Input5 {
        return this
    }

    fun Input5.part1(): Int {
        TODO()
    }

    fun Input5.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day05").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
