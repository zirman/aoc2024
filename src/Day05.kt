private typealias Input5 = Pair<List<Pair<String, String>>, List<List<String>>>

fun main() {
    fun List<String>.parse(): Input5 {
        val (rules, updates) = joinToString("\n").split("\n\n")
        return Pair(
            rules.split('\n').map {
                val (a, b) = it.split('|')
                Pair(a, b)
            },
            updates.split('\n').map { it.split(',') },
        )
    }

    fun Input5.separateSorted(): Pair<List<List<String>>, List<List<String>>> {
        val (rules, updates) = this
        return updates.partition { update ->
            rules.all { (u1, u2) ->
                val i = update.indexOf(u1)
                val k = update.indexOf(u2)
                i == -1 || k == -1 || i < k
            }
        }
    }

    fun List<List<String>>.sumMiddle(): Int = sumOf { strings -> strings[strings.size / 2].toInt() }

    fun Input5.part1(): Int {
        val (sorted, _) = separateSorted()
        return sorted.sumMiddle()
    }

    fun Input5.part2(): Int {
        val (_, unsorted) = separateSorted()
        val (rules, _) = this
        return unsorted
            .map { strings ->
                strings.sortedWith { o1, o2 ->
                    rules.forEach { (a, b) ->
                        if (a == o1 && b == o2) {
                            return@sortedWith 1
                        } else if (a == o2 && b == o1) {
                            return@sortedWith -1
                        }
                    }
                    0
                }
            }
            .sumMiddle()
    }

    val testInput = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 143)
    val input = readInput("Day05").parse()
    input.part1().println()
    check(testInput.part2() == 123)
    input.part2().println()
}
