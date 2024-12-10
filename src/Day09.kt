private typealias Input9 = List<String>

fun main() {
    fun List<String>.parse(): Input9 {
        return this
    }

    fun Input9.part1(): Long {
        val line = first()
        val ls = mutableListOf<Short>()
        line.forEachIndexed { index, ch ->
            if (index % 2 == 0) {
                repeat(ch.digitToInt()) {
                    ls.add((index / 2).toShort())
                }
            } else {
                repeat(ch.digitToInt()) {
                    ls.add(-1)
                }
            }
        }
        val fs = ls.toShortArray()

        var i = 0
        var k = fs.size - 1
        outer@ while (i < k) {
            if (fs[i] == (-1).toShort()) {
                while (fs[k] == (-1).toShort()) {
                    if (k <= i) {
                        break@outer
                    }
                    k--
                }
                fs[i] = fs[k]
                fs[k] = -1
            }
            i++
        }
        //fs.joinToString("") { if (it == -1) "." else it.toString() }.println()
        var checksum = 0L
        i = 0
        while (fs[i] != (-1).toShort()) {
            checksum += i * fs[i]
            i++
        }
        return checksum
    }

    fun Input9.part2(): Long {
        val line = first()
        val ls = mutableListOf<Short>()
        line.forEachIndexed { index, ch ->
            if (index % 2 == 0) {
                repeat(ch.digitToInt()) {
                    ls.add((index / 2).toShort())
                }
            } else {
                repeat(ch.digitToInt()) {
                    ls.add(-1)
                }
            }
        }
        val fs = ls.toShortArray()

        fun findFree(size: Int, k: Int): Int? {
            var i = 0
            var count = 0
            var start = 0
            while (i < k) {
                if (fs[i] == (-1).toShort()) {
                    count++
                    if (count == size) {
                        return start
                    }
                    i++
                } else {
                    i++
                    start = i
                    count = 0
                }
            }
            return null
        }

        var kEnd = fs.indexOfLast { i -> i != (-1).toShort() }
        var fileId = fs[kEnd]
        outer@ while (true) {
            var kStart = kEnd
            while (kStart > 0 && fs[kStart - 1] == fileId) {
                kStart--
            }
            val width = kEnd - kStart + 1
            val h = findFree(width, kStart)
            if (h != null) {
                for (q in 0..<width) {
                    fs[h + q] = fs[kStart + q]
                    fs[kStart + q] = -1
                }
            } else {
                kEnd = kStart - 1
            }
            while (true) {
                if (kEnd < 0) break@outer
                if (fs[kEnd] != (-1).toShort() && fs[kEnd] < fileId) {
                    fileId = fs[kEnd]
                    break
                }
                kEnd--
            }
            // fs.joinToString("") { if (it == -1) "." else it.toString() }.println()
        }
        // fs.joinToString("") { if (it == (-1).toShort()) "." else it.toString() }.println()
        var checksum = 0L
        var i = 0
        while (i < fs.size) {
            if (fs[i] != (-1).toShort()) {
                checksum += i * fs[i]
            }
            i++
        }
        return checksum
    }

    val testInput = """
        2333133121414131402
    """.trimIndent().split('\n').parse()
    check(testInput.part1() == 1928L)
    val input = readInput("Day09").parse()
    printlnMeasureTimeMillis { input.part1().println() }
    check(testInput.part2() == 2858L)
    printlnMeasureTimeMillis { input.part2().println() }
}
