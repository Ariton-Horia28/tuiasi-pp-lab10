package ro.tuiasi.pp.lab10.factorial

import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Modul de calcul factorial paralel folosind corutine și canale.
 *
 * Arhitectura:
 * - Un `Channel<Int>` produs cu toate valorile din input
 * - 4 corutine consumatoare care calculează factorial în paralel
 * - Rezultatele sunt colectate într-un `Map<Int, Long>`
 */

/**
 * Calculează factorial pentru fiecare valoare din [values] folosind 4 corutine paralele.
 *
 * Fiecare valoare este preluată dintr-un canal de lucru de una dintre cele 4 corutine,
 * calculul este efectuat și rezultatul este stocat în map-ul final.
 *
 * @param values Lista de valori pentru care se calculează factorial
 * @return Map cu perechile n → n!
 */
suspend fun computeFactorials(values: List<Int>): Map<Int, Long> = coroutineScope {
    val canal = Channel<Int>(capacity = values.size)

    launch {
        for (v in values) {
            canal.send(v)
        }
        canal.close()
    }

    val rezultate = java.util.concurrent.ConcurrentHashMap<Int, Long>()

    val joburi = (1..4).map {
        async {
            for (n in canal) {
                rezultate[n] = factorial(n)
            }
        }
    }

    joburi.forEach { it.await() }

    rezultate
}

/**
 * Calculează n! (factorial) pentru un număr natural n.
 *
 * @param n Numărul pentru care se calculează factorial (n >= 0)
 * @return Valoarea n! ca Long
 */
fun factorial(n: Int): Long {
    if (n < 0) return 0
    if (n == 0) return 1

    var result = 1L
    for (i in 1..n) {
        result *= i
    }
    return result
}
