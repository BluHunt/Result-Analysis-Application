package com.example.ra.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.example.ra.R
import com.example.ra.Utils.login_button
import com.example.ra.Utils.toast
import com.example.ra.Utils.welcome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.login.*

class Login : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var timer : Thread
    private lateinit var lav : LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        db = FirebaseDatabase.getInstance().reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        lav = findViewById(R.id.login_lottie_view)
        progressBar.visibility = View.INVISIBLE
        login_button.setOnClickListener {
            login_button.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            val email_s = email.text.toString().trim()
            val password_s = password.text.toString().trim()
            if (email_s.isEmpty()) {
                email.error = "Email Required!!"
                email.requestFocus()
                login_button.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
                return@setOnClickListener
            }
            if (!EMAIL_ADDRESS.matcher(email_s).matches()) {
                email.error = "Valid Email Required"
                email.requestFocus()
                login_button.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
                return@setOnClickListener
            }
            if (password_s.isEmpty() || password_s.length < 8) {
                password.error = "8 char Password Required"
                password.requestFocus()
                login_button.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
                return@setOnClickListener
            }
           // mdatabase.child(id).setValue(users)
            uservalidation(email_s, password_s)
        }

        text_view_forget_password.setOnClickListener{
            startActivity(Intent(this@Login,PasswordResetActivity::class.java))
        }

    }
    private fun uservalidation(email: String, password: String) {
        db.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    var uls : Boolean = false
                    for(u in p0.children){
                        if(u.child("email").value.toString() == email && u.child("password").value.toString() == password){
                            uls = true
                            user_login(email,password)
                        }
                    }
                    if(uls == false){
                        toast("Please Check Email & Password")
                        login_button.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }


    private fun user_login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task1 ->
                if(task1.isSuccessful){
                    toast("Login Successfully")
                    login_button()
                    finish()
                }else{
                    toast("Failed!!")
                }
            }
    }

    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.let {
            login_button()
            welcome()
            finish()
        }
    }
}