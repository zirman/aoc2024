private typealias Input12 = List<String>

fun Set<Pos>.toGroup(): List<String> {
    val rowMin = minOf { (row, _) -> row }
    val rowMax = maxOf { (row, _) -> row }
    val colMin = minOf { (_, col) -> col }
    val colMax = maxOf { (_, col) -> col }
    val group = (rowMin - 1..rowMax + 1).map { (colMin - 1..colMax + 1).map { 'O' }.toMutableList() }
    forEach { (row, col) ->
        group[row - rowMin + 1][col - colMin + 1] = 'X'
    }
    return group.map { it.joinToString("") }
}

fun List<String>.countCorners(): Int {
    val cornerRegexes: List<List<Regex>> = """
        OO
        OX

        OO
        XO
        
        XO
        OO
        
        OX
        OO
        
        XX
        XO
        
        XX
        OX
        
        OX
        XX
        
        XO
        XX
    """.trimIndent().split("\n\n").map { regexes ->
        regexes.split('\n').map { it.toRegex() }
    }

    val mobius = """
        XO
        OX

        OX
        XO
    """.trimIndent().split("\n\n").map { regexes ->
        regexes.split('\n').map { it.toRegex() }
    }
    var corner = 0
    indices.forEach { row ->
        this[row].indices.forEach { index ->
            if (
                cornerRegexes.any { regex ->
                    regex.indices.all { i ->
                        row + i < size && regex[i].matchesAt(this[row + i], index)
                    }
                }
            ) {
                corner++
            }
            if (
                mobius.any { regex ->
                    regex.indices.all { i ->
                        row + i < size && regex[i].matchesAt(this[row + i], index)
                    }
                }
            ) {
                corner += 2
            }
        }
    }
    return corner
}

fun main() {
    fun List<String>.parse(): Input12 {
        return this
    }

    fun Input12.part1(): Int {
        val size = toSize()

        val foo = DeepRecursiveFunction<Triple<Char, Pos, Set<Pos>>, Pair<Set<Pos>, Int>> { (char, pos, visited) ->
            if (pos !in size || this@part1[pos.row][pos.col] != char) {
                Pair(visited, 1)
            } else if (pos in visited) {
                Pair(visited, 0)
            } else {
                pos
                    .adjacencies()
                    .fold(Pair(visited + pos, 0)) { (a, b), pos ->
                        val (c, d) = callRecursive(Triple(char, pos, a))
                        Pair(c, b + d)
                    }
            }
        }

        var visited = mutableSetOf<Pos>()
        var count = 0
        indices.forEach { row ->
            this[row].indices.forEach { col ->
                val p = Pos(row, col)
                if (p !in visited) {
                    val (v, x) = foo(Triple(this[row][col], p, emptySet()))
                    count += v.size * x
                    visited += v
                }
            }
        }
        return count
    }

    fun Input12.part2(): Int {
        val size = toSize()

        val foo = DeepRecursiveFunction<Triple<Char, Pos, Set<Pos>>, Pair<Set<Pos>, Int>> { (char, pos, visited) ->
            if (pos !in size || this@part2[pos.row][pos.col] != char) {
                Pair(visited, 1)
            } else if (pos in visited) {
                Pair(visited, 0)
            } else {
                pos
                    .adjacencies()
                    .fold(Pair(visited + pos, 0)) { (a, b), pos ->
                        val (c, d) = callRecursive(Triple(char, pos, a))
                        Pair(c, b + d)
                    }
            }
        }

        var visited = mutableSetOf<Pos>()
        var count = 0
        indices.forEach { row ->
            this[row].indices.forEach { col ->
                val p = Pos(row, col)
                if (p !in visited) {
                    val (v, _) = foo(Triple(this[row][col], p, emptySet()))
                    val q = v.toGroup().countCorners()
                    count += q * v.size
                    visited += v
                }
            }
        }
        return count
    }

    val testInput = """
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 1930)
    val input = readInput("Day12").parse()
    printlnMeasureTimeMillis { input.part1().println() }
    check(testInput.part2() == 1206)
    check(
        """
            AAAA
            BBCD
            BBCC
            EEEC
        """.trimIndent().split('\n').parse().part2() == 80
    )
    check(
        """
            OOOOO
            OXOXO
            OOOOO
            OXOXO
            OOOOO
        """.trimIndent().split('\n').parse().part2() == 436
    )
    check(
        """
            EEEEE
            EXXXX
            EEEEE
            EXXXX
            EEEEE
        """.trimIndent().split('\n').parse().part2() == 236
    )
    check(
        """
            AAAAAA
            AAABBA
            AAABBA
            ABBAAA
            ABBAAA
            AAAAAA
        """.trimIndent().split('\n').parse().part2() == 368
    )
    printlnMeasureTimeMillis { input.part2().println() }
}
