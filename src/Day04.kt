private typealias Input4 = List<String>

typealias RegexGrid = List<List<Regex>>

fun List<String>.toRegexGrid(): RegexGrid = map { it.split('\n').map { it.toRegex() } }

fun RegexGrid.matchesAt(row: Int, index: Int, input: List<String>): Int = count { regexes ->
    regexes
        .mapIndexed { r, regex -> row + r < input.size && regex.matchesAt(input[row + r], index) }
        .all { it }
}

fun main() {
    fun List<String>.parse(): Input4 = this

    fun List<String>.part1(): Int {
        val patterns: RegexGrid = """
            XMAS

            SAMX

            X
            M
            A
            S

            S
            A
            M
            X

            X...
            .M..
            ..A.
            ...S

            S...
            .A..
            ..M.
            ...X

            ...S
            ..A.
            .M..
            X...

            ...X
            ..M.
            .A..
            S...
        """.trimIndent().split("\n\n").toRegexGrid()

        var count = 0
        for (r in 0..<size) {
            for (c in 0..<size) {
                count += patterns.matchesAt(r, c, this)
            }
        }
        return count
    }


    fun Input4.part2(): Int {
        val patterns: RegexGrid = """
            M.S
            .A.
            M.S

            S.M
            .A.
            S.M

            S.S
            .A.
            M.M

            M.M
            .A.
            S.S
        """.trimIndent().split("\n\n").toRegexGrid()

        var count = 0
        for (r in 0..<size) {
            for (c in 0..<size) {
                count += patterns.matchesAt(r, c, this)
            }
        }
        return count
    }

    val testInput = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent().split('\n')
    check(testInput.part1() == 18)
    val input = readInput("Day04")
    input.part1().println()
    check(testInput.parse().part2() == 9)
    input.parse().part2().println()
}
