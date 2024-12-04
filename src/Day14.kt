private typealias Input14 = List<String>

fun main() {
    fun List<String>.parse(): Input14 {
        return this
    }

    fun Input14.part1(): Int {
        TODO()
    }

    fun Input14.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day14").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
