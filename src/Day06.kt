import kotlin.system.measureTimeMillis

private typealias Input6 = Triple<Pos, Set<Pos>, Size>

fun Pos.goWest(): Pos = copy(col = col - 1)
fun Pos.goEast(): Pos = copy(col = col + 1)
fun Pos.goNorth(): Pos = copy(row = row - 1)
fun Pos.goSouth(): Pos = copy(row = row + 1)

fun Pos.goWestIn(rect: Size): Pos? = if (col > 0) copy(col = col - 1) else null
fun Pos.goEastIn(rect: Size): Pos? = if (col < rect.width - 1) copy(col = col + 1) else null
fun Pos.goNorthIn(rect: Size): Pos? = if (row > 0) copy(row = row - 1) else null
fun Pos.goSouthIn(rect: Size): Pos? = if (row < rect.height - 1) copy(row = row + 1) else null

fun main() {
    fun List<String>.parse(): Input6 {
        var row = 0
        var col = 0
        var blockers = buildSet {
            outer@ for (r in this@parse.indices) {
                for (c in this@parse[r].indices) {
                    when (this@parse[r][c]) {
                        '^' -> {
                            row = r
                            col = c
                        }

                        '#' -> {
                            add(Pos(r, c))
                        }
                    }
                }
            }
        }
        return Triple(Pos(row, col), blockers, third = Size(width = this[0].length, height = this.size))
    }

    fun Input6.part1(): Int {
        var pos = first
        val blockerPos = second
        val size = third
        val visitedPos = mutableSetOf<Pos>()
        outer@ while (true) {
            while (true) {
                visitedPos.add(pos)
                if (blockerPos.contains(pos.goNorth())) break
                pos = pos.goNorthIn(size) ?: break@outer
            }
            while (true) {
                visitedPos.add(pos)
                if (blockerPos.contains(pos.goEast())) break
                pos = pos.goEastIn(size) ?: break@outer
            }
            while (true) {
                visitedPos.add(pos)
                if (blockerPos.contains(pos.goSouth())) break
                pos = pos.goSouthIn(size) ?: break@outer
            }
            while (true) {
                visitedPos.add(pos)
                if (blockerPos.contains(pos.goWest())) break
                pos = pos.goWestIn(size) ?: break@outer
            }
        }
        return visitedPos.size
    }

    fun findLoop(
        startPos: Pos,
        startDir: Char,
        blockPos: Set<Pos>,
        newBlockPos: Pos,
        visitedPosNorth: Set<Pos>,
        visitedPosSouth: Set<Pos>,
        visitedPosEast: Set<Pos>,
        visitedPosWest: Set<Pos>,
        size: Size,
    ): Boolean {
        var pos = startPos
        var dir = startDir
        val visitedPosNorth2 = mutableSetOf<Pos>()
        val visitedPosSouth2 = mutableSetOf<Pos>()
        val visitedPosEast2 = mutableSetOf<Pos>()
        val visitedPosWest2 = mutableSetOf<Pos>()
        while (true) {
            if (dir == '^') {
                while (true) {
                    if (visitedPosNorth.contains(pos) || visitedPosNorth2.contains(pos)) {
                        return true
                    }
                    visitedPosNorth2.add(pos)
                    pos.goNorthIn(size) ?: return false
                    val p = pos.goNorth()
                    if (blockPos.contains(p) || p == newBlockPos) break
                    pos = p
                }
                dir = '>'
            }
            if (dir == '>') {
                while (true) {
                    if (visitedPosEast.contains(pos) || visitedPosEast2.contains(pos)) {
                        return true
                    }
                    visitedPosEast2.add(pos)
                    pos.goEastIn(size) ?: return false
                    val p = pos.goEast()
                    if (blockPos.contains(p) || p == newBlockPos) break
                    pos = p
                }
                dir = 'v'
            }
            if (dir == 'v') {
                while (true) {
                    if (visitedPosSouth.contains(pos) || visitedPosSouth2.contains(pos)) {
                        return true
                    }
                    visitedPosSouth2.add(pos)
                    pos.goSouthIn(size) ?: return false
                    val p = pos.goSouth()
                    if (blockPos.contains(p) || p == newBlockPos) break
                    pos = p
                }
                dir = '<'
            }
            if (dir == '<') {
                while (true) {
                    if (visitedPosWest.contains(pos) || visitedPosWest2.contains(pos)) {
                        return true
                    }
                    visitedPosWest2.add(pos)
                    pos.goWestIn(size) ?: return false
                    val p = pos.goWest()
                    if (blockPos.contains(p) || p == newBlockPos) break
                    pos = p
                }
                dir = '^'
            }
        }
        return false
    }

    fun Input6.part2(): Int {
        var pos = first
        val blockPos = second
        val size = third
        val visitedPosNorth = mutableSetOf<Pos>()
        val visitedPosEast = mutableSetOf<Pos>()
        val visitedPosWest = mutableSetOf<Pos>()
        val visitedPosSouth = mutableSetOf<Pos>()
        val visitedPos = listOf(visitedPosNorth, visitedPosEast, visitedPosWest, visitedPosSouth)
        val newBlockPos = mutableSetOf<Pos>()
        outer@ while (true) {
            while (true) {
                visitedPosNorth.add(pos)
                val newBlock = pos.goNorth()
                if (blockPos.contains(newBlock)) break
                if (visitedPos.none { it.contains(newBlock) } &&
                    findLoop(
                        startPos = pos,
                        startDir = '>',
                        blockPos = blockPos,
                        newBlockPos = newBlock,
                        visitedPosNorth = visitedPosNorth,
                        visitedPosSouth = visitedPosSouth,
                        visitedPosEast = visitedPosEast,
                        visitedPosWest = visitedPosWest,
                        size = size,
                    )
                ) {
                    newBlockPos.add(newBlock)
                }
                pos = pos.goNorthIn(size) ?: break@outer
            }
            while (true) {
                visitedPosEast.add(pos)
                val newBlock = pos.goEast()
                if (blockPos.contains(newBlock)) break
                if (visitedPos.none { it.contains(newBlock) } &&
                    findLoop(
                        startPos = pos,
                        startDir = 'v',
                        blockPos = blockPos,
                        newBlockPos = newBlock,
                        visitedPosNorth = visitedPosNorth,
                        visitedPosSouth = visitedPosSouth,
                        visitedPosEast = visitedPosEast,
                        visitedPosWest = visitedPosWest,
                        size = size,
                    )
                ) {
                    newBlockPos.add(newBlock)
                }
                pos = pos.goEastIn(size) ?: break@outer
            }
            while (true) {
                visitedPosSouth.add(pos)
                val newBlock = pos.goSouth()
                if (blockPos.contains(newBlock)) break
                if (visitedPos.none { it.contains(newBlock) } &&
                    findLoop(
                        startPos = pos,
                        startDir = '<',
                        blockPos = blockPos,
                        newBlockPos = newBlock,
                        visitedPosNorth = visitedPosNorth,
                        visitedPosSouth = visitedPosSouth,
                        visitedPosEast = visitedPosEast,
                        visitedPosWest = visitedPosWest,
                        size = size,
                    )
                ) {
                    newBlockPos.add(newBlock)
                }
                pos = pos.goSouthIn(size) ?: break@outer
            }
            while (true) {
                visitedPosWest.add(pos)
                val newBlock = pos.goWest()
                if (blockPos.contains(newBlock)) break
                if (visitedPos.none { it.contains(newBlock) } &&
                    findLoop(
                        startPos = pos,
                        startDir = '^',
                        blockPos = blockPos,
                        newBlockPos = newBlock,
                        visitedPosNorth = visitedPosNorth,
                        visitedPosSouth = visitedPosSouth,
                        visitedPosEast = visitedPosEast,
                        visitedPosWest = visitedPosWest,
                        size = size,
                    )
                ) {
                    newBlockPos.add(newBlock)
                }
                pos = pos.goWestIn(size) ?: break@outer
            }
        }
        return newBlockPos.size
    }

    val testInput = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 41)
    val input = readInput("Day06").parse()
    measureTimeMillis { input.part1().println() }.also { println("time: $it") }
    check(testInput.part2() == 6)
    measureTimeMillis { input.part2().println() }.also { println("time: $it") }
}
