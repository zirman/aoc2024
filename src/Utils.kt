import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun printlnMeasureTimeMillis(block: () -> Unit) {
    measureTimeMillis(block)
        .also { println("time: $it") }
}

data class Pos(val row: Int, val col: Int)

fun Pos.goWest(): Pos = copy(col = col - 1)
fun Pos.goEast(): Pos = copy(col = col + 1)
fun Pos.goNorth(): Pos = copy(row = row - 1)
fun Pos.goSouth(): Pos = copy(row = row + 1)

fun Pos.adjacencies(): List<Pos> = listOf(
    goWest(),
    goEast(),
    goNorth(),
    goSouth(),
)

operator fun Pos.minus(pos: Pos): Pos = Pos(
    row - pos.row,
    col - pos.col,
)

operator fun Pos.plus(pos: Pos): Pos = Pos(
    row + pos.row,
    col + pos.col,
)

fun Pos.manhattanDistance(pos: Pos): Int = (row - pos.row).absoluteValue + (col - pos.col).absoluteValue
data class Size(val width: Int, val height: Int)

fun <T> List<List<T>>.toSize2(): Size = Size(width = this[0].size, height = size)
fun List<String>.toSize(): Size = Size(width = this[0].length, height = size)

operator fun Size.contains(pos: Pos): Boolean =
    pos.row >= 0 && pos.row < height &&
            pos.col >= 0 && pos.col < width

fun <T> List<T>.dropAt(index: Int): List<T> = filterIndexed { i, t -> index != i }

fun <T> List<T>.permutations(prefix: List<T> = emptyList()): List<List<T>> {
    if (isEmpty()) {
        return listOf(prefix)
    }
    return flatMapIndexed { index, t ->
        buildList(size - 1) {
            this@permutations.forEachIndexed { index2, t ->
                if (index != index2) {
                    add(t)
                }
            }
        }.permutations(
            buildList(prefix.size + 1) {
                addAll(prefix)
                add(t)
            },
        )
    }
}

fun <T> List<T>.combinations(size: Int): List<List<T>> {
    fun MutableList<List<T>>.recur(i: Int, c: List<T>) {
        if (c.size == size) {
            this@recur.add(c)
            return
        }

        (i..<this@combinations.size).forEach { t ->
            recur(t + 1, c + this@combinations[t])
        }
    }
    return buildList {
        recur(0, emptyList())
    }
}

fun Long.countDigits(base: Int = 10): Int {
    var n = this
    var i = 0
    while (true) {
        n /= base
        i++
        if (n == 0L) {
            return i
        }
    }
}

fun Long.evenDivOrZero(denominator: Long): Long = if (this % denominator != 0L) 0 else this / denominator
fun Int.evenDivOrZero(denominator: Int): Int = if (this % denominator != 0) 0 else this / denominator

fun <T> List<List<T>>.transpose(): List<List<T>> = buildList {
    for (x in this@transpose.first().indices) {
        add(
            buildList {
                for (y in this@transpose.indices) {
                    add(this@transpose[y][x])
                }
            }
        )
    }
}

//fun <T> List<List<T>>.window(width: Int, height: Int): List<List<T>> {
//    for (i in indices) {}
//    windowed(height).map { line ->
//        line
//        line.windowed(width)
//    }
//}
