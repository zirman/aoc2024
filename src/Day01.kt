import kotlin.math.absoluteValue

fun main() {
    fun parse(input: List<String>): List<Pair<Int, Int>> = input.map { line ->
        val (a, b) = line.split("""\s+""".toRegex())
        Pair(
            a.toInt(),
            b.toInt(),
        )
    }

    fun part1(input: List<String>): Int {
        val list = parse(input)
        val listA = list.map { it.first }.sorted()
        val listB = list.map { it.second }.sorted()
        return listA.zip(listB).sumOf { (a, b) -> (b - a).absoluteValue }
    }

    fun part2(input: List<String>): Int {
        val list = parse(input)
        val listA = list.map { it.first }
        val listB = list.map { it.second }
        return listA.sumOf { i -> listB.count { k -> k == i } * i }
    }

    val testInput =
        """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent().split('\n')
    check(part1(testInput) == 11)
    val input = readInput("Day01")
    part1(input).println()
    check(part2(testInput) == 31)
    part2(input).println()
}
