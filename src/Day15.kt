private typealias Input15 = Pair<List<MutableList<Char>>, String>

fun main() {
    fun String.parse(): Input15 {
        val (boxes, moves) = split("\n\n")
        return Pair(boxes.split('\n').map { it.toMutableList() }, moves.filter { it != '\n' })
    }

//    fun String.parse2(): Input15 {
//
//    }

    fun Input15.part1(): Int {
        val (boxes, moves) = this
        var size = Size(width = boxes[0].size, height = boxes.size)
        var pos = Pos(0, 0)
        outer@ for (row in 0..<size.height) {
            for (col in 0..<size.width) {
                if (boxes[row][col] == '@') {
                    pos = Pos(row, col)
                    break@outer
                }
            }
        }
        for (dir in moves) {
//            boxes.joinToString("\n") { it.joinToString("") }.println()
//            println()
            when (dir) {
                'v' -> {
                    fun Pos.pushSouth(): Boolean {
                        val s = goSouth()
                        if (s in size) {
                            when (boxes[s.row][s.col]) {
                                '.' -> {
                                    boxes[s.row][s.col] = boxes[row][col]
                                    boxes[row][col] = '.'
                                }

                                'O' -> {
                                    if (s.pushSouth()) {
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
                    if (pos.pushSouth()) {
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

                                'O' -> {
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

                                'O' -> {
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
                    fun Pos.push(): Boolean {
                        val p = goNorth()
                        if (p in size) {
                            when (boxes[p.row][p.col]) {
                                '.' -> {
                                    boxes[p.row][p.col] = boxes[row][col]
                                    boxes[row][col] = '.'
                                }

                                'O' -> {
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
                        pos = pos.goNorth()
                    }
                }
            }
        }
        var gpsSum = 0
        for (row in boxes.indices) {
            for (col in boxes[row].indices) {
                if (boxes[row][col] == 'O') {
                    gpsSum += 100 * row + col
                }
            }
        }
        return gpsSum
    }

    fun String.part2(): Int {
        val (boxes2, moves2) = split("\n\n")
        val boxes = boxes2.split('\n').map {
            it.map {
                when (it) {
                    '.' -> ".."
                    'O' -> "[]"
                    '#' -> "##"
                    '@' -> "@."
                    else -> throw IllegalStateException()
                }
            }.joinToString("").toMutableList()
        }
        val moves = moves2.filter { it != '\n' }
        var size = Size(width = boxes[0].size, height = boxes.size)
        var pos = Pos(0, 0)
        outer@ for (row in 0..<size.height) {
            for (col in 0..<size.width) {
                if (boxes[row][col] == '@') {
                    pos = Pos(row, col)
                    break@outer
                }
            }
        }
        for (dir in moves) {
            boxes.joinToString("\n") { it.joinToString("") }.println()
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
        var gpsSum = 0
        for (row in boxes.indices) {
            for (col in boxes[row].indices) {
                if (boxes[row][col] == '[') {
                    gpsSum += 100 * row + col
                }
            }
        }
        return gpsSum
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
    check(testInput.parse().part1() == 2028)
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
    check(testInput2.parse().part1() == 10092)
    val input = readInput("Day15").joinToString("\n")
    input.parse().part1().println()
    check(testInput2.part2() == 9021)
    input.part2().println()
}
