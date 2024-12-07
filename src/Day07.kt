private typealias Input7 = List<Pair<Long, List<Long>>>

fun main() {
    fun List<String>.parse(): Input7 = map { line ->
        val (result, values) = line.split(": ")
        Pair(result.toLong(), values.split(' ').map { it.toLong() })
    }

    fun Input7.part1(): Long {
        fun recur(result: Long, values: List<Long>): Boolean {
            if (values.isEmpty()) {
                throw IllegalStateException()
            }
            if (values.size == 1) {
                return values[0] == result
            }
            return recur(
                result,
                buildList {
                    add(values[0] + values[1])
                    addAll(values.subList(2, values.size))
                },
            ) || recur(
                result,
                buildList {
                    add(values[0] * values[1])
                    addAll(values.subList(2, values.size))
                },
            )
        }
        return sumOf { pair ->
            val result = pair.first
            val values = pair.second
            if (recur(result, values)) {
                result
            } else {
                0
            }
        }
    }

    fun Input7.part2(): Long {
        fun recur(result: Long, values: List<Long>): Boolean {
            if (values.isEmpty()) {
                throw IllegalStateException()
            }
            if (values.size == 1) {
                return values[0] == result
            }
            return recur(
                result,
                buildList {
                    add(values[0] + values[1])
                    addAll(values.subList(2, values.size))
                },
            ) || recur(
                result,
                buildList {
                    add(values[0] * values[1])
                    addAll(values.subList(2, values.size))
                },
            ) || recur (
                result,
                buildList {
                    add((values[0].toString() + values[1].toString()).toLong())
                    addAll(values.subList(2, values.size))
                },
            )
        }
        return sumOf { pair ->
            val result = pair.first
            val values = pair.second
            if (recur(result, values)) {
                result
            } else {
                0
            }
        }
    }

    val testInput = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 3749L)
    val input = readInput("Day07").parse()
    input.part1().println()
    check(testInput.part2() == 11387L)
    input.part2().println()
}
