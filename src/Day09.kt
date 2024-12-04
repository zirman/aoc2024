private typealias Input9 = List<String>

fun main() {
    fun List<String>.parse(): Input9 {
        return this
    }

    fun Input9.part1(): Int {
        TODO()
    }

    fun Input9.part2(): Int {
        TODO()
    }

    val testInput = """
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == TODO())
    val input = readInput("Day09").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
