package ro.tuiasi.pp.lab10.pipeline

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Procesor pipeline pentru liste de întregi, implementat cu canale Kotlin.
 *
 * Pipeline-ul are 3 etape conectate prin canale:
 * 1. **Înmulțire**: fiecare element × alpha
 * 2. **Sortare**: colectarea tuturor elementelor și sortarea lor
 * 3. **Colectare**: returnarea listei finale
 *
 * Etapele rulează concurent în corutine separate, communicate prin `Channel<Int>`.
 */
class PipelineProcessor {

    /**
     * Procesează lista [input] prin pipeline-ul de 3 etape.
     *
     * @param input Lista de întregi de procesat
     * @param alpha Factorul de înmulțire pentru etapa 1
     * @return Lista rezultat după înmulțire și sortare
     */
    fun process(input: List<Int>, alpha: Int): List<Int> = runBlocking {
        val canalInmultire = Channel<Int>(Channel.BUFFERED)
        val canalSortat = Channel<Int>(Channel.BUFFERED)

        launch {
            for (element in input) {
                canalInmultire.send(element * alpha)
            }
            canalInmultire.close()
        }


        launch {
            val listaTemp = mutableListOf<Int>()
            for (element in canalInmultire) {
                listaTemp.add(element)
            }
            listaTemp.sort()
            for (element in listaTemp) {
                canalSortat.send(element)
            }
            canalSortat.close()
        }

        val rezultat = mutableListOf<Int>()
        for (element in canalSortat) {
            rezultat.add(element)
        }

        rezultat
    }
}
