private typealias Input17 = Machine

data class Machine(val a: Int, val b: Int, val c: Int, val insts: List<Int>)

fun main() {
    val registerRegex = """Register [A-Z]: (\d+)""".toRegex()
    val programRegex = """Program: (\d[,\d]*)""".toRegex()
    fun List<String>.parse(): Input17 {
        val (regAStr) = registerRegex.matchEntire(this[0])!!.destructured
        val (regBStr) = registerRegex.matchEntire(this[1])!!.destructured
        val (regCStr) = registerRegex.matchEntire(this[2])!!.destructured
        val (instsStr) = programRegex.matchEntire(this[4])!!.destructured
        var a = regAStr.toInt()
        var b = regBStr.toInt()
        var c = regCStr.toInt()
        val insts = instsStr.split(',').map { it.toInt() }
        return Machine(a, b, c, insts)
    }

    fun Input17.part1(): List<Int> {
        var a = a
        var b = b
        var c = c
        var ip = 0
        fun combo(x: Int): Int {
            return when (x) {
                0, 1, 2, 3 -> x
                4 -> a
                5 -> b
                6 -> c
                else -> throw IllegalStateException()
            }
        }
        return buildList {
            while (ip in 0..<insts.size - 1) {
                when (insts[ip]) {
                    0 -> { // adv
                        a = a shr combo(insts[ip + 1])
                        ip += 2
                    }

                    1 -> { // bxl
                        b = b xor insts[ip + 1]
                        ip += 2
                    }

                    2 -> { // bst
                        b = combo(insts[ip + 1]).mod(8)
                        ip += 2
                    }

                    3 -> { // jnz
                        if (a == 0) {
                            ip += 2
                        } else {
                            ip = insts[ip + 1]
                        }
                    }

                    4 -> { // bxc
                        b = b xor c
                        ip += 2
                    }

                    5 -> { // out
                        add(combo(insts[ip + 1]).mod(8))
                        ip += 2
                    }

                    6 -> { // bdv
                        b = a shr combo(insts[ip + 1])
                        ip += 2
                    }

                    7 -> {
                        c = a shr combo(insts[ip + 1])
                        ip += 2
                    }
                }
            }
        }
    }

    fun Input17.part2(): Int {
        for (a in a..Int.MAX_VALUE) {
            if (copy(a = a).part1() == insts) {
                return a
            }
        }
        throw IllegalStateException()
    }

    fun Input17.part3(): Long {
        val insts = insts.map { it.toLong() }
        for (a in 0..Long.MAX_VALUE) {
            if (a % 100_000_000 == 0L) println(a)
            var i = 0
            var a = a.toLong()
            var b = b.toLong()
            var c = c.toLong()
            while (true) {
                //2, 4
                b = a.mod(8L)
                //1, 1
                b = b xor 1
                //7, 5
                c = a shr b.toInt()
                //4, 0
                b = b xor c
                //0, 3
                a = a shr 3
                //1, 6
                b = b xor 6
                //5, 5
                if (i !in insts.indices || b.mod(8L) != insts[i]) {
                    break
                }
                i++
                //3, 0
                if (a == 0L && i == insts.size) {
                    return a
                }
            }
        }
        throw IllegalStateException()
    }

    val testInput1 = """
        Register A: 729
        Register B: 0
        Register C: 0

        Program: 0,1,5,4,3,0
    """.trimIndent().split('\n').parse()
    check(testInput1.part1().joinToString(",") == "4,6,3,5,6,3,5,2,1,0")
    val input = """
        Register A: 30899381
        Register B: 0
        Register C: 0

        Program: 2,4,1,1,7,5,4,0,0,3,1,6,5,5,3,0
    """.trimIndent().split('\n').parse()
    printlnMeasureTimeMillis { input.part1().println() }
    val testInput2 = """
        Register A: 117440
        Register B: 0
        Register C: 0

        Program: 0,3,5,4,3,0
    """.trimIndent().split('\n').parse()
    check(testInput2.part2() == 117440)
    printlnMeasureTimeMillis { input.part3().println() }
}
