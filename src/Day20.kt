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
            println(count)
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

//        route.joinToString("\n") {
//            it.joinToString("") {
//                if (it != null) {
//                    "${it % 10}"
//                } else {
//                    "#"
//                }
//            }
//        }.println()
        return count.also { println(it) }
    }

    fun Input20.part2(): Int {
        TODO()
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
    input.part1(100).println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
