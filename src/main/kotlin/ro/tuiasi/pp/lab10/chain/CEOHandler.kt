package ro.tuiasi.pp.lab10.chain

import kotlinx.coroutines.delay

/**
 * Handler-ul de nivel cel mai înalt din lanț — CEO.
 *
 * Inițiază cererea pe lanțul descendent cu formatul `"Request - <mesaj>"`
 * și primește răspunsul final de la baza lanțului.
 *
 * Simulează procesarea asincronă cu `delay()`.
 */
class CEOHandler : Handler {

    override var next: Handler? = null
    override var previous: Handler? = null

    /**
     * Transformă mesajul în cerere formală și o trimite mai departe în lanț.
     *
     * Format mesaj trimis: `"Request - <message>"`
     *
     * @param message Mesajul inițial
     * @return Răspunsul final primit de la baza lanțului
     */
    override suspend fun handleRequest(message: String): String {
        delay(100)
        val cerere="Request - $message"
        println("CEO trimit :$cerere")
        val raspuns=next?.handleRequest(cerere) ?: return cerere
        println("[CEO] Am primit răspunsul: $raspuns")
        return raspuns
    }
}
