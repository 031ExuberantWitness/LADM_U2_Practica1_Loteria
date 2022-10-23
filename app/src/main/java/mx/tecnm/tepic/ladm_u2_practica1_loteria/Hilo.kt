package mx.tecnm.tepic.ladm_u2_practica1_loteria

import android.media.MediaPlayer
import android.widget.Toast
import kotlin.random.Random

class Hilo(actividad: MainActivity) : Thread() {
    var actividad = actividad
    var turnoActual = 0
    var cartasRestantes = 0

    private var pausa = false
    private var reanudar = true

    var arrayCartasOut = mutableListOf(R.drawable.carta1)

    var arrayCartas = mutableListOf(
        "El Gallo", "El Diablito", "La Dama", "El catrin", "El Paraguas",
        "La sirena", "La Escalera", "La Botella", "El Barril", "El arbol",
        "El melon", "El valiente", "El gorrito", "La muerte", "La pera",
        "La bandera", "El bandolon", "El violoncello", "La garza", "El pajaro",
        "La mano", "La Bota", "La luna", "El cotorro", "El borracho",
        "El negrito", "El corazon", "La sandia", "El tambor", "El camaron", // Chrstopher
        "Las jaraz", "El musico", "La araña", "El Soldado", "La estrella",
        "El Cazo", "El mundo", "El apache", "El nopal", "El Alacran",
        "La Rosa", "La Calavera", "La Campana", "El Cantarito", "El Venado",
        "El sol", "La corona", "La chalupa", "El pino", "El pescado",
        "La palma", "La maceta", "El Arpa", "La rana"
    )

    var arrayCartasImagen = mutableListOf( R.drawable.carta1, R.drawable.carta2, R.drawable.carta3, R.drawable.carta4, R.drawable.carta5,
        R.drawable.carta6, R.drawable.carta7, R.drawable.carta8, R.drawable.carta9, R.drawable.carta10,
        R.drawable.carta11, R.drawable.carta12, R.drawable.carta13,  R.drawable.carta14, R.drawable.carta15,
        R.drawable.carta16, R.drawable.carta17, R.drawable.carta18, R.drawable.carta19, R.drawable.carta20,
        R.drawable.carta21, R.drawable.carta22, R.drawable.carta23, R.drawable.carta24, R.drawable.carta25,
        R.drawable.carta26, R.drawable.carta27, R.drawable.carta28, R.drawable.carta29, R.drawable.carta30,
        R.drawable.carta31, R.drawable.carta32, R.drawable.carta33, R.drawable.carta34, R.drawable.carta35,
        R.drawable.carta36, R.drawable.carta37,  R.drawable.carta38, R.drawable.carta39, R.drawable.carta40,
        R.drawable.carta41, R.drawable.carta42, R.drawable.carta43, R.drawable.carta44, R.drawable.carta45,
        R.drawable.carta46, R.drawable.carta47, R.drawable.carta48, R.drawable.carta49, R.drawable.carta50,
        R.drawable.carta51, R.drawable.carta52, R.drawable.carta53, R.drawable.carta54
    )

    var arrayVoiceOver = mutableListOf(R.raw.carta1, R.raw.carta2, R.raw.carta3, R.raw.carta4, R.raw.carta5,
        R.raw.carta6, R.raw.carta7, R.raw.carta8, R.raw.carta9, R.raw.carta10,
        R.raw.carta11, R.raw.carta12, R.raw.carta13, R.raw.carta14, R.raw.carta15,
        R.raw.carta16, R.raw.carta17, R.raw.carta18, R.raw.carta19, R.raw.carta20,
        R.raw.carta21, R.raw.carta22, R.raw.carta23, R.raw.carta24,  R.raw.carta25,
        R.raw.carta26, R.raw.carta27, R.raw.carta28, R.raw.carta29, R.raw.carta30,
        R.raw.carta31, R.raw.carta32, R.raw.carta33, R.raw.carta34,  R.raw.carta35,
        R.raw.carta36, R.raw.carta37, R.raw.carta38, R.raw.carta39, R.raw.carta40,
        R.raw.carta41, R.raw.carta42, R.raw.carta43, R.raw.carta44, R.raw.carta45,
        R.raw.carta46, R.raw.carta47, R.raw.carta48, R.raw.carta49, R.raw.carta50,
        R.raw.carta51, R.raw.carta52, R.raw.carta53, R.raw.carta54
    )

    override fun run() {
        super.run()
        var arregloImagenesCopia = arrayCartasImagen
        while (turnoActual < 52 && reanudar) {
            turnoActual++
            while (pausa) {
                sleep(500)
            }
            actividad.runOnUiThread {
                var randomizer = Random.nextInt(arrayCartas.size)

                actividad.VoiceOver = MediaPlayer.create(actividad, arrayVoiceOver[randomizer])
                actividad.VoiceOver?.start()

                actividad.binding.cartaActual.setImageResource(arregloImagenesCopia[randomizer])
                actividad.binding.lblTitulo.text = arrayCartas[randomizer]
                arrayCartasOut.add(arrayCartasImagen[randomizer])

                if (arrayCartasImagen.size == 54) {
                    actividad.binding.cartaRetirada1.setImageResource(arrayCartasOut[turnoActual])
                } else
                    if (arrayCartasImagen.size == 53) {
                        actividad.binding.cartaRetirada1.setImageResource(arrayCartasOut[turnoActual])
                        actividad.binding.cartaRetirada2.setImageResource(arrayCartasOut[turnoActual - 1])
                    } else {
                        actividad.binding.cartaRetirada1.setImageResource(arrayCartasOut[turnoActual])
                        actividad.binding.cartaRetirada2.setImageResource(arrayCartasOut[turnoActual - 1])
                        actividad.binding.cartaRetirada3.setImageResource(arrayCartasOut[turnoActual - 2])
                    }

                arrayCartas.removeAt(randomizer)
                arregloImagenesCopia.removeAt(randomizer)
                actividad.runOnUiThread{ arrayVoiceOver.removeAt(randomizer) }
            }

            sleep(3000)
            var aux = Random.nextInt(9) + 2
            var decrementoTiempo = 100L
            while (aux != 0) {
                actividad.runOnUiThread {
                    var aux2 = Random.nextInt(arrayCartasImagen.size - 1)
                    actividad.binding.lblTitulo.text = arrayCartas[aux2]
                    actividad.binding.cartaActual.setImageResource(arrayCartasImagen[aux2])
                    aux--
                }

                decrementoTiempo += 100
                sleep(decrementoTiempo)
            }

            actividad.VoiceOver?.release()
            sleep(200L)

            if (arrayCartas.size == 0)
            else {
                actividad.runOnUiThread {
                    actividad.binding.cartasFaltantes.text = ""
                    mostrarFaltantesTexto()
                    actividad.binding.btnSig.isEnabled = true
                }
            }
        }
        actividad.runOnUiThread { Toast.makeText(actividad, "JUEGO TERMINADO", Toast.LENGTH_SHORT).show() }
    }

    fun mostrarFaltantesTexto() {
        actividad.runOnUiThread {
            var aux = arrayCartasImagen.size
            while (aux > 0) {
                actividad.binding.cartasFaltantes.text =
                    actividad.binding.cartasFaltantes.text.toString() + "\n" + arrayCartas[aux - 1]
                aux--
            }
            cartasRestantes = arrayCartasImagen.size - 1
            mostrarFaltantesImagenes()
        }
    }

    fun mostrarFaltantesImagenes() {
        if (cartasRestantes == 0)
            cartasRestantes = arrayCartasImagen.size - 1

        actividad.binding.cartaFaltante1.setImageResource(arrayCartasImagen[cartasRestantes])
        cartasRestantes--

        if (cartasRestantes == 0)
            cartasRestantes = arrayCartasImagen.size - 1

        actividad.binding.cartaFaltante2.setImageResource(arrayCartasImagen[cartasRestantes])
        cartasRestantes--

        if (cartasRestantes == 0)
            cartasRestantes = arrayCartasImagen.size - 1

        actividad.binding.cartaFaltante3.setImageResource(arrayCartasImagen[cartasRestantes])
        cartasRestantes--

        if (cartasRestantes == 0)
            cartasRestantes = arrayCartasImagen.size - 1

        actividad.binding.cartaFaltante4.setImageResource(arrayCartasImagen[cartasRestantes])
        cartasRestantes--

        if (cartasRestantes == 0)
            cartasRestantes = arrayCartasImagen.size - 1

        actividad.binding.cartaFaltante5.setImageResource(arrayCartasImagen[cartasRestantes])
        cartasRestantes--
    }

    fun terminarHilo() {
        reanudar = false
    }

    fun pausarHilo() {
        pausa = true
    }

    fun reanudarHilo() {
        pausa = false
    }

    fun estaEnPausa(): Boolean {
        return pausa
    }
}
