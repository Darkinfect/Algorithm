import java.io.File
import kotlin.random.Random

fun main() {
    // Размеры тестов (растут экспоненциально и линейно до 2.5 млн)
    val sizes = listOf(
        6, 10, 20, 50, 100, 500, 1000, 2500, 4000, 6500, 10000,
        20000, 30000, 50000, 100000, 200000, 250000, 400000, 600000,
        800000, 1000000, 1250000, 1500000, 1750000, 2000000, 2200000,
        2400000, 2450000, 2490000, 2500000
    )

    sizes.forEachIndexed { idx, n ->
        // Выбираем диапазон частот в зависимости от размера теста
        val (lo, hi) = when {
            n < 10000 -> Pair(1, 1_000_000)
            n < 250000 -> Pair(1, 10_000)
            else -> Pair(1, 1000)  // Для больших тестов - меньше значения
        }

        // Генерируем отсортированный список частот
        val frequencies = (1..n)
            .map { Random.nextLong(lo.toLong(), (hi + 1).toLong()) }
            .sorted()

        // Записываем в файл
        val fileName = "huffman_test_${idx + 1}.in"
        File(fileName).writeText(buildString {
            append(n)
            append("\n")
            append(frequencies.joinToString(" "))
            append("\n")
        })

        println("Создан файл: $fileName (n=$n)")
    }

    println("\n✓ Все 30 тестовых файлов успешно сгенерированы!")
}
