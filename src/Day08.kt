import kotlin.system.measureTimeMillis

private typealias Input8 = Pair<List<List<Pos>>, Size>

fun main() {
    fun antiNodes(pos1: Pos, pos2: Pos, size: Size): List<Pos> = listOf(
        Pos(
            row = pos2.row + (pos2.row - pos1.row),
            col = pos2.col + (pos2.col - pos1.col),
        ),
        Pos(
            row = pos1.row + (pos1.row - pos2.row),
            col = pos1.col + (pos1.col - pos2.col),
        ),
    ).filter { pos -> size.contains(pos) }

    fun antiNodes2(pos1: Pos, pos2: Pos, size: Size): List<Pos> = buildList {
        var row = pos2.row - pos1.row
        var col = pos2.col - pos1.col
        var pos = Pos(
            row = pos2.row + row,
            col = pos2.col + col,
        )
        add(pos1)
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
        add(pos2)
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

    fun List<String>.parse(): Input8 {
        val antennaGroup = map { line -> line.filter { it != '.' && it != '#' }.toSet() }
            .fold(emptySet<Char>()) { a, b -> a + b }
            .map { frequency ->
                flatMapIndexed { row, line ->
                    line.mapIndexedNotNull { col, f -> if (f == frequency) Pos(row, col) else null }
                }
            }
        return Pair(antennaGroup, toSize())
    }

    fun Input8.part1(): Int {
        val (antennaGroups, size) = this
        return antennaGroups.flatMap { antennaGroup ->
            antennaGroup.combination(2).flatMap { (a, b) ->
                antiNodes(a, b, size)
            }
        }.toSet().size
    }

    fun Input8.part2(): Int {
        val (antennaGroups, size) = this
        return antennaGroups.flatMap { antennaGroup ->
            antennaGroup.combination(2).flatMap { (a, b) ->
                antiNodes2(a, b, size)
            }
        }.toSet().size
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
