package com.example.ra.UI.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.ra.R
import kotlinx.android.synthetic.main.fragment_student_database.*
class StudentDatabase : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_database, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fy2018.setOnClickListener{
            val action = StudentDatabaseDirections.ViewList()
            action.yearInN = "2018"
            action.yearInW = "FY COMPUTER"
            Navigation.findNavController(it).navigate(action)
        }
        sy2018.setOnClickListener{
            val action = StudentDatabaseDirections.ViewList()
            action.yearInN = "2018"
            action.yearInW = "SY COMPUTER"
            Navigation.findNavController(it).navigate(action)
        }
        ty2018.setOnClickListener {
            val action = StudentDatabaseDirections.ViewList()
            action.yearInN = "2018"
            action.yearInW = "TY COMPUTER"
            Navigation.findNavController(it).navigate(action)
        }
        ty2019.setOnClickListener {
            val action = StudentDatabaseDirections.ViewList()
            action.yearInN = "2019"
            action.yearInW = "TY COMPUTER"
            Navigation.findNavController(it).navigate(action)
        }
    }
}
