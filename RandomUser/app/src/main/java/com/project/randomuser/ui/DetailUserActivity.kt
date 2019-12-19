package com.project.randomuser.ui

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.project.randomuser.R
import com.project.randomuser.networking.UserDetail
import com.squareup.picasso.Picasso


class DetailUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        //get user data pass from last activity
        val i = intent
        val user: UserDetail = i.getSerializableExtra("userDetail") as UserDetail


        //initialise view
        val image=findViewById<ImageView>(R.id.imgProfile)
        val name=findViewById<TextView>(R.id.txtName)
        val registered=findViewById<TextView>(R.id.txtRegister)
        val phone=findViewById<TextView>(R.id.txtPhone)
        val id=findViewById<TextView>(R.id.txtID)
        val email=findViewById<TextView>(R.id.txtEmail)
        val uname=findViewById<TextView>(R.id.txtUsername)
        val dob=findViewById<TextView>(R.id.txtDateOfBirth)
        val address=findViewById<TextView>(R.id.txtAddress)


        //assign value to view
        Picasso.get()
            .load(Uri.parse(user.picture.large))
            .placeholder(R.drawable.progressbar)
            .fit()
            .centerInside()
            .into(image)
        name.text=resources.getString(R.string.full_name, user.name.title +" "+ user.name.first+" "+ user.name.last)
        registered.text=user.registered.date;
        phone.text=user.cell
        id.text=user.id.value.toString()
        email.text=user.email
        uname.text=user.id.name
        dob.text=user.dob.date
        address.text=resources.getString(R.string.universal, user.location.street.number.toString()+" ,"+user.location.street.name+" ,"+user.location.city+" ,"+user.location.state)





    }
}
