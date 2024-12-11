private typealias Input8 = AntennaData

private data class AntennaData(val antennaGroups: List<List<Pos>>, val size: Size)

fun main() {
    fun Size.antiNodesOf(pos1: Pos, pos2: Pos): List<Pos> = listOf(
        pos2 + (pos2 - pos1),
        pos1 + (pos1 - pos2),
    ).filter { it in this }

    fun Size.antiNodesOf2(pos1: Pos, pos2: Pos): List<Pos> = buildList {
        fun addAntiNodes(pos1: Pos, pos2: Pos) {
            val diff = pos2 - pos1
            var pos = pos2 + diff
            add(pos2)
            while (true) {
                if (pos !in this@antiNodesOf2) {
                    break
                }
                add(pos)
                pos += diff
            }
        }
        addAntiNodes(pos1, pos2)
        addAntiNodes(pos2, pos1)
    }

    fun List<String>.parse(): Input8 {
        val antennaGroup = map { line -> line.filter { it != '.' && it != '#' }.toSet() }
            .fold(emptySet<Char>()) { a, b -> a + b }
            .map { frequency ->
                flatMapIndexed { row, line ->
                    line.mapIndexedNotNull { col, f -> if (f == frequency) Pos(row, col) else null }
                }
            }
        return AntennaData(antennaGroup, toSize())
    }

    fun Input8.part1(): Int = antennaGroups
        .flatMap { antennaGroup ->
            antennaGroup.combinations(2).flatMap { (a, b) ->
                size.antiNodesOf(a, b)
            }
        }
        .distinct().size

    fun Input8.part2(): Int = antennaGroups
        .flatMap { antennaGroup ->
            antennaGroup.combinations(2).flatMap { (a, b) ->
                size.antiNodesOf2(a, b)
            }
        }
        .distinct().size

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
    printlnMeasureTimeMillis { input.part1().println() }
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
    printlnMeasureTimeMillis { input.part2().println() }
}
