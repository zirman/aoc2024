import java.util.PriorityQueue

private typealias Input23 = Map<String, List<String>>

// index is next computer than needs to check if it is connected to previous ones in the list
data class Network(val index: Int, val computers: List<String>)

fun main() {
    fun List<String>.parse(): Input23 {
        return flatMap {
            val (a, b) = it.split('-')
            listOf(a to b, b to a)
        }
            .groupBy { it.first }
            .mapValues { (_, b) -> b.map { it.second } }
    }

    fun Input23.part1(): Int = buildSet<Set<String>> {
        this@part1.forEach { (computer1, connections) ->
            connections.forEach { computer2 ->
                getValue(computer2).forEach { computer3 ->
                    if (computer3 in connections && (computer1.startsWith('t') || computer2.startsWith('t') || computer3.startsWith(
                            't'
                        ))
                    ) {
                        add(setOf(computer1, computer2, computer3))
                    }
                }
            }
        }
    }.size

    fun Input23.part2(): String {
        val queue = PriorityQueue<Network>(compareByDescending { it.computers.size })
        forEach { queue.add(Network(2, listOf(it.key) + it.value)) }
        var biggestSoFar = emptyList<String>()
        outer@while (true) {
            val network = queue.remove()
            // all candidate networks are smaller than best found
            if (network.computers.size < biggestSoFar.size) {
                return biggestSoFar.sorted().joinToString(",")
            }
            for (i in network.index..<network.computers.size) {
                val nextComputerConnections = this@part2.getValue(network.computers[i])
                if (network.computers.subList(0, i).all { it in nextComputerConnections }.not()) {
                    // this computer is not connected to all previous computers so we create two candidate networks
                    queue.add(Network(i, network.computers.dropAt(i)))
                    val connected = network.computers.subList(0, i + 1).filter { it in nextComputerConnections }
                    queue.add(Network(connected.size, connected + network.computers.subList(i + 1, network.computers.size)))
                    continue@outer
                }
            }
            biggestSoFar = network.computers
        }
    }

    val testInput = """
        kh-tc
        qp-kh
        de-cg
        ka-co
        yn-aq
        qp-ub
        cg-tb
        vc-aq
        tb-ka
        wh-tc
        yn-cg
        kh-ub
        ta-co
        de-co
        tc-td
        tb-wq
        wh-td
        ta-ka
        td-qp
        aq-cg
        wq-ub
        ub-vc
        de-ta
        wq-aq
        wq-vc
        wh-yn
        ka-de
        kh-ta
        co-tc
        wh-qp
        tb-vc
        td-yn
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 7)
    val input = readInput("Day23").parse()
    printlnMeasureTimeMillis { println("part1: ${input.part1()}") }
    check(testInput.part2() == "co,de,ka,ta")
    printlnMeasureTimeMillis { println("part2: ${input.part2()}") }
}
