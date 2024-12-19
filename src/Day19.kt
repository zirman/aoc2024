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
        val memo = mutableMapOf<String, Boolean>()
        return patterns.filter { pattern ->
            fun recur(pattern: String): Boolean {
                return memo.getOrPut(pattern) {
                    if (pattern.isEmpty()) {
                        true
                    } else {
                        towels.any { towel ->
                            if (pattern.startsWith(towel)) {
                                recur(pattern.substring(startIndex = towel.length))
                            } else {
                                false
                            }
                        }
                    }
                }
            }
            recur(pattern)
        }.count()
    }

    fun Input19.part2(): Long {
        val (towels, patterns) = this
        val memo = mutableMapOf<String, Long>()
        return patterns.sumOf { pattern ->
            fun recur(pattern: String): Long = memo.getOrPut(pattern) {
                if (pattern.isEmpty()) {
                    1
                } else {
                    towels.sumOf { towel ->
                        if (pattern.startsWith(towel)) {
                            recur(pattern.substring(startIndex = towel.length))
                        } else {
                            0
                        }
                    }
                }
            }
            recur(pattern)
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
