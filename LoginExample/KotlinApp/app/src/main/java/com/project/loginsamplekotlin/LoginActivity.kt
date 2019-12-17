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
import android.widget.ProgressBar
import android.widget.Toast
import com.project.loginsamplekotlin.Background.ServerActivity


@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    var email :EditText? =null
    var password :EditText?=null
    lateinit var progress:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       email=findViewById(R.id.usrusr)
       password=findViewById(R.id.pswrdd)
       progress=ProgressDialog(this)



    }

    fun createAccount(view: View) {
        finish()
        startActivity(Intent(this,SignupActivity::class.java))
    }
    fun checkLogin(view: View) {

        val loginString= email?.text.toString()+"@@seprate@@"+password?.text.toString()
        //pass loginstring and web service method name
        ServerAsyncTask().execute(loginString,"Login")

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
                //SOAP REQUEST with data and method name
                serverResponse=ServerActivity().CallService(params[0],params[1])
            }
            catch(e:Exception)
            {
                Log.e("doinbackground",""+e.toString())
            }

            return serverResponse
        }
        // do things with output
        override fun onPostExecute(result: Array<String?>?) {

            if(result?.get(0).toString().equals("true"))
            {
                finish()
                startActivity(Intent(applicationContext,UserAreaActivity::class.java))
            }
            else if(result?.get(0).toString().equals("false"))
            {
                Toast.makeText(applicationContext,"Invalid email/password",Toast.LENGTH_SHORT).show()
            }

            progress.dismiss()

        }
    }
}
