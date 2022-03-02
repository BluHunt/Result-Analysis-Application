package com.example.ra.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.ra.R
import com.example.ra.Utils.logout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.home.*

class Home : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var currentuser : FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        setSupportActionBar(toolbar)
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser!!

        val navController = Navigation.findNavController(this,
            R.id.fragment
        )
        NavigationUI.setupWithNavController(nav_view,navController)
        NavigationUI.setupActionBarWithNavController(this,navController,drawer_layout)
        updateNavHeader()
    }

    private fun updateNavHeader() {
        var naviationview : NavigationView = findViewById(R.id.nav_view)
        var headerview : View = naviationview.getHeaderView(0)
        var hu : TextView = headerview.findViewById(R.id.header_username)
        var he : TextView = headerview.findViewById(R.id.header_useremail)


        hu.setText(currentuser.displayName)
        he.setText(currentuser.email)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.fragment),drawer_layout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_logout){
            AlertDialog.Builder(this).apply {
                setTitle("Are You Sure?")
                setPositiveButton("Yes"){ _,_->
                    FirebaseAuth.getInstance().signOut()
                    logout()
                }
                setNegativeButton("No"){_,_->

                }
            }.create().show()
        }
        return super.onOptionsItemSelected(item)
    }


}
