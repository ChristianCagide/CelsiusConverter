package com.cagide.celciusconverter.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.cagide.celciusconverter.R
import com.cagide.celciusconverter.helpers.WebServiceCall
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var result: Float = 0.0f

    val handler: Handler = Handler(Handler.Callback {
        txvResult.text = result.toString()

        return@Callback false
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnConvert.setOnClickListener {
            if (edtDegrees.text.toString() != "")
                getFahrenheit(edtDegrees.text.toString())
            else
                Toast.makeText(this, "Introduce los grados centigrados a convertir", Toast.LENGTH_LONG).show()
        }
    }

    private fun getFahrenheit(celsius: String) {
        Thread(Runnable {
            val ex = WebServiceCall(this)
            result = ex.getFahrenheit(celsius)
            handler.sendEmptyMessage(0)
        }).start()

    }
}