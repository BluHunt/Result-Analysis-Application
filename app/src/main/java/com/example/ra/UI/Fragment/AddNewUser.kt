package com.example.ra.UI.Fragment



import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.ra.R

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_add_new_user.*
import com.example.ra.Utils.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*


/**
 * A simple [Fragment] subclass.
 */
class AddNewUser : Fragment() {
    private val DefaultURL = "https://picsum.photos/200"
    private lateinit var db: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var cu : FirebaseUser
    private lateinit var users : Users
    private lateinit var cue : String
    private lateinit var cup : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        db = FirebaseDatabase.getInstance().reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        cu = mAuth.currentUser!!
        return inflater.inflate(R.layout.fragment_add_new_user, container, false)
    }

    override fun onStart() {
        super.onStart()
        new_user_add_btn.setOnClickListener(View.OnClickListener {
            new_user_add_btn.visibility = View.INVISIBLE
            pb.visibility = View.VISIBLE
            val u_s = nuname.text.toString()
            val e_s = nuemail.text.toString().toLowerCase().toString()
            val p_s = nupassword.text.toString().trim()
            val r_s = nurole.selectedItem.toString()
            if(u_s.isEmpty()){
                nuname.error = "Username Required"
                nuname.requestFocus()
                new_user_add_btn.visibility = View.VISIBLE
                pb.visibility = View.INVISIBLE
                return@OnClickListener
            }
            if (e_s.isEmpty()) {
                nuemail.error = "Email Required!!"
                nuemail.requestFocus()
                new_user_add_btn.visibility = View.VISIBLE
                pb.visibility = View.INVISIBLE
                return@OnClickListener
            }
            if (!EMAIL_ADDRESS.matcher(e_s).matches()) {
                nuemail.error = "Valid Email Required"
                nuemail.requestFocus()
                new_user_add_btn.visibility = View.VISIBLE
                pb.visibility = View.INVISIBLE
                return@OnClickListener
            }
            if (p_s.isEmpty() || p_s.length < 8) {
                nupassword.error = "8 char Password Required"
                nupassword.requestFocus()
                new_user_add_btn.visibility = View.VISIBLE
                pb.visibility = View.INVISIBLE
                return@OnClickListener
            }
            var priority = if(r_s == "DEO"){ 2 }else{if(r_s == "HOD"){ 3 }else{ 4 }}
            db.child("${FirebaseAuth.getInstance().currentUser?.uid}").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    cue = p0.child("email").value.toString()
                    cup = p0.child("password").value.toString()
                }
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
            mAuth.createUserWithEmailAndPassword(e_s,p_s)
                .addOnCompleteListener {task->
                    if(task.isSuccessful){
                        val updates = UserProfileChangeRequest.Builder()
                            .setDisplayName(u_s)
                            .build()
                        mAuth.currentUser?.updateProfile(updates)
                        users = Users(
                            mAuth.currentUser?.uid.toString(),
                            u_s,
                            e_s,
                            p_s,
                            r_s,
                            "empty",
                            priority.toString()
                        )
                        db.child(mAuth.currentUser?.uid.toString()).setValue(users)
                            .addOnCompleteListener{
                                context?.toast("User Added Successfully")
                                NUAS()
                                mAuth.signOut()
                                mAuth.signInWithEmailAndPassword(cue,cup)

                            }
                    }else{
                        context?.toast("Error!!!")
                    }
                }
        })
    }

    private fun NUAS() {
        val action = AddNewUserDirections.NewUserAdded()
        Navigation.findNavController(this.requireView()).navigate(action)
    }

}
