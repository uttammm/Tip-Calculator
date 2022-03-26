package com.example.tippy

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val InitialTipPer = 15
class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount : EditText
    private lateinit var seekBar: SeekBar
    private lateinit var tvTipPer: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDesc: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBar = findViewById(R.id.seekBar)
        tvTipPer = findViewById(R.id.tvTipPer)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipDesc = findViewById(R.id.tvTipDesc)

        seekBar.progress = InitialTipPer
        tvTipPer.text = "$InitialTipPer%"
        updateTipDescription(InitialTipPer)
        seekBar.setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvTipPer.text = "$progress%"
                computeTipAndTotal()
                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        etBaseAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                computeTipAndTotal()
            }

        })
    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription = when(tipPercent){
            in 0..5 -> "Come on now \uD83D\uDE14"
            in 6..10 -> "well..thanks I guess \uD83D\uDE36"
            in 11..18 -> "Appreciable \uD83D\uDE0A"
            in 19..25 -> "Realllllyyyyy ????? \uD83D\uDE2E"
            else -> "OMGGGG!!!! \uD83E\uDD11"
        }
        tvTipDesc.text = tipDescription

        var color = ArgbEvaluator().evaluate(
            (tipPercent.toFloat()/seekBar.max),
            ContextCompat.getColor(this, R.color.color_worst_tip),
            ContextCompat.getColor(this, R.color.color_best_tip)
        ) as Int
        tvTipDesc.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if(etBaseAmount.text.isEmpty()){
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return
        }
        val base = etBaseAmount.text.toString().toDouble()
        val tip = seekBar.progress.toString().toDouble()
        val tipAmount = base * tip/100
        val totalAmount = base + tipAmount
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text ="%.2f".format(totalAmount)
    }

}