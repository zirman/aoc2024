private typealias Input4 = List<String>

fun main() {
    val xmasRegex = """XMAS""".toRegex()
    fun List<String>.parse(): Input4 {
        return this
    }

    fun Input4.transposed(): Input4 {
        return (0..<first().length)
            .map { i -> joinToString("") { it -> "${it[i]}" } }
    }

    //    fun Input4.diagonals(): Input4 {
//        return indices
//            .map { i -> (0..i).joinToString("") { k -> "${this[i - k][k]}" } }
//    }
    fun Input4.countDiagonals(): Int {
        var count = 0
        indices
            .map { i -> (0..i).joinToString("") { k -> "${this[i - k][k]}" } }
            .also { println(it) }.forEach { line ->
                var match = xmasRegex.find(line)
                while (match != null) {
                    count++
                    match = match.next()
                }
                match = xmasRegex.find(line.reversed())
                while (match != null) {
                    count++
                    match = match.next()
                }
            }
        (size - 1 downTo 1)
            .map { i ->
                val q = size - i
                (0..<i).joinToString("") { k ->
                    "${this[size - k - 1][q + k]}"
                }
            }.also { println(it) }.forEach { line ->
                var match = xmasRegex.find(line)
                while (match != null) {
                    count++
                    match = match.next()
                }
                match = xmasRegex.find(line.reversed())
                while (match != null) {
                    count++
                    match = match.next()
                }
            }
        return count
    }

    fun Input4.part1(): Int {
        var count = 0
        forEach { line ->
            var match = xmasRegex.find(line)
            while (match != null) {
                count++
                match = match.next()
            }
            match = xmasRegex.find(line.reversed())
            while (match != null) {
                count++
                match = match.next()
            }
        }
        transposed().forEach { line ->
            var match = xmasRegex.find(line)
            while (match != null) {
                count++
                match = match.next()
            }
            match = xmasRegex.find(line.reversed())
            while (match != null) {
                count++
                match = match.next()
            }
        }
        count += countDiagonals()
        count += transposed().map { it.toList().reversed().joinToString("")}.countDiagonals()
        return count
    }

    fun Input4.part2(): Int {
        TODO()
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
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 18)
    val input = readInput("Day04").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
