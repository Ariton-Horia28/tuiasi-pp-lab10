package ro.tuiasi.pp.lab10.chain

import kotlinx.coroutines.delay

/**
 * Handler de la baza lanțului — HappyWorker.
 *
 * Procesează cererea finală și generează răspunsul cu formatul `"Response - <mesaj>"`.
 * Nu delegă mai departe (este ultimul în lanț).
 */
class HappyWorkerHandler : Handler {

    override var next: Handler? = null
    override var previous: Handler? = null

    /**
     * Procesează cererea finală și generează răspunsul.
     *
     * Format răspuns: `"Response - <message>"`
     *
     * @param message Mesajul primit de la Manager
     * @return Răspunsul generat de worker
     */
    override suspend fun handleRequest(message: String): String {
        delay(200)
        println("[HappyWorker] Procesez: $message")

        val raspuns = "Response - $message"

        println("[HappyWorker] Răspund: $raspuns")
        return raspuns
    }
}
