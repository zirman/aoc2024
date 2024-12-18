import java.util.PriorityQueue

private typealias Input18 = List<Pos>

fun main() {
    fun List<String>.parse(): Input18 = map {
        val (col, row) = it.split(',')
        Pos(row.toInt(), col.toInt())
    }

    fun Pos.next(): List<Pos> = listOf(
        goNorth(),
        goSouth(),
        goEast(),
        goWest(),
    )

    fun Input18.part1(n: Int, size: Size): Int? {
        val v = mutableMapOf<Pos, Int>()
        val e = Pos(size.height - 1, size.width - 1)
        val q = PriorityQueue<Pair<Pos, Int>>(
            compareBy<Pair<Pos, Int>> { it.second }
                .thenBy { e.manhattanDistance(it.first) },
        )
        val corrupted = take(n).toSet()
        q.add(Pair(Pos(0, 0), 0))
        v[Pos(0, 0)] = 0
        while (q.isNotEmpty()) {
            val (pos, steps) = q.remove()
            if (pos == e) {
                return steps
            }
            pos
                .next()
                .filter { it in size && it !in corrupted }
                .forEach { pos ->
                    val s = v[pos]
                    if (s != null) {
                        if (steps + 1 < s) {
                            q.remove(Pair(pos, s))
                            q.add(Pair(pos, steps + 1))
                            v[pos] = steps + 1
                        }
                    } else {
                        q.add(Pair(pos, steps + 1))
                        v[pos] = steps + 1
                    }
                }
        }
        return null
    }

    fun Input18.part2(n: Int, size: Size): String {
        for (i in n + 1..this.size) {
            if (part1(i, size) == null) {
                return "${this[i - 1].col},${this[i - 1].row}"
            }
        }
        throw IllegalStateException()
    }

    val testInput = """
        5,4
        4,2
        4,5
        3,0
        2,1
        6,3
        2,4
        1,5
        0,6
        3,3
        2,6
        5,1
        1,2
        5,5
        2,5
        6,5
        1,4
        0,4
        6,4
        1,1
        6,1
        1,0
        0,5
        1,6
        2,0
    """.trimIndent().split('\n').parse()
    check(testInput.part1(12, Size(7, 7)) == 22)
    val input = readInput("Day18").parse()
    printlnMeasureTimeMillis { println("part1: ${input.part1(1024, Size(71, 71))}") }
    check(testInput.part2(12, Size(7, 7)) == "6,1")
    printlnMeasureTimeMillis { println("part2: ${input.part2(1024, Size(71, 71))}") }
}
