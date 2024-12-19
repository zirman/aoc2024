private typealias Input19 = Pair<List<String>, List<String>>

fun main() {
    fun List<String>.parse(): Input19 {
        val (towels, patterns) = joinToString("\n").split("\n\n")
        return Pair(
            towels.split(", ").sortedDescending(),
            patterns.split('\n'),
        )
    }

    fun Input19.part1(): Int {
        val (towels, patterns) = this
        return patterns.count { pattern ->
            val memo = mutableMapOf<Int, Boolean>()
            fun recur(index: Int): Boolean = memo.getOrPut(index) {
                if (index >= pattern.length) {
                    true
                } else {
                    towels.any { towel ->
                        if (pattern.startsWith(towel, index)) {
                            recur(index + towel.length)
                        } else {
                            false
                        }
                    }
                }
            }
            recur(0)
        }
    }

    fun Input19.part2(): Long {
        val (towels, patterns) = this
        return patterns.sumOf { pattern ->
            val memo = mutableMapOf<Int, Long>()
            fun recur(index: Int): Long = memo.getOrPut(index) {
                if (index >= pattern.length) {
                    1
                } else {
                    towels.sumOf { towel ->
                        if (pattern.startsWith(towel, index)) {
                            recur(index + towel.length)
                        } else {
                            0
                        }
                    }
                }
            }
            recur(0)
        }
    }

    val testInput = """
        r, wr, b, g, bwu, rb, gb, br

        brwrr
        bggr
        gbbr
        rrbgbr
        ubwu
        bwurrg
        brgr
        bbrgwb
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 6)
    val input = readInput("Day19").parse()
    printlnMeasureTimeMillis { println("part1: ${input.part1()}") }
    check(testInput.part2() == 16L)
    printlnMeasureTimeMillis { println("part2: ${input.part2()}") }
}
