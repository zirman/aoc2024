private typealias Input20 = List<String>

fun main() {
    fun List<String>.parse(): Input20 {
        return this
    }

    fun Input20.part1(save: Int): Int {
        var start: Pos? = null
        var end: Pos? = null
        val route = mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                when (c) {
                    '.' -> -1
                    '#' -> null
                    'S' -> {
                        start = Pos(y, x)
                        -1
                    }

                    'E' -> {
                        end = Pos(y, x)
                        0
                    }

                    else -> throw IllegalStateException()
                }
            }.toMutableList()
        }
        val size = toSize()
        fun shortcuts(p: Pos): List<Pos> {
            val pd = route[p.row][p.col]!! - 2
            return listOf(
                p.goNorth(),
                p.goSouth(),
                p.goEast(),
                p.goWest(),
            )
                .flatMap { it ->
                    listOf(
                        it.goNorth(),
                        it.goSouth(),
                        it.goEast(),
                        it.goWest(),
                    )
                }
                .filter {
                    if (it !in size) return@filter false
                    val q = route[it.row][it.col]
                    if (q == null) return@filter false
                    q < pd
                }
                .distinct()
        }

        var steps = 0
        var p = end!!
        while (true) {
            val p1 = listOf(
                p.goNorth(),
                p.goSouth(),
                p.goEast(),
                p.goWest(),
            ).firstOrNull { it in size && route[it.row][it.col] == -1 }
            if (p1 == null) {
                break
            }
            steps++
            route[p1.row][p1.col] = route[p.row][p.col]!! + 1
            p = p1
        }
        var count = 0
        p = start!!
        while (true) {
            count += shortcuts(p).count {
                (route[p.row][p.col]!! - route[it.row][it.col]!!) - 2 >= save
            }
            val p1 = listOf(
                p.goNorth(),
                p.goSouth(),
                p.goEast(),
                p.goWest(),
            ).firstOrNull { it in size && (route[it.row][it.col] ?: Int.MAX_VALUE) < route[p.row][p.col]!! }
            if (p1 == null) {
                break
            }
            p = p1
        }
        return count
    }

    fun Input20.part2(save: Int, picos: Int): Long {
        var start: Pos? = null
        var end: Pos? = null
        val route = mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                when (c) {
                    '.' -> -1
                    '#' -> null
                    'S' -> {
                        start = Pos(y, x)
                        -1
                    }

                    'E' -> {
                        end = Pos(y, x)
                        0
                    }

                    else -> throw IllegalStateException()
                }
            }.toMutableList()
        }
        val size = toSize()
        fun shortcuts(p: Pos, picos: Int): List<Pos> {
            val s = mutableSetOf(p)
            var q = listOf(p)
            repeat(picos) {
                q = q.flatMap {
                    listOf(
                        it.goNorth(),
                        it.goSouth(),
                        it.goEast(),
                        it.goWest(),
                    )
                }.distinct().filter { it in size && it !in s }
                s += q
            }
            s.removeAll { route[it.row][it.col] == null }
            return s.toList()
        }

        var steps = 0
        var p = end!!
        while (true) {
            val p1 = listOf(
                p.goNorth(),
                p.goSouth(),
                p.goEast(),
                p.goWest(),
            ).firstOrNull { it in size && route[it.row][it.col] == -1 }
            if (p1 == null) {
                break
            }
            steps++
            route[p1.row][p1.col] = route[p.row][p.col]!! + 1
            p = p1
        }
        var count = 0L
        p = start!!
        while (true) {
            count += shortcuts(p = p, picos = picos).count {
                (route[p.row][p.col]!! - route[it.row][it.col]!!) - (it.manhattanDistance(p)) >= save
            }
            val p1 = listOf(
                p.goNorth(),
                p.goSouth(),
                p.goEast(),
                p.goWest(),
            ).firstOrNull { it in size && (route[it.row][it.col] ?: Int.MAX_VALUE) < route[p.row][p.col]!! }
            if (p1 == null) {
                break
            }
            p = p1
        }
        return count
    }

    val testInput = """
        ###############
        #...#...#.....#
        #.#.#.#.#.###.#
        #S#...#.#.#...#
        #######.#.#.###
        #######.#.#...#
        #######.#.###.#
        ###..E#...#...#
        ###.#######.###
        #...###...#...#
        #.#####.#.###.#
        #.#...#.#.#...#
        #.#.#.#.#.#.###
        #...#...#...###
        ###############
    """.trimIndent().split('\n').parse()
    check(testInput.part1(12) == 8)
    val input = readInput("Day20").parse()
    printlnMeasureTimeMillis { println("part1: ${input.part1(save = 100)}") }
    check(testInput.part2(save = 72, picos = 20) == 29L)
    printlnMeasureTimeMillis { println("part2: ${input.part2(save = 100, picos = 20)}") }
}
