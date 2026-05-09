package ro.tuiasi.pp.lab10.chain

import kotlinx.coroutines.delay

/**
 * Handler de nivel executiv — intermediar între CEO și Manager.
 *
 * Primește cererea de la CEO, o procesează asincron și o transmite
 * mai departe spre Manager. Răspunsul primit este transmis înapoi.
 */
class ExecutiveHandler : Handler {

    override var next: Handler? = null
    override var previous: Handler? = null

    /**
     * Procesează cererea și o pasează mai departe în lanț.
     *
     * @param message Mesajul primit de la CEO
     * @return Răspunsul primit de la handler-ul următor
     */
    override suspend fun handleRequest(message: String): String {
        delay(100)
        println("Executive primit: $message")

        val requestForNext = "Request - $message"

        val raspuns = next?.handleRequest(requestForNext) ?: message

        println("Executive $raspuns")
        return raspuns
    }

}
