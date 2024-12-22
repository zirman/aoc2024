private typealias Input22 = List<Long>

private fun Long.nextSecret(): Long {
    var s = this
    s = (s xor (s * 64)).mod(16777216L)
    s = (s xor (s / 32)).mod(16777216L)
    s = (s xor (s * 2048)).mod(16777216L)
    return s
}

fun main() {
    fun List<String>.parse(): Input22 = map { it.toLong() }

    fun Input22.part1(): Long = sumOf {
        var s = it
        repeat(2000) {
            s = s.nextSecret()
        }
        s
    }

    fun Input22.part2(): Long {
        val seqs = map {
            var s = it
            val prices = mutableListOf<Long>()
            val diffs = mutableListOf<Long>()
            repeat(2000) {
                val ns = s.nextSecret()
                val sm = s % 10
                val nsm = ns % 10
                prices.add(nsm)
                diffs.add(nsm - sm)
                s = ns
            }
            Pair(prices as List<Long>, diffs as List<Long>)
        }

        fun List<Pair<List<Long>, List<Long>>>.bananaSum(market: List<Long>): Long = sumOf { (price, diff) ->
            val i = diff.asSequence().windowed(4).indexOf(market)
            //val k = diff.asSequence().windowed(4).lastIndexOf(market)
            if (i != -1) price[i + 3] else 0
        }

        fun List<Pair<List<Long>, List<Long>>>.bestPricesMap(): List<Map<List<Long>, Long>> {
            return map { (prices, diffs) ->
                buildMap {
                    diffs.windowed(4).forEachIndexed { index, diffs ->
                        val currentPrice = prices[index + 3]
                        compute(diffs) { _, bestPriceSoFar ->
                            (bestPriceSoFar ?: 0).coerceAtLeast(currentPrice)
                        }
                    }
                }
            }
        }

        val bestPrices = seqs.bestPricesMap()
        val bestDiff = bestPrices.flatMap { it.keys }.distinct().maxBy { diffSeq ->
            bestPrices.sumOf { prices -> prices[diffSeq] ?: 0 }
        }
        return seqs.bananaSum(bestDiff)
    }

    check(123L.nextSecret() == 15887950L)
    check(15887950L.nextSecret() == 16495136L)
    check(16495136L.nextSecret() == 527345L)
    check(527345L.nextSecret() == 704524L)
    check(704524L.nextSecret() == 1553684L)
    check(12683156L.nextSecret() == 11100544L)
    check(11100544L.nextSecret() == 12249484L)
    check(12249484L.nextSecret() == 7753432L)
    check(7753432L.nextSecret() == 5908254L)
    val testInput = """
        1
        10
        100
        2024
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 37327623L)
    val input = readInput("Day22").parse()
    printlnMeasureTimeMillis { println("part1: ${input.part1()}") }
    val testInput2 = """
        1
        2
        3
        2024
    """.trimIndent().split('\n').parse()
    check(testInput2.part2() == 23L)
    printlnMeasureTimeMillis { println("part2: ${input.part2()}") }
}
