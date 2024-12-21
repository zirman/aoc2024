private typealias Input21 = List<String>

private data class KeypadMoves(val prev: List<Char>, val pos: Pos)

fun main() {
    val numpadCharToPos = mapOf(
        '7' to Pos(0, 0),
        '8' to Pos(0, 1),
        '9' to Pos(0, 2),
        '4' to Pos(1, 0),
        '5' to Pos(1, 1),
        '6' to Pos(1, 2),
        '1' to Pos(2, 0),
        '2' to Pos(2, 1),
        '3' to Pos(2, 2),
        '0' to Pos(3, 1),
        'A' to Pos(3, 2),
    )

    val numpadPosToChar = mapOf(
        Pos(0, 0) to '7',
        Pos(0, 1) to '8',
        Pos(0, 2) to '9',
        Pos(1, 0) to '4',
        Pos(1, 1) to '5',
        Pos(1, 2) to '6',
        Pos(2, 0) to '1',
        Pos(2, 1) to '2',
        Pos(2, 2) to '3',
        Pos(3, 1) to '0',
        Pos(3, 2) to 'A',
    )

    val memoDialNumpadPos = mutableMapOf<Pair<Pos, Pos>, List<String>>()
    fun Pos.dialNumpadPos(pos: Pos): List<String> = memoDialNumpadPos.getOrPut(Pair(this, pos)) {
        buildList {
            fun Pos.recur(prefix: String) {
                if (this == pos) {
                    add(prefix + 'A')
                    return
                }
                if (col > pos.col) {
                    goWest().takeIf { it in numpadPosToChar }?.recur(prefix + '<')
                }
                if (col < pos.col) {
                    goEast().takeIf { it in numpadPosToChar }?.recur(prefix + '>')
                }
                if (row > pos.row) {
                    goNorth().takeIf { it in numpadPosToChar }?.recur(prefix + '^')
                }
                if (row < pos.row) {
                    goSouth().takeIf { it in numpadPosToChar }?.recur(prefix + 'v')
                }
            }
            recur("")
        }
    }

    fun String.dialNumpadString(): List<String> {
        fun String.recur(index: Int, pos: Pos): List<String> {
            if (index !in this@dialNumpadString.indices) return listOf(this)
            val nextPos = numpadCharToPos[this@dialNumpadString[index]]!!
            return pos.dialNumpadPos(nextPos).flatMap { it ->
                (this + it).recur(index + 1, numpadCharToPos[this@dialNumpadString[index]]!!)
            }
        }
        return "".recur(0, Pos(3, 2))
    }

    val dirPadCharToPos = mapOf(
        '^' to Pos(0, 1),
        'A' to Pos(0, 2),
        '<' to Pos(1, 0),
        'v' to Pos(1, 1),
        '>' to Pos(1, 2),
    )

    val dirPadPosToChar = mapOf(
        Pos(0, 1) to '^',
        Pos(0, 2) to 'A',
        Pos(1, 0) to '<',
        Pos(1, 1) to 'v',
        Pos(1, 2) to '>',
    )

    val memoDialDirPadPos = mutableMapOf<Pair<Pos, Pos>, List<String>>()
    fun Pos.dialDirPadPos(pos: Pos): List<String> = memoDialDirPadPos.getOrPut(Pair(this, pos)) {
        buildList {
            fun Pos.recur(prefix: String) {
                if (this == pos) {
                    add(prefix + 'A')
                    return
                }
                if (col > pos.col) {
                    goWest().takeIf { it in dirPadPosToChar }?.recur(prefix + '<')
                }
                if (col < pos.col) {
                    goEast().takeIf { it in dirPadPosToChar }?.recur(prefix + '>')
                }
                if (row > pos.row) {
                    goNorth().takeIf { it in dirPadPosToChar }?.recur(prefix + '^')
                }
                if (row < pos.row) {
                    goSouth().takeIf { it in dirPadPosToChar }?.recur(prefix + 'v')
                }
            }
            recur("")
        }
    }

    fun String.dialDirPadString(): List<String> {
        fun String.recur(index: Int, pos: Pos): List<String> {
            if (index !in this@dialDirPadString.indices) return listOf(this)
            val nextPos = dirPadCharToPos[this@dialDirPadString[index]]!!
            return pos.dialDirPadPos(nextPos).flatMap { it ->
                (this + it).recur(index + 1, dirPadCharToPos[this@dialDirPadString[index]]!!)
            }
        }
        return "".recur(0, Pos(0, 2))
    }

    fun Input21.part1(): Long {
        return sumOf { doorCode ->
            doorCode.dialNumpadString().flatMap {
                it.dialDirPadString().flatMap {
                    it.dialDirPadString()
                }
            }
                .minBy { it.length }.length * doorCode.dropLast(1).toLong()
//            doorCode.dialNumpadString().flatMap {
//                it.dialDirPadString().flatMap {
//                    it.dialDirPadString()
//                }
//            }.sumOf { doorCode.dropLast(1).toLong() * it.length }
        }
    }

    fun Input21.part2(): Int {
        TODO()
    }

    fun List<List<Char>>.stringify(): String = joinToString("\n") { it.joinToString("") }

    check(
        "029A".dialNumpadString() == """
            <A^A>^^AvvvA
            <A^A^>^AvvvA
            <A^A^^>AvvvA
        """.trimIndent().split('\n')
    )

    "029A".dialNumpadString().flatMap {
        it.dialDirPadString().flatMap {
            it.dialDirPadString()
        }
    }.minBy { it.length }.length.println()
//    .sumOf {
//        val x = it.length
//        29L * x
//    }.println()

//    "029A".dialNumpadString().flatMap {
//        it.dialDirPadString().flatMap {
//            it.dialDirPadString()
//        }
//    }.map { it. }
//    check(
//        "029A".dialDirPadString().stringify() == """
//            <A^A>^^AvvvA
//        """.trimIndent()
//    )

//    val testInput = """
//    """.trimIndent().split('\n')
//    check(testInput.part1() == TODO())
//
    val input = """
        140A
        143A
        349A
        582A
        964A
    """.trimIndent().split('\n')
    input.part1().println()
//    check(testInput.part2() == 4)
//    input.part2().println()
}
