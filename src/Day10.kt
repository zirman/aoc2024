private typealias Input10 = Pair<List<List<Int>>, Size>

fun main() {
    fun List<String>.parse(): Input10 {
        return Pair(map { it.map { it.digitToInt() } }, toSize())
    }

    fun Input10.part1(): Int {
        val (arr, size) = this
        fun Pos.score(): Set<Pos> {
            val height = arr[row][col]
            if (height == 9) {
                return setOf(this)
            }
            val heightPlus = height + 1
            val next = setOf(
                goNorth(),
                goSouth(),
                goWest(),
                goEast(),
            ).filter { it in size && arr[it.row][it.col] == heightPlus }
            return next.flatMap { it.score() }.toSet()
        }

        return arr.indices.flatMap { row ->
            arr[row].indices.mapNotNull { col ->
                if (arr[row][col] == 0) Pos(row, col) else null
            }
        }.sumOf { it.score().size }
    }

    fun Input10.part2(): Int {
        val (arr, size) = this
        fun Pos.score(): Int {
            val height = arr[row][col]
            if (height == 9) {
                return 1
            }
            val heightPlus = height + 1
            val next = setOf(
                goNorth(),
                goSouth(),
                goWest(),
                goEast(),
            ).filter { it in size && arr[it.row][it.col] == heightPlus }
            return next.sumOf { it.score() }
        }

        return arr.indices.flatMap { row ->
            arr[row].indices.mapNotNull { col ->
                if (arr[row][col] == 0) Pos(row, col) else null
            }
        }.sumOf { it.score() }
    }

    val testInput = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 36)
    val input = readInput("Day10").parse()
    printlnMeasureTimeMillis { input.part1().println() }
    check(testInput.part2() == 81)
    input.part2().println()
}
