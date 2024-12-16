import java.util.PriorityQueue

private typealias Input16 = Triple<Pos, Pos, List<List<Char>>>

data class State(val pos: Pos, val dir: Char, val score: Int, val prev: State?)

fun State?.toPos(): Set<Pos> {
    if (this == null) return emptySet()
    return setOf(pos) + prev.toPos()
}

fun main() {
    fun List<String>.parse(): Input16 {
        var end = Pos(0, 0)
        var start = Pos(0, 0)
        for (row in 0..<size) {
            for (col in 0..<this[row].length) {
                when (this[row][col]) {
                    'E' -> {
                        end = Pos(row, col)
                    }

                    'S' -> {
                        start = Pos(row, col)
                    }
                }
            }
        }
        return Triple(start, end, map { it.map { if (it == 'E' || it == 'S') '.' else it } })
    }

    fun Input16.part1(): Int {
        val (start, end, maze) = this
        fun State.next(): List<State> = buildList {
            when (dir) {
                '>' -> {
                    val p = pos.goEast()
                    if (maze[p.row][p.col] == '.') {
                        add(this@next.copy(pos = p, score = this@next.score + 1))
                    }
                    add(this@next.copy(dir = '^', score = this@next.score + 1000))
                    add(this@next.copy(dir = 'v', score = this@next.score + 1000))
                }

                '<' -> {
                    val p = pos.goWest()
                    if (maze[p.row][p.col] == '.') {
                        add(this@next.copy(pos = p, score = this@next.score + 1))
                    }
                    add(this@next.copy(dir = '^', score = this@next.score + 1000))
                    add(this@next.copy(dir = 'v', score = this@next.score + 1000))
                }

                '^' -> {
                    val p = pos.goNorth()
                    if (maze[p.row][p.col] == '.') {
                        add(this@next.copy(pos = p, score = this@next.score + 1))
                    }
                    add(this@next.copy(dir = '<', score = this@next.score + 1000))
                    add(this@next.copy(dir = '>', score = this@next.score + 1000))
                }

                'v' -> {
                    val p = pos.goSouth()
                    if (maze[p.row][p.col] == '.') {
                        add(this@next.copy(pos = p, score = this@next.score + 1))
                    }
                    add(this@next.copy(dir = '<', score = this@next.score + 1000))
                    add(this@next.copy(dir = '>', score = this@next.score + 1000))
                }
            }
        }


        val q = PriorityQueue(compareBy<State> { it.score })
        q.add(State(start, '>', 0, null))
        val visited = mutableMapOf(Pair(start, '>') to 0)
        while (true) {
            val c = q.remove()
            if (c.pos == end) {
                return c.score
            }
            c.next().forEach {
                val x = Pair(it.pos, it.dir)
                if (x !in visited) {
                    q.add(it)
                    visited[x] = it.score
                }
                val y = visited[x]
                if (y != null && y > it.score) {
                    q.remove(State(it.pos, it.dir, y, null))
                    q.add(it)
                    visited[x] = it.score
                }
            }
        }
    }

    fun Input16.part2(): Int {
        val (start, end, maze) = this
        fun State.next(): List<State> = buildList {
            when (dir) {
                '>' -> {
                    val p = pos.goEast()
                    if (maze[p.row][p.col] == '.') {
                        add(this@next.copy(pos = p, score = this@next.score + 1))
                    }
                    add(this@next.copy(dir = '^', score = this@next.score + 1000))
                    add(this@next.copy(dir = 'v', score = this@next.score + 1000))
                }

                '<' -> {
                    val p = pos.goWest()
                    if (maze[p.row][p.col] == '.') {
                        add(this@next.copy(pos = p, score = this@next.score + 1))
                    }
                    add(this@next.copy(dir = '^', score = this@next.score + 1000))
                    add(this@next.copy(dir = 'v', score = this@next.score + 1000))
                }

                '^' -> {
                    val p = pos.goNorth()
                    if (maze[p.row][p.col] == '.') {
                        add(this@next.copy(pos = p, score = this@next.score + 1))
                    }
                    add(this@next.copy(dir = '<', score = this@next.score + 1000))
                    add(this@next.copy(dir = '>', score = this@next.score + 1000))
                }

                'v' -> {
                    val p = pos.goSouth()
                    if (maze[p.row][p.col] == '.') {
                        add(this@next.copy(pos = p, score = this@next.score + 1))
                    }
                    add(this@next.copy(dir = '<', score = this@next.score + 1000))
                    add(this@next.copy(dir = '>', score = this@next.score + 1000))
                }
            }
        }


        val q = PriorityQueue(compareBy<State> { it.score })
        q.add(State(start, '>', 0, null))
        val visited = mutableMapOf(Pair(start, '>') to 0)
        while (true) {
            val c = q.remove()
            c.next().forEach {
                val x = Pair(it.pos, it.dir)
                if (x !in visited) {
                    q.add(it.copy(prev = c))
                    visited[x] = it.score
                }
                val y = visited[x]
                if (y != null && y >= it.score) {
//                    q.remove(State(it.pos, it.dir, y, c))
                    q.add(it.copy(prev = c))
                    visited[x] = it.score
                }
            }
            if (c.pos == end) {
                val ends = mutableSetOf(c)
                val score = c.score
                while (true) {
                    val c = q.remove()
                    if (c.score == score && c.pos == end) {
                        ends.add(c)
                    }
                    if (c.score > score) {
                        return ends.flatMap { it.toPos().toList() }.toSet().size
                    }
                    c.next().forEach {
                        val x = Pair(it.pos, it.dir)
                        if (x !in visited) {
                            q.add(it)
                            visited[x] = it.score
                        }
                        val y = visited[x]
                        if (y != null && y > it.score) {
//                            q.remove(State(it.pos, it.dir, y, c))
                            q.add(it)
                            visited[x] = it.score
                        }
                    }
                }
//                return c.score
            }
        }
    }

    val testInput1 = """
        ###############
        #.......#....E#
        #.#.###.#.###.#
        #.....#.#...#.#
        #.###.#####.#.#
        #.#.#.......#.#
        #.#.#####.###.#
        #...........#.#
        ###.#.#####.#.#
        #...#.....#.#.#
        #.#.#.###.#.#.#
        #.....#...#.#.#
        #.###.#.#.#.#.#
        #S..#.....#...#
        ###############
    """.trimIndent().split('\n').parse()
    check(testInput1.part1() == 7036)
    val testInput2 = """
        #################
        #...#...#...#..E#
        #.#.#.#.#.#.#.#.#
        #.#.#.#...#...#.#
        #.#.#.#.###.#.#.#
        #...#.#.#.....#.#
        #.#.#.#.#.#####.#
        #.#...#.#.#.....#
        #.#.#####.#.###.#
        #.#.#.......#...#
        #.#.###.#####.###
        #.#.#...#.....#.#
        #.#.#.#####.###.#
        #.#.#.........#.#
        #.#.#.#########.#
        #S#.............#
        #################
    """.trimIndent().split('\n').parse()
    check(testInput2.part1() == 11048)
    val input = readInput("Day16").parse()
    printlnMeasureTimeMillis { input.part1().println() }
    check(testInput1.part2() == 45)
    check(testInput2.part2() == 64)
    printlnMeasureTimeMillis { input.part2().println() }
}
