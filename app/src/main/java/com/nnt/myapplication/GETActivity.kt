package com.nnt.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nnt.myapplication.databinding.ActivityGetactivityBinding
import com.nnt.myapplication.databinding.DialogGetBinding
import com.nnt.myapplication.databinding.DialogInfoGetBinding
import java.text.DecimalFormat

class GETActivity: AppCompatActivity() {
    private lateinit var binding: ActivityGetactivityBinding
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.botaoVoltar.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        binding.botaoInfo.setOnClickListener { infoGET() }


        var idade = 0
        var altura = 0
        var peso = 0.0

        binding.seekBarIdade.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //Faz alguma coisa enquanto a barra se move
                ("$progress anos").also { binding.textViewIdade.text = it }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //Faz alguma coisa quando a barra começa a mover
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //Faz alguma coisa quando a barra para de se mover
                if (seekBar != null) {
                    idade = seekBar.progress
                }
            }
        })

        binding.seekBarAltura.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                altura = progress
                (altura.toString().replace(".", ",") + "cm").also { binding.textViewAltura.text = it }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    altura = seekBar.progress
                }
            }
        })

        binding.seekBarPeso.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                peso = progress / 100.00
                "${peso}kg".also { binding.textViewPeso.text = it }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    peso = seekBar.progress / 100.00
                }
            }
        })

        binding.botaoCalcularTmb.setOnClickListener { calcularGET(idade, altura, peso) }
        binding.botaoInfo.setOnClickListener { infoGET() }
    }

    private fun calcularGET(idade: Int, altura: Int, peso: Double) {
        var tmb = 0.0
        var get = 0.0

        when {
            binding.chipMasculino.isChecked -> {
                //Para homens: GET = 66,47 + (13,75 x peso em kg) + (5,0 x altura em cm) – (6,76 x idade em anos)
                tmb = 66.47 + (13.75 * peso) + (5.0 * altura) - (6.76 * idade)

                when {
                    binding.chipLeve.isChecked -> {
                        get = tmb * 1.55
                    }
                    binding.chipModerada.isChecked -> {
                        get = tmb * 1.78
                    }
                    binding.chipIntensa.isChecked -> {
                        get = tmb * 12.1
                    }
                }
                dialogGET(get)
            }

            binding.chipFeminino.isChecked -> {
                //Para mulheres: GET = 655,1 + (9,56 x peso em kg) + (1,85 x altura em cm) – (4,68 x idade em anos)
                tmb = 655.1 + (9.56 * peso) + (1.85 * altura) - (4.68 * idade)
                when {
                    binding.chipLeve.isChecked -> {
                        get = tmb * 1.56
                    }
                    binding.chipModerada.isChecked -> {
                        get = tmb * 1.64
                    }
                    binding.chipIntensa.isChecked -> {
                        get = tmb * 1.82
                    }
                }
                dialogGET(get)

            }
        }
    }

    private fun dialogGET(tmb: Double) {
        val builder = AlertDialog.Builder(this, R.style.Theme_Dialog)
        val dialogBinding = DialogGetBinding.inflate(LayoutInflater.from(this))
        builder.setView(dialogBinding.root)

        (DecimalFormat("#.##").format(tmb).replace(".",",") + " calorias").also { dialogBinding.textViewGET.text = it }
        dialogBinding.buttonTMB.setOnClickListener { alertDialog.dismiss() }

        alertDialog = builder.create()
        alertDialog.show()
    }

    private fun infoGET() {
        val builder = AlertDialog.Builder(this, R.style.Theme_Dialog)
        val dialogBinding = DialogInfoGetBinding.inflate(LayoutInflater.from(this))
        builder.setView(dialogBinding.root)

        dialogBinding.button.setOnClickListener { alertDialog.dismiss() }

        alertDialog = builder.create()
        alertDialog.show()
    }
}