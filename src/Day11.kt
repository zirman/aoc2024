private typealias Input11 = String

fun Long.split(digits: Int, base: Int = 10): Pair<Long, Long> {
    var right = 0L
    var left = this
    var shift = 1
    for (i in 1..digits) {
        right += (left % base) * shift
        left /= base
        shift *= base
    }
    return Pair(left, right)
}

fun main() {
    fun Input11.blinkTimes(blinks: Int): Long {
        val memo = mutableMapOf<Pair<Long, Int>, Long>()
        val recur = DeepRecursiveFunction<Pair<Long, Int>, Long> { stoneBlinks ->
            memo.getOrPut(stoneBlinks) {
                val (stone, blinks) = stoneBlinks
                when {
                    blinks == 0 -> 1
                    stone == 0L -> callRecursive(Pair(1, blinks - 1))
                    else -> {
                        val digits = stone.countDigits()
                        if (digits % 2 == 0) {
                            val (left, right) = stone.split(digits / 2)
                            callRecursive(Pair(left, blinks - 1)) + callRecursive(Pair(right, blinks - 1))
                        } else {
                            callRecursive(Pair(stone * 2024, blinks - 1))
                        }
                    }
                }
            }
        }
        return split(' ').map { it.toLong() }.sumOf { recur(Pair(it, blinks)) }
    }

    fun Input11.part1(): Long = blinkTimes(25)
    fun Input11.part2(): Long = blinkTimes(75)
    val input = "64599 31 674832 2659361 1 0 8867 321"
    check("125 17".part1() == 55312L)
    printlnMeasureTimeMillis { input.part1().println() }
    printlnMeasureTimeMillis { input.part2().println() }
}
