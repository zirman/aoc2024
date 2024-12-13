fun main() {
    val buttonRegex = """Button [A-Z]: X\+(\d+), Y\+(\d+)""".toRegex()
    val prizeRegex = """Prize: X=(\d+), Y=(\d+)""".toRegex()
    fun List<String>.part1(offset: Long): Long = sumOf { line ->
        val (a, b, p) = line.split("\n")
        val (axs, ays) = buttonRegex.matchEntire(a)!!.destructured
        val (bxs, bys) = buttonRegex.matchEntire(b)!!.destructured
        val (pxs, pys) = prizeRegex.matchEntire(p)!!.destructured
        val ax = axs.toLong()
        val ay = ays.toLong()
        val bx = bxs.toLong()
        val by = bys.toLong()
        val px = pxs.toLong() + offset
        val py = pys.toLong() + offset

        val bxay = bx * ay
        val byax = by * ax
        val an = py * bx - px * by
        val ad = bxay - byax
        val bn = py * ax - px * ay
        val bd = byax - bxay
        if (an % ad == 0L && bn % bd == 0L) (3 * (an / ad)) + (bn / bd) else 0
    }

    val testInput = """
        Button A: X+94, Y+34
        Button B: X+22, Y+67
        Prize: X=8400, Y=5400

        Button A: X+26, Y+66
        Button B: X+67, Y+21
        Prize: X=12748, Y=12176

        Button A: X+17, Y+86
        Button B: X+84, Y+37
        Prize: X=7870, Y=6450

        Button A: X+69, Y+23
        Button B: X+27, Y+71
        Prize: X=18641, Y=10279
    """.trimIndent().split("\n\n")
    check(testInput.part1(0) == 480L)
    val input = readInput("Day13").joinToString("\n").split("\n\n")
    printlnMeasureTimeMillis { input.part1(0).println() }
    printlnMeasureTimeMillis { input.part1(10000000000000).println() }
}
