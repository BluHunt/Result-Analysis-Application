package com.example.ra.Utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.ra.UI.Fragment.UserListDirections
import com.example.ra.UI.Home
import com.example.ra.UI.Login



fun Context.toast(message: String){
    //var s = Snackbar.make(message,this,Snackbar.LENGTH_SHORT)
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}

fun Context.welcome(){
    toast("Welcome")
}

fun Context.ANU(v:View){
    val action = UserListDirections.NewUserAdd()
    Navigation.findNavController(v).navigate(action)
}

fun Context.login_button(){
    val intent = Intent(this, Home::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}

fun Context.logout(){
    val intent = Intent(this, Login::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}
