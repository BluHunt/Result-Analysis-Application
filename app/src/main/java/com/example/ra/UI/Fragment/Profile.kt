package com.example.ra.UI.Fragment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ra.Utils.toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.ra.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile_fragment.*
import java.io.ByteArrayOutputStream

class Profile : Fragment() {
    private val DefaultURL = "https://picsum.photos/200"
    private lateinit var imageuri : Uri
    private val REQUEST_IMAGE_CAPTURE = 1000
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val mdata = FirebaseDatabase.getInstance().getReference("Users")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUser?.let { user->
            Glide.with(this)
                .load(user.photoUrl)
                .into(profile_user_image)
            if(profile_user_role.text.isEmpty()){
                mdata.child(currentUser.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        profile_user_role.text = p0.child("role").value.toString()
                    }
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
            }
            if(user.displayName.isNullOrBlank()){
                profile_user_name.setText("Add User Name!!")
            }else{
                profile_user_name.setText(user.displayName)
            }

            if(user.isEmailVerified){
                profile_email_verification.visibility = View.INVISIBLE
            }else{
                profile_email_verification.visibility = View.VISIBLE
            }
            profile_user_email.text = user.email

            profile_user_phno.text = if(currentUser.phoneNumber.isNullOrBlank()) "Add Phone Number" else currentUser.phoneNumber.toString()

        }

        profile_user_phno.setOnClickListener{
            val action = ProfileDirections.actionVerifyPhone()
            Navigation.findNavController(it).navigate(action)
        }

        profile_user_forget_password.setOnClickListener{
            val action = ProfileDirections.actionUpdatePassword()
            Navigation.findNavController(it).navigate(action)
        }

        profile_user_email.setOnClickListener{
            val action = ProfileDirections.actionUpdateEmail()
            Navigation.findNavController(it).navigate(action)
        }

        profile_user_image.setOnClickListener{
            takePictureIntent()
        }

        profile_savechanges.setOnClickListener{
            mdata.child(currentUser?.uid.toString()).child("username").setValue(profile_user_name.text.toString().trim())
            mdata.child(currentUser?.uid.toString()).child("phoneno").setValue(currentUser?.phoneNumber.toString())
            val photo = when{
                ::imageuri.isInitialized -> imageuri
                currentUser?.photoUrl == null -> Uri.parse(DefaultURL)
                else -> currentUser.photoUrl
            }
            val name = profile_user_name.text.toString().trim()

            if(name.isEmpty()){
                profile_user_name.error = "Name Required"
                profile_user_name.requestFocus()
                return@setOnClickListener
            }

            val updates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(photo)
                .build()
            profile_savechanges.visibility = View.INVISIBLE
            profile_progressbar.visibility = View.VISIBLE
            mdata.child(currentUser!!.uid).child("username").setValue(name)
            currentUser.updateProfile(updates)
                .addOnCompleteListener{
                    profile_savechanges.visibility = View.VISIBLE
                    profile_progressbar.visibility = View.INVISIBLE
                    if(it.isSuccessful){
                        context?.toast("Profile Updated")
                    }else{
                        context?.toast(it.exception?.message!!)
                    }
                }
        }

        profile_email_verification.setOnClickListener{
            profile_progressbar.visibility = View.VISIBLE
            currentUser?.sendEmailVerification()
                ?.addOnCompleteListener{
                    if(it.isSuccessful){
                        profile_progressbar.visibility = View.INVISIBLE
                        context?.toast("Verification Email Sent")
                    }else{
                        context?.toast(it.exception?.message!!)
                    }
                }
        }
    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{pictureIntent ->
            pictureIntent.resolveActivity(activity?.packageManager!!)?.also {
                startActivityForResult(pictureIntent,REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
          if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            uploadImageAndSaveUri(imageBitmap)
        }
    }

    private fun uploadImageAndSaveUri(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance()
            .reference
            .child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val image = baos.toByteArray()
        val upload = storageRef.putBytes(image)
        profile_user_image_pb.visibility = View.VISIBLE
        upload.addOnCompleteListener{uploadTask ->
            profile_user_image_pb.visibility = View.INVISIBLE
            if(uploadTask.isSuccessful){
                storageRef.downloadUrl.addOnCompleteListener{urlTask->
                    urlTask.result?.let {
                        imageuri = it
                           activity?.toast(imageuri.toString())
                        profile_user_image.setImageBitmap(bitmap)
                        mdata.child("${ FirebaseAuth.getInstance().currentUser?.uid}").child("profileuri").setValue(imageuri.toString())
                    }
                }

            }else{
                uploadTask.exception?.let {
                    activity?.toast(it.message!!)
                }
            }
        }

    }


}
