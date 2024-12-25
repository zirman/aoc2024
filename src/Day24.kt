private typealias Input24 = Wires

private data class Wires(val inputs: Map<String, Boolean>, val gates: Map<String, Gate>)
private data class Gate(val lhs: String, val op: String, val rhs: String)

fun main() {
    val inputRegex = """^([a-z]\d\d): ([01])$""".toRegex()
    val gateRegex = """^([a-z\d]{3}) (AND|XOR|OR) ([a-z\d]{3}) -> ([a-z\d]{3})$""".toRegex()
    fun List<String>.parse(): Input24 {
        val (inputsStr, gatesStr) = joinToString("\n").split("\n\n")
        return Wires(
            buildMap<String, Boolean> {
                inputsStr.split("\n").map { line ->
                    val (name, valueStr) = inputRegex.matchEntire(line)!!.destructured
                    set(name, valueStr.toInt() != 0)
                }
            },
            buildMap<String, Gate> {
                gatesStr.split("\n").map { line ->
                    val (lhs, op, rhs, dst) = gateRegex.matchEntire(line)!!.destructured
                    set(dst, Gate(lhs, op, rhs))
                }
            },
        )
    }

    fun Input24.part1(): Long {
        val memoInputs = inputs.toMutableMap()
        val gates = gates
        fun recur(name: String): Boolean = memoInputs.getOrPut(name) {
            val gate = gates.getValue(name)
            when (gate.op) {
                "AND" -> recur(gate.lhs) and recur(gate.rhs)
                "XOR" -> recur(gate.lhs) xor recur(gate.rhs)
                "OR" -> recur(gate.lhs) or recur(gate.rhs)
                else -> throw IllegalStateException()
            }
        }
        return gates.keys.filter { it.startsWith('z') }
            .sortedDescending()
            .map { recur(it) }
            .joinToString("") { if (it) "1" else "0" }
            .toLong(2)
    }

    fun Long.nextOneDigit(): List<Int> = buildList {
        var nn = this@nextOneDigit
        var bit = 0
        while (bit < Long.SIZE_BITS) {
            if (nn and 0b1L == 1L) {
                add(bit)
            }
            bit++
            nn = nn shr 1
        }
    }

    fun Map<String, Gate>.getGates(gate: String): List<String> = buildList {
        fun MutableList<String>.addGates(gate: String) {
            val g = this@getGates[gate]
            if (g != null) {
                add(gate)
                addGates(g.lhs)
                addGates(g.rhs)
            }
        }
        addGates(gate)
    }

    fun Input24.part2(): String {
        val x = inputs.keys.filter { it.startsWith('x') }.sortedDescending()
            .joinToString("") { if (inputs.getValue(it)) "1" else "0" }.toLong(2)
        val y = inputs.keys.filter { it.startsWith('y') }.sortedDescending()
            .joinToString("") { if (inputs.getValue(it)) "1" else "0" }.toLong(2)
        val zs = gates.keys.filter { it.startsWith('z') }.sortedDescending()
        val z = x + y
        val output = part1()
        val places = (z xor output).also { it.toString(2).println() }.nextOneDigit()
        fun List<String>.foo(): List<String> {
            fun MutableList<String>.recur(index: Int) {

            }
            return buildList {

            }
        }
        places
            .map {
                val x = it.toString()
                if (x.length == 1) {
                    "z0${it}"
                } else {
                    "z${it}"
                }
            }
            .flatMap {
                gates.getGates(it)
            }
            .distinct()
            .foo()
            .println()

        TODO()
    }

    val testInput = """
        x00: 1
        x01: 0
        x02: 1
        x03: 1
        x04: 0
        y00: 1
        y01: 1
        y02: 1
        y03: 1
        y04: 1

        ntg XOR fgs -> mjb
        y02 OR x01 -> tnw
        kwq OR kpj -> z05
        x00 OR x03 -> fst
        tgd XOR rvg -> z01
        vdt OR tnw -> bfw
        bfw AND frj -> z10
        ffh OR nrd -> bqk
        y00 AND y03 -> djm
        y03 OR y00 -> psh
        bqk OR frj -> z08
        tnw OR fst -> frj
        gnj AND tgd -> z11
        bfw XOR mjb -> z00
        x03 OR x00 -> vdt
        gnj AND wpb -> z02
        x04 AND y00 -> kjc
        djm OR pbm -> qhw
        nrd AND vdt -> hwm
        kjc AND fst -> rvg
        y04 OR y02 -> fgs
        y01 AND x02 -> pbm
        ntg OR kjc -> kwq
        psh XOR fgs -> tgd
        qhw XOR tgd -> z09
        pbm OR djm -> kpj
        x03 XOR y03 -> ffh
        x00 XOR y04 -> ntg
        bfw OR bqk -> z06
        nrd XOR fgs -> wpb
        frj XOR qhw -> z04
        bqk OR frj -> z07
        y03 OR x01 -> nrd
        hwm AND bqk -> z03
        tgd XOR rvg -> z12
        tnw OR pbm -> gnj
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 2024L)
    val input = readInput("Day24").parse()
    printlnMeasureTimeMillis { println("part1: ${input.part1()}") }
//    val testInput2 = """
//        x00: 0
//        x01: 1
//        x02: 0
//        x03: 1
//        x04: 0
//        x05: 1
//        y00: 0
//        y01: 0
//        y02: 1
//        y03: 1
//        y04: 0
//        y05: 1
//
//        x00 AND y00 -> z05
//        x01 AND y01 -> z02
//        x02 AND y02 -> z01
//        x03 AND y03 -> z03
//        x04 AND y04 -> z04
//        x05 AND y05 -> z00
//    """.trimIndent().split('\n').parse()
//    check(testInput2.part2() == "z00,z01,z02,z05")
//    printlnMeasureTimeMillis { println("part2: ${input.part2()}") }
}
