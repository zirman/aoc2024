private typealias Input8 = List<String>

private fun antinodes(pos1: Pos, pos2: Pos): List<Pos> = listOf(
    Pos(
        row = pos2.row + (pos2.row - pos1.row),
        col = pos2.col + (pos2.col - pos1.col),
    ),
    Pos(
        row = pos1.row + (pos1.row - pos2.row),
        col = pos1.col + (pos1.col - pos2.col),
    ),
)

fun Size.contains(pos: Pos): Boolean {
    return pos.row >= 0 && pos.row < height && pos.col >= 0 && pos.col < width
}

fun main() {
    fun List<String>.parse(): Input8 {
        return this
    }

    fun Input8.part1(): Int {
        val size = Size(width = this[0].length, height = this.size)
        println(size)
        val freqs =
            map { line -> line.filter { it != '.' && it != '#' }.toSet() }.fold(emptySet<Char>()) { a, b -> a + b }
        var nodes = 0
        val locations = freqs.associate { freq ->
            Pair(
                freq,
                mapIndexed { row, string ->
                    string.mapIndexedNotNull { col, ch -> if (ch == freq) Pos(row, col) else null }
                }.fold(emptySet<Pos>()) { acc, pos -> acc + pos },
            )
        }
        nodes = locations.flatMap { (freq, locations) ->
            locations.toList().combination(2).flatMap { (a, b) ->
                antinodes(a, b).filter { size.contains(it) }
            }
        }.toSet().count()
        println(nodes)
        return nodes
    }

    fun Input8.part2(): Int {
        TODO()
    }

    val testInput = """
        ......#....#
        ...#....0...
        ....#0....#.
        ..#....0....
        ....0....#..
        .#....A.....
        ...#........
        #......#....
        ........A...
        .........A..
        ..........#.
        ..........#.
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 14)
    val input = readInput("Day08").parse()
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
