private typealias Input25 = Pair<List<List<Int>>, List<List<Int>>>

fun main() {
    fun List<String>.parse(): Input25 {
        val locks = mutableListOf<List<Int>>()
        val keys = mutableListOf<List<Int>>()
        joinToString("\n").split("\n\n").map {
            val t = it.split('\n').map { it.toList() }.transpose()
            if (t.first().first() == '#') {
                // lock
                locks.add(
                    t.map {
                        it.takeWhile { it == '#' }.count() - 1
                    },
                )
            } else {
                // key
                keys.add(
                    t.map {
                        it.asReversed().takeWhile { it == '#' }.count() - 1
                    },
                )
            }
        }
        return Pair(locks, keys)
    }

    fun Input25.part1(): Int {
        val (locks, keys) = this
        return locks.flatMap { lock ->
            keys.map { key ->
                if (lock.indices.all { lock[it] + key[it] <= 5 }) {
                    1
                } else {
                    0
                }
            }
        }.sum()
    }

    fun Input25.part2(): Int {
        TODO()
    }

    val testInput = """
        #####
        .####
        .####
        .####
        .#.#.
        .#...
        .....

        #####
        ##.##
        .#.##
        ...##
        ...#.
        ...#.
        .....

        .....
        #....
        #....
        #...#
        #.#.#
        #.###
        #####

        .....
        .....
        #.#..
        ###..
        ###.#
        ###.#
        #####

        .....
        .....
        .....
        #....
        #.#..
        #.#.#
        #####
    """.trimIndent().split('\n').parse()
    val input = readInput("Day25").parse()
    printlnMeasureTimeMillis { println("part1: ${input.part1()}") }
}
