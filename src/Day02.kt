fun <T> List<T>.dropAt(index: Int): List<T> = mapIndexedNotNull { i, item -> if (index != i) item else null }

fun main() {
    fun parse(input: List<String>): List<List<Int>> {
        return input.map { it.split(' ') }.map { it.map { it.toInt() } }
    }

    fun List<Int>.isSafe(): Boolean {
        val diffs = windowed(2).map { (a, b) -> b - a }
        return diffs.all { it in 1..3 } || diffs.all { it >= -3 && it <= -1 }
    }

    fun List<Int>.isSafe2(): Boolean = isSafe() || indices.any { index -> dropAt(index).isSafe() }

    fun part1(input: List<String>): Int {
        return parse(input).count { it.isSafe() }
    }

    fun part2(input: List<String>): Int {
        return parse(input).count { it.isSafe2() }
    }

    val testInput = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent().split('\n')
    check(part1(testInput) == 2)
    val input = readInput("Day02")
    part1(input).println()
    check(part2(testInput) == 4)
    part2(input).println()
}
