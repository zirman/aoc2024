private typealias Input6 = List<CharArray>

fun main() {
    fun List<String>.parse(): Input6 = map { it.toCharArray() }

    fun Input6.part1(): Int {
        var row = indexOfFirst { it.contains('^') }
        var col = this[row].indexOf('^')

        outer@ while (true) {
            while (true) {
                this[row][col] = 'X'
                if (row - 1 < 0) {
                    break@outer
                }
                if (this[row - 1][col] == '#') {
                    break
                }
                row--
            }
            while (true) {
                this[row][col] = 'X'
                if (col + 1 >= this[0].size) {
                    break@outer
                }
                if (this[row][col + 1] == '#') {
                    break
                }

                col++
            }
            while (true) {
                this[row][col] = 'X'
                if (row + 1 >= this.size) {
                    break@outer
                }
                if (this[row + 1][col] == '#') {
                    break
                }
                row++
            }
            while (true) {
                this[row][col] = 'X'
                if (col - 1 < 0) {
                    break@outer
                }
                if (this[row][col - 1] == '#') {
                    break
                }
                col--
            }
        }
        return this.sumOf { it.count { ch -> ch == 'X' } }
    }

    fun Input6.part2(): Int {
        var row = indexOfFirst { it.contains('^') }
        var col = this[row].indexOf('^')
        val visited = mutableSetOf<Triple<Int, Int, Char>>()

        fun Input6.findLoop(row: Int, col: Int, dir: Char): Boolean {
            var row = row
            var col = col
            var dir = dir
            val visited = visited.toMutableSet()
            while (true) {
                if (dir == '^') {
                    while (true) {
                        if (visited.contains(Triple(row, col, dir))) {
                            return true
                        }
                        visited.add(Triple(row, col, dir))
                        if (row - 1 < 0) {
                            return false
                        }
                        if (this[row - 1][col] == '#') {
                            break
                        }
                        row--
                    }
                    dir = '>'
                }
                if (dir == '>') {
                    while (true) {
                        if (visited.contains(Triple(row, col, dir))) {
                            return true
                        }
                        visited.add(Triple(row, col, dir))
                        if (col + 1 >= this[0].size) {
                            return false
                        }
                        if (this[row][col + 1] == '#') {
                            break
                        }
                        col++
                    }
                    dir = 'v'
                }
                if (dir == 'v') {
                    while (true) {
                        if (visited.contains(Triple(row, col, dir))) {
                            return true
                        }
                        visited.add(Triple(row, col, dir))
                        if (row + 1 >= this.size) {
                            return false
                        }
                        if (this[row + 1][col] == '#') {
                            break
                        }
                        row++
                    }
                    dir = '<'
                }
                if (dir == '<') {
                    while (true) {
                        if (visited.contains(Triple(row, col, dir))) {
                            return true
                        }
                        visited.add(Triple(row, col, dir))
                        if (col - 1 < 0) {
                            return false
                        }
                        if (this[row][col - 1] == '#') {
                            break
                        }
                        col--
                    }
                    dir = '^'
                }
            }
            return false
        }

        var blocks = mutableSetOf<Pair<Int, Int>>()
        outer@ while (true) {
            while (true) {
                this[row][col] = 'X'
                visited.add(Triple(row, col, '^'))
                if (row - 1 < 0) {
                    break@outer
                }
                if (this[row - 1][col] == '#') {
                    break
                }
                if (this[row - 1][col] != 'X' && blocks.contains(Pair(row - 1, col))
                        .not() && this.map { chars -> chars.clone() }.apply { this[row - 1][col] = '#' }
                        .findLoop(row, col, '>')
                ) {

                    blocks.add(Pair(row - 1, col))
                }
                row--
            }
            while (true) {
                this[row][col] = 'X'
                visited.add(Triple(row, col, '>'))
                if (col + 1 >= this[0].size) {
                    break@outer
                }
                if (this[row][col + 1] == '#') {
                    break
                }
                if (this[row][col + 1] != 'X' && blocks.contains(Pair(row, col + 1))
                        .not() && this.map { chars -> chars.clone() }.apply { this[row][col + 1] = '#' }
                        .findLoop(row, col, 'v')
                ) {
                    blocks.add(Pair(row, col + 1))
                }
                col++
            }
            while (true) {
                this[row][col] = 'X'
                visited.add(Triple(row, col, 'v'))
                if (row + 1 >= this.size) {
                    break@outer
                }
                if (this[row + 1][col] == '#') {
                    break
                }
                if (this[row + 1][col] != 'X' && blocks.contains(Pair(row + 1, col))
                        .not() && this.map { chars -> chars.clone() }.apply { this[row + 1][col] = '#' }
                        .findLoop(row, col, '<')
                ) {
                    blocks.add(Pair(row + 1, col))
                }
                row++
            }
            while (true) {
                this[row][col] = 'X'
                visited.add(Triple(row, col, '<'))
                if (col - 1 < 0) {
                    break@outer
                }
                if (this[row][col - 1] == '#') {
                    break
                }
                if (this[row][col - 1] != 'X' && blocks.contains(Pair(row, col - 1))
                        .not() && this.map { chars -> chars.clone() }.apply { this[row][col - 1] = '#' }
                        .findLoop(row, col, '^')
                ) {
                    blocks.add(Pair(row, col - 1))
                }
                col--
            }
        }
        return blocks.size
    }

    val testInput = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent().split('\n')
    check(testInput.parse().part1() == 41)
    val input = readInput("Day06")
    input.parse().part1().println()
    check(testInput.parse().part2() == 6)
    input.parse().part2().println()
}
