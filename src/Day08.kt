import kotlin.system.measureTimeMillis

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

private fun antinodes2(pos1: Pos, pos2: Pos, size: Size): List<Pos> = buildList {
    var row = pos2.row - pos1.row
    var col = pos2.col - pos1.col
    var pos = Pos(
        row = pos2.row + row,
        col = pos2.col + col,
    )
    while (true) {
        if (size.contains(pos).not()) {
            break
        }
        add(pos)
        pos = pos.copy(
            row = pos.row + row,
            col = pos.col + col,
        )
    }
    row = pos1.row - pos2.row
    col = pos1.col - pos2.col
    pos = Pos(
        row = pos1.row + row,
        col = pos1.col + col,
    )
    while (true) {
        if (size.contains(pos).not()) {
            break
        }
        add(pos)
        pos = pos.copy(
            row = pos.row + row,
            col = pos.col + col,
        )
    }
}

fun Size.contains(pos: Pos): Boolean {
    return pos.row >= 0 && pos.row < height && pos.col >= 0 && pos.col < width
}

fun main() {
    fun List<String>.parse(): Input8 {
        return this
    }

    fun Input8.part1(): Int {
        val size = Size(width = this[0].length, height = this.size)
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
        return nodes
    }

    fun Input8.part2(): Int {
        val size = Size(width = this[0].length, height = this.size)
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
                antinodes2(a, b, size) + a + b
            }
        }.toSet().count()
        return nodes
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
    measureTimeMillis { input.part1().println() }
        .also { println("time: $it") }
    val testInput2 = """
        T....#....
        ...T......
        .T....#...
        .........#
        ..#.......
        ..........
        ...#......
        ..........
        ....#.....
        ..........
    """.trimIndent().split('\n').parse()
    check(testInput2.part2() == 9)
    measureTimeMillis { input.part2().println() }
        .also { println("time: $it") }
}
