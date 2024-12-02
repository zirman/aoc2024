fun <T> List<T>.dropAt(index: Int): List<T> = filterIndexed { i, item -> index != i }

fun main() {
    fun List<String>.parse(): List<List<Int>> = map { it.split(' ') }.map { it.map { it.toInt() } }

    fun List<Int>.isSafe(): Boolean {
        val diffs = windowed(2).map { (a, b) -> b - a }
        return diffs.all { it in 1..3 } || diffs.all { it >= -3 && it <= -1 }
    }

    fun List<Int>.isSafe2(): Boolean = isSafe() || indices.any { index -> dropAt(index).isSafe() }
    fun List<List<Int>>.part1(): Int = count { it.isSafe() }
    fun List<List<Int>>.part2(): Int = count { it.isSafe2() }

    val testInput = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 2)
    val input = readInput("Day02").parse()
    input.part1().println()
    check(testInput.part2() == 4)
    input.part2().println()
}
