private typealias Input14 = List<Robot>

private data class Robot(val pos: Pos, val vel: Pos)

fun main() {
    val regex = """^p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)$""".toRegex()
    fun List<String>.parse(): Input14 = map { line ->
        val (px, py, vx, vy) = regex.matchEntire(line)!!.destructured
        Robot(Pos(py.toInt(), px.toInt()), Pos(vy.toInt(), vx.toInt()))
    }

    fun Input14.atTime(size: Size, time: Int): List<Pos> = map { robot ->
        Pos(
            (robot.pos.row + robot.vel.row * time).mod(size.height),
            (robot.pos.col + robot.vel.col * time).mod(size.width),
        )
    }

    fun Input14.part1(size: Size, time: Int): Int {
        val robots = atTime(size, time)
        val filterCol = (size.width - 1) / 2
        val filterRow = (size.height - 1) / 2
        return robots.count { (row, col) -> row < filterRow && col < filterCol } *
                robots.count { (row, col) -> row > filterRow && col < filterCol } *
                robots.count { (row, col) -> row > filterRow && col > filterCol } *
                robots.count { (row, col) -> row < filterRow && col > filterCol }
    }

    fun List<Pos>.reachRobots(size: Size): Int {
        val groupedCount: Map<Pos, Int> = groupingBy { it }.eachCount()
        val visited = mutableSetOf<Pos>()
        var touched = 0
        val recur = DeepRecursiveFunction { pos: Pos ->
            if (pos !in size || pos in visited) return@DeepRecursiveFunction
            visited += pos
            if (pos in groupedCount) {
                touched += groupedCount[pos]!!
            } else {
                callRecursive(pos.goNorth())
                callRecursive(pos.goSouth())
                callRecursive(pos.goWest())
                callRecursive(pos.goEast())
            }
        }
        for (i in 0..<size.height) {
            recur(Pos(row = 0, col = i))
            recur(Pos(row = size.width - 1, col = i))
        }
        for (i in 0..<size.width) {
            recur(Pos(row = i, col = 0))
            recur(Pos(row = i, col = size.height - 1))
        }
        return touched
    }

    fun List<Pos>.toString(size: Size): String = buildString {
        (0..<size.height).forEach { row ->
            (0..<size.width).forEach { col ->
                append(if (Pos(row, col) in this@toString) 'X' else '.')
            }
            append('\n')
        }
    }

    fun Input14.part2(size: Size): Int {
        val robotsVisited = mutableSetOf<List<Pos>>()
        var bestRobots: List<Pos> = emptyList()
        var bestRobotsI = -1
        var bestRobotsReached = Int.MAX_VALUE
        for (i in 1..Int.MAX_VALUE) {
            val robots = atTime(size, i)
            val robotsReached = robots.reachRobots(size)
            if (robotsReached < bestRobotsReached) {
                bestRobotsReached = robotsReached
                bestRobots = robots
                bestRobotsI = i
            }
            if (robots in robotsVisited) {
                bestRobots.toString(size).println()
                return bestRobotsI
            }
            robotsVisited += robots
        }
        throw IllegalStateException()
    }

    val testInput = """
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3
    """.trimIndent().split('\n').parse()
    check(testInput.part1(Size(width = 11, height = 7), 100) == 12)
    val input = readInput("Day14").parse()
    input.part1(Size(width = 101, height = 103), 100).println()
    printlnMeasureTimeMillis { input.part2(Size(width = 101, height = 103)).println() }
}
