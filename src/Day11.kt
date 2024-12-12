private typealias Input11 = String

fun main() {
    fun Input11.blinkTimes(blinks: Int): Long {
        val regex = """^0*([1-9][0-9]*|0)$""".toRegex()
        val memo = mutableMapOf<Pair<String, Int>, Long>()
        val recur = DeepRecursiveFunction<Pair<String, Int>, Long> { stoneBlinks ->
            memo.getOrPut(stoneBlinks) {
                val (stone, blinks) = stoneBlinks
                if (blinks == 0) {
                    1
                } else if (stone == "0") {
                    callRecursive(Pair("1", blinks - 1))
                } else if (stone.length % 2 == 0) {
                    val (a) = regex.matchEntire(stone.substring(stone.length / 2))!!.destructured
                    callRecursive(Pair(stone.substring(0..<stone.length / 2), blinks - 1)) +
                            callRecursive(Pair(a, blinks - 1))
                } else {
                    callRecursive(Pair((stone.toLong() * 2024).toString(), blinks - 1))
                }
            }
        }
        return split(' ').sumOf { recur(Pair(it, blinks)) }
    }

    fun Input11.part1(): Long = blinkTimes(25)
    fun Input11.part2(): Long = blinkTimes(75)
    val input = "64599 31 674832 2659361 1 0 8867 321"
    check("125 17".part1() == 55312L)
    printlnMeasureTimeMillis { input.part1().println() }
    printlnMeasureTimeMillis { input.part2().println() }
}
