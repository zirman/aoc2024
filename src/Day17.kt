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
                        b = combo(insts[ip + 1]) and 0b111
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
                        add(combo(insts[ip + 1]) and 0b111)
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

    fun Input17.part2(): Long {
        val insts = insts.map { it.toLong() }

        fun search(i: Long, a: Long, max: Long): Sequence<Long> = sequence {
            for (n in 0L..max) {
                var a = (a shl 3) + n
                var b = a and 0b111
                b = b xor 0b001
                var c = a shr b.toInt()
                b = b xor c
                b = b xor 0b110
                b = b and 0b111
                if (b == i) {
                    yield(a)
                }
            }
        }

        return search(insts[15], 0, Long.MAX_VALUE).flatMap {
            search(insts[14], it, 7).flatMap {
                println(it.toString(8))
                search(insts[13], it, 7).flatMap {
                    println(it.toString(8))
                    search(insts[12], it, 7).flatMap {
                        println(it.toString(8))
                        search(insts[11], it, 7).flatMap {
                            println(it.toString(8))
                            search(insts[10], it, 7).flatMap {
                                println(it.toString(8))
                                search(insts[9], it, 7).flatMap {
                                    println(it.toString(8))
                                    search(insts[8], it, 7).flatMap {
                                        println(it.toString(8))
                                        search(insts[7], it, 7).flatMap {
                                            println(it.toString(8))
                                            search(insts[6], it, 7).flatMap {
                                                println(it.toString(8))
                                                search(insts[5], it, 7).flatMap {
                                                    println(it.toString(8))
                                                    search(insts[4], it, 7).flatMap {
                                                        println(it.toString(8))
                                                        search(insts[3], it, 7).flatMap {
                                                            println(it.toString(8))
                                                            search(insts[2], it, 7).flatMap {
                                                                println(it.toString(8))
                                                                search(insts[1], it, 7).flatMap {
                                                                    println(it.toString(8))
                                                                    search(insts[0], it, 7)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.first()
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
    printlnMeasureTimeMillis { println("part1: ${input.part1().joinToString(",")}") }
    printlnMeasureTimeMillis { println("part2: ${input.part2()}") }
}
