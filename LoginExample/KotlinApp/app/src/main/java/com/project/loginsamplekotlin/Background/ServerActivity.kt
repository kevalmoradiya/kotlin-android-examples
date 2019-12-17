package com.project.loginsamplekotlin.Background

import android.util.Log
import com.project.loginsamplekotlin.Constants
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE


class ServerActivity{


    lateinit var resTxt:Array<String?>

    //SOAP REQUEST
    fun CallService(
        fromMobileLogin: String?,
        webMethName: String
    ): Array<String?>? { // Create request
        val request = SoapObject(Constants().NAMESPACE, webMethName)
        // Property which holds input parameters
        val send = PropertyInfo()
        // Set Name
        send.setName("passstring")
        // Set Value
        send.setValue(fromMobileLogin)
        // Set dataType
        send.setType(String::class.java)
        // Add the property to request object
        request.addProperty(send)
        // Create envelope
        val envelope = SoapSerializationEnvelope(
            SoapEnvelope.VER11
        )
        envelope.dotNet = true
        // Set output SOAP object
        envelope.setOutputSoapObject(request)
        // Create HTTP call object
        val androidHttpTransport = HttpTransportSE(Constants().URL)



         try {
             // Call web service
            androidHttpTransport.call(Constants().SOAP_ACTION + webMethName, envelope)
            // Get the response

            val result: SoapObject = envelope.getResponse() as SoapObject
            resTxt =arrayOfNulls(result.getPropertyCount())
            for (i in 0 until result.getPropertyCount()) {
                resTxt[i] = result.getProperty(i).toString()
            }

        } catch (e: Exception) {
            Log.d("SoapRequest", e.toString())

        }

        return resTxt
    }



}