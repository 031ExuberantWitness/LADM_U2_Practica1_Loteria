package mx.tecnm.tepic.ladm_u2_practica1_loteria


import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import mx.tecnm.tepic.ladm_u2_practica1_loteria.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var estadoCartas = true

    var VoiceOver: MediaPlayer? = null
    var VoiceMute: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarPantalla()

        var hiloSorteador = Hilo(this)

        binding.btnIniciar.setOnClickListener {
            hiloSorteador.start()
            ocultarPantallaInicial()
        }

        binding.btnDetener.setOnClickListener {
            if (hiloSorteador.estaEnPausa()) {
                Toast.makeText(this, "REANUDAR", Toast.LENGTH_LONG).show()
                binding.btnDetener.setBackgroundResource(R.drawable.btn_pausar)
                hiloSorteador.reanudarHilo()
            } else {
                Toast.makeText(this, "PAUSADO", Toast.LENGTH_LONG).show()
                binding.btnDetener.setBackgroundResource(R.drawable.btn_reanudar)
                hiloSorteador.pausarHilo()
            }
        }

        binding.btnReiniciar.setOnClickListener {
            hiloSorteador.terminarHilo()
            restartActivity(this)
        }

        binding.btnTirarCartas.setOnClickListener {
            ocultarCartasRestantes(estadoCartas)
        }

        binding.btnSig.setOnClickListener {
            if (hiloSorteador.arrayCartas.size == 0) {
            } else {
                hiloSorteador.mostrarFaltantesImagenes()
            }
        }
    }

    fun ocultarCartasRestantes(estadoCartas: Boolean) {
        if (estadoCartas){
            binding.lblCartasRestantes.isVisible = estadoCartas
            binding.cartasFaltantes.isVisible = estadoCartas

            binding.cartaFaltante1.isVisible = estadoCartas
            binding.cartaFaltante2.isVisible = estadoCartas
            binding.cartaFaltante3.isVisible = estadoCartas
            binding.cartaFaltante4.isVisible = estadoCartas
            binding.cartaFaltante5.isVisible = estadoCartas

            binding.btnSig.isVisible = estadoCartas

            binding.btnTirarCartas.setBackgroundResource(R.drawable.btn_mostrar)
            this.estadoCartas = false
        }else{
            binding.lblCartasRestantes.isVisible = estadoCartas
            binding.cartasFaltantes.isVisible = estadoCartas

            binding.cartaFaltante1.isVisible = estadoCartas
            binding.cartaFaltante2.isVisible = estadoCartas
            binding.cartaFaltante3.isVisible = estadoCartas
            binding.cartaFaltante4.isVisible = estadoCartas
            binding.cartaFaltante5.isVisible = estadoCartas

            binding.btnSig.isVisible = estadoCartas

            binding.btnTirarCartas.setBackgroundResource(R.drawable.btn_ocultar)
            this.estadoCartas = true
        }
    }

    fun ocultarPantallaInicial() {
        binding.btnIniciar.isVisible = false
        binding.ivFiller.isVisible = false

        binding.setCartas.isVisible = true
        binding.setControles.isVisible = true
        binding.setControlesTitulos.isVisible = true
        binding.btnTirarCartas.isVisible = true
    }

    fun configurarPantalla(){
        binding.setCartas.isVisible = false
        binding.setControles.isVisible = false
        binding.setControlesTitulos.isVisible = false
        binding.btnTirarCartas.isVisible = false

        binding.lblCartasRestantes.isVisible = false
        binding.cartasFaltantes.isVisible = false

        binding.cartaFaltante1.isVisible = false
        binding.cartaFaltante2.isVisible = false
        binding.cartaFaltante3.isVisible = false
        binding.cartaFaltante4.isVisible = false
        binding.cartaFaltante5.isVisible = false

        binding.btnSig.isVisible = false
    }

    fun restartActivity(actividad: Activity) {
        VoiceOver?.pause()
        VoiceMute?.pause()
        val intent = Intent()
        intent.setClass(actividad, actividad.javaClass)
        actividad.startActivity(intent)
        actividad.finish()
    }
}
