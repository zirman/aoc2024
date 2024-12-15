private typealias Input15 = Triple<List<MutableList<Char>>, Size, String>

fun main() {
    fun String.parse1(): Input15 {
        val (boxesString, movesString) = split("\n\n")
        val boxes = boxesString.split('\n').map { it.toMutableList() }
        return Triple(boxes, boxes.toSize2(), movesString.filter { it != '\n' })
    }

    fun String.parse2(): Input15 {
        val (boxesString, moves) = split("\n\n")
        val boxes = boxesString
            .split('\n')
            .map {
                it
                    .flatMap {
                        when (it) {
                            '.' -> ".."
                            'O' -> "[]"
                            '#' -> "##"
                            '@' -> "@."
                            else -> throw IllegalStateException()
                        }.toList()
                    }
                    .toMutableList()
            }
        return Triple(boxes, boxes.toSize2(), moves.filter { it != '\n' })
    }

    fun List<List<Char>>.stringify(): String = joinToString("\n") { it.joinToString("") }

    fun List<List<Char>>.gpsSum(box: Char): Int {
        var gpsSum = 0
        for (row in indices) {
            for (col in this[row].indices) {
                if (this[row][col] == box) {
                    gpsSum += 100 * row + col
                }
            }
        }
        return gpsSum
    }

    fun List<List<Char>>.startingPosition(size: Size): Pos {
        for (row in 0..<size.height) {
            for (col in 0..<size.width) {
                if (this[row][col] == '@') {
                    return Pos(row, col)
                }
            }
        }
        throw IllegalStateException()
    }

    fun Input15.part1(): Int {
        val (boxes, size, moves) = this
        fun Pos.push(move: Pos.() -> Pos): Boolean {
            val p = move()
            val c = boxes[p.row][p.col]
            return if (c == '.' || (c == 'O' && p.push(move))) {
                boxes[p.row][p.col] = boxes[row][col]
                boxes[row][col] = '.'
                true
            } else {
                false
            }
        }

        var pos = boxes.startingPosition(size)
        for (dir in moves) {
            when (dir) {
                'v' -> {
                    if (pos.push { goSouth() }) {
                        pos = pos.goSouth()
                    }
                }

                '<' -> {
                    if (pos.push { goWest() }) {
                        pos = pos.goWest()
                    }
                }

                '>' -> {
                    if (pos.push { goEast() }) {
                        pos = pos.goEast()
                    }
                }

                '^' -> {
                    if (pos.push { goNorth() }) {
                        pos = pos.goNorth()
                    }
                }
            }
        }
        boxes.stringify().println()
        return boxes.gpsSum('O')
    }

    fun Input15.part2(): Int {
        val (boxes, size, moves) = this
        fun Pos.canPushVertical(move: Pos.() -> Pos): Boolean {
            val p = move()
            return when (boxes[p.row][p.col]) {
                '.' -> true
                '[' -> p.canPushVertical(move) && p.goEast().canPushVertical(move)
                ']' -> p.canPushVertical(move) && p.goWest().canPushVertical(move)
                '#' -> false
                else -> throw IllegalStateException()
            }
        }

        fun Pos.pushVertical(move: Pos.() -> Pos) {
            val p = move()
            when (boxes[p.row][p.col]) {
                '.' -> {
                    boxes[p.row][p.col] = boxes[row][col]
                    boxes[row][col] = '.'
                }

                '[' -> {
                    p.pushVertical(move)
                    p.goEast().pushVertical(move)
                    boxes[p.row][p.col] = boxes[row][col]
                    boxes[row][col] = '.'
                }

                ']' -> {
                    p.pushVertical(move)
                    p.goWest().pushVertical(move)
                    boxes[p.row][p.col] = boxes[row][col]
                    boxes[row][col] = '.'
                }

                else -> {
                    throw IllegalStateException()
                }
            }
        }

        fun Pos.pushHorizontal(move: Pos.() -> Pos): Boolean {
            val p = move()
            val c = boxes[p.row][p.col]
            return if (c == '.' || (c in "[]" && p.pushHorizontal(move))) {
                boxes[p.row][p.col] = boxes[row][col]
                boxes[row][col] = '.'
                true
            } else {
                false
            }
        }

        var pos = boxes.startingPosition(size)
        for (dir in moves) {
            when (dir) {
                '<' -> if (pos.pushHorizontal { goWest() }) {
                    pos = pos.goWest()
                }

                '>' -> if (pos.pushHorizontal { goEast() }) {
                    pos = pos.goEast()
                }

                'v' -> if (pos.canPushVertical { goSouth() }) {
                    pos.pushVertical { goSouth() }
                    pos = pos.goSouth()
                }

                '^' -> if (pos.canPushVertical { goNorth() }) {
                    pos.pushVertical { goNorth() }
                    pos = pos.goNorth()
                }

                else -> throw IllegalStateException()
            }
        }
        boxes.stringify().println()
        return boxes.gpsSum('[')
    }

    val testInput = """
        ########
        #..O.O.#
        ##@.O..#
        #...O..#
        #.#.O..#
        #...O..#
        #......#
        ########

        <^^>>>vv<v>>v<<
    """.trimIndent()
    check(testInput.parse1().part1() == 2028)
    val testInput2 = """
        ##########
        #..O..O.O#
        #......O.#
        #.OO..O.O#
        #..O@..O.#
        #O#..O...#
        #O..O..O.#
        #.OO.O.OO#
        #....O...#
        ##########

        <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
        vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
        ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
        <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
        ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
        ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
        >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
        <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
        ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
        v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
    """.trimIndent()
    check(testInput2.parse1().part1() == 10092)
    val input = readInput("Day15").joinToString("\n")
    printlnMeasureTimeMillis { println("part 1: ${input.parse1().part1()}") }
    check(testInput2.parse2().part2() == 9021)
    printlnMeasureTimeMillis { println("part 2: ${input.parse2().part2()}") }
}
