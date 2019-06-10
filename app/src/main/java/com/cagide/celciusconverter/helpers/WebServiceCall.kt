package com.cagide.celciusconverter.helpers

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.cagide.celciusconverter.views.MainActivity
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class WebServiceCall(var context: Context) {

    private val NAMESPACE = "http://www.w3schools.com/xml/"
    private val METHOD_NAME = "CelsiusToFahrenheit"
    private val SOAP_ACTION = "http://www.w3schools.com/xml/CelsiusToFahrenheit"
    private val URL = "http://www.w3schools.com/xml/tempconvert.asmx"
    private var result: Float = 0.0f

    fun getFahrenheit(celsius: String): Float {
        //TODO WebService call
        val celsiusNum: Float = celsius.toFloat()

        result = (celsiusNum * 9/5) + 32

        var db = DatabaseHelper(context)
        db.insertData(celsiusNum, result)

        val registry = db.allRecords
        registry.forEach {
            Log.i("data", it.date + " " + it.celsius.toString() + " " + it.fahrenheit.toString())
        }

        return result

        /*try{
            val request = SoapObject(NAMESPACE, METHOD_NAME)
            request.addProperty("Celsius", celsius)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER12)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val httpTransportSE = HttpTransportSE(URL)
            httpTransportSE.call(SOAP_ACTION, envelope)

            result = envelope.response.toString().toFloat()

            return result
        } catch (e: Exception){
            Log.e("error", e.message)
        }*/

    }
}