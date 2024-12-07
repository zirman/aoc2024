import kotlin.system.measureTimeMillis

private data class Calibration(val result: Long, val rhs: List<Long>)
private typealias Input7 = List<Calibration>

fun main() {
    fun List<String>.parse(): Input7 = map { line ->
        val (result, values) = line.split(": ")
        Calibration(result = result.toLong(), rhs = values.split(' ').map { it.toLong() })
    }

    fun Calibration.isCalibrated(): Boolean {
        fun recur(top: Long, index: Int): Boolean = when {
            index >= rhs.size -> top == result
            top > result -> false
            else -> recur(top = top + rhs[index], index = index + 1) ||
                    recur(top = top * rhs[index], index = index + 1)
        }
        return recur(top = rhs[0], index = 1)
    }

    fun Input7.part1(): Long = sumOf { pair ->
        if (pair.isCalibrated()) {
            pair.result
        } else {
            0
        }
    }

    fun concatenation(left: Long, right: Long): Long {
        var shift = 1L
        while (right / shift > 0) {
            shift *= 10
        }
        return (left * shift) + right
    }

    fun Calibration.isCalibrated2(): Boolean {
        fun recur(top: Long, index: Int): Boolean = when {
            index >= rhs.size -> top == result
            top > result -> false
            else -> recur(top = top + rhs[index], index = index + 1) ||
                    recur(top = top * rhs[index], index = index + 1) ||
                    recur(top = concatenation(top, rhs[index]), index = index + 1)
        }
        return recur(top = rhs[0], index = 1)
    }

    fun Input7.part2(): Long = sumOf { pair ->
        if (pair.isCalibrated2()) {
            pair.result
        } else {
            0
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
    measureTimeMillis { input.part1().println() }
        .also { println("time: $it") }
    check(testInput.part2() == 11387L)
    measureTimeMillis { input.part2().println() }
        .also { println("time: $it") }
}
