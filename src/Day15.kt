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
            val s = move()
            if (s in size) {
//                boxes.stringify().println()
                when (boxes[s.row][s.col]) {
                    '.' -> {
                        boxes[s.row][s.col] = boxes[row][col]
                        boxes[row][col] = '.'
                    }

                    'O' -> {
                        if (s.push(move)) {
                            boxes[s.row][s.col] = boxes[row][col]
                            boxes[row][col] = '.'
                        } else {
                            return false
                        }
                    }

                    '#' -> {
                        return false
                    }

                    else -> {
                        throw IllegalStateException()
                    }
                }
                return true
            } else {
                return false
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
        return boxes.gpsSum('O')
    }

    fun Input15.part2(): Int {
        val (boxes, size, moves) = this
        var pos = boxes.startingPosition(size)
        for (dir in moves) {
//            boxes.stringify().println()
            when (dir) {
                'v' -> {
                    fun Pos.canMove(): Boolean {
                        val s = goSouth()
                        if (s in size) {
                            when (boxes[s.row][s.col]) {
                                '.' -> {
                                    return true
                                }

                                '[' -> {
                                    return s.canMove() && s.goEast().canMove()
                                }

                                ']' -> {
                                    return s.canMove() && s.goWest().canMove()
                                }

                                '#' -> {
                                    return false
                                }

                                else -> {
                                    throw IllegalStateException()
                                }
                            }
                            return true
                        } else {
                            return false
                        }
                    }

                    fun Pos.move() {
                        val p = goSouth()
                        when (boxes[p.row][p.col]) {
                            '.' -> {
                                boxes[p.row][p.col] = boxes[row][col]
                                boxes[row][col] = '.'
                            }

                            '[' -> {
                                p.move()
                                boxes[p.row][p.col] = boxes[row][col]
                                boxes[row][col] = '.'
                                p.goEast().move()
                            }

                            ']' -> {
                                p.move()
                                boxes[p.row][p.col] = boxes[row][col]
                                boxes[row][col] = '.'
                                p.goWest().move()
                            }

                            '#' -> {
                            }

                            else -> {
                                throw IllegalStateException()
                            }
                        }
                    }
                    if (pos.canMove()) {
                        pos.move()
                        pos = pos.goSouth()
                    }
                }

                '<' -> {
                    fun Pos.push(): Boolean {
                        val p = goWest()
                        if (p in size) {
                            when (boxes[p.row][p.col]) {
                                '.' -> {
                                    boxes[p.row][p.col] = boxes[row][col]
                                    boxes[row][col] = '.'
                                }

                                '[', ']' -> {
                                    if (p.push()) {
                                        boxes[p.row][p.col] = boxes[row][col]
                                        boxes[row][col] = '.'
                                    } else {
                                        return false
                                    }
                                }

                                '#' -> {
                                    return false
                                }

                                else -> {
                                    throw IllegalStateException()
                                }
                            }
                            return true
                        } else {
                            return false
                        }
                    }
                    if (pos.push()) {
                        pos = pos.goWest()
                    }
                }

                '>' -> {
                    fun Pos.push(): Boolean {
                        val p = goEast()
                        if (p in size) {
                            when (boxes[p.row][p.col]) {
                                '.' -> {
                                    boxes[p.row][p.col] = boxes[row][col]
                                    boxes[row][col] = '.'
                                }

                                '[', ']' -> {
                                    if (p.push()) {
                                        boxes[p.row][p.col] = boxes[row][col]
                                        boxes[row][col] = '.'
                                    } else {
                                        return false
                                    }
                                }

                                '#' -> {
                                    return false
                                }

                                else -> {
                                    throw IllegalStateException()
                                }
                            }
                            return true
                        } else {
                            return false
                        }
                    }
                    if (pos.push()) {
                        pos = pos.goEast()
                    }
                }

                '^' -> {
                    fun Pos.canMove(): Boolean {
                        val s = goNorth()
                        if (s in size) {
                            when (boxes[s.row][s.col]) {
                                '.' -> {
                                    return true
                                }

                                '[' -> {
                                    return s.canMove() && s.goEast().canMove()
                                }

                                ']' -> {
                                    return s.canMove() && s.goWest().canMove()
                                }

                                '#' -> {
                                    return false
                                }

                                else -> {
                                    throw IllegalStateException()
                                }
                            }
                            return true
                        } else {
                            return false
                        }
                    }

                    fun Pos.move() {
                        val p = goNorth()
                        when (boxes[p.row][p.col]) {
                            '.' -> {
                                boxes[p.row][p.col] = boxes[row][col]
                                boxes[row][col] = '.'
                            }

                            '[' -> {
                                p.move()
                                boxes[p.row][p.col] = boxes[row][col]
                                boxes[row][col] = '.'
                                p.goEast().move()
                            }

                            ']' -> {
                                p.move()
                                boxes[p.row][p.col] = boxes[row][col]
                                boxes[row][col] = '.'
                                p.goWest().move()
                            }

                            '#' -> {
                            }

                            else -> {
                                throw IllegalStateException()
                            }
                        }
                    }
                    if (pos.canMove()) {
                        pos.move()
                        pos = pos.goNorth()
                    }
                }
            }
        }
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
    printlnMeasureTimeMillis { input.parse1().part1().println() }
    check(testInput2.parse2().part2() == 9021)
    printlnMeasureTimeMillis { input.parse2().part2().println() }
}
