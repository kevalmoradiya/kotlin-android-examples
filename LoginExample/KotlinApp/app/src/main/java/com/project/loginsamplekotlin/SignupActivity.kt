package com.project.loginsamplekotlin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.project.loginsamplekotlin.Background.ServerActivity
import com.project.loginsamplekotlin.SignupActivity.ServerAsyncTask

@Suppress("DEPRECATION")
class SignupActivity : AppCompatActivity() {

    var email :EditText? =null
    var password :EditText?=null
    lateinit var progress: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        email=findViewById(R.id.mail)
        password=findViewById(R.id.password)
        progress=ProgressDialog(this)

    }

    fun createAccount(view: View) {
        val signupString= email?.text.toString()+"@@seprate@@"+password?.text.toString()
        //pass data and webservice method name
        ServerAsyncTask().execute(signupString,"Signup")
    }


    @SuppressLint("StaticFieldLeak")
    inner class ServerAsyncTask:
        AsyncTask<String, Void, Array<String?>?>() {

        var serverResponse : Array<String?>? = null

        override fun onPreExecute() {
            super.onPreExecute()
            progress.setMessage("Please wait..")
            progress.show()

        }

        override fun doInBackground(vararg params: String): Array<String?>? {

            try{
                //SOAP REQUEST with data and webservice method name
                serverResponse= ServerActivity().CallService(params[0],params[1])
            }
            catch(e:Exception)
            {
                Log.e("doinbackground",""+e.toString())
            }

            return serverResponse
        }
        override fun onPostExecute(result: Array<String?>?) {

            if(result?.get(0).toString().equals("true"))
            {
                finish()
                startActivity(Intent(applicationContext,LoginActivity::class.java))
            }
            else if(result?.get(0).toString().equals("false"))
            {
                Toast.makeText(applicationContext,"Email exist", Toast.LENGTH_SHORT).show()
            }

            progress.dismiss()

        }
    }
}
