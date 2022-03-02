package com.example.ra.UI.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.ra.R
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_home_fragment.*

class Home : Fragment() {
    private lateinit var ut : MaterialCardView
    private lateinit var se : MaterialCardView
    private lateinit var an : MaterialCardView
    private lateinit var sd : MaterialCardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home_fragment, container, false)

        //unit test slide in left animation
        ut = view.findViewById(R.id.unit_test_cv)
        val inleft = AnimationUtils.loadAnimation(this.context,R.anim.slide_in_left)
        ut.startAnimation(inleft)

        //semester slide in right animation
        se = view.findViewById(R.id.semester_cv)
        val inright = AnimationUtils.loadAnimation(this.context,R.anim.slide_in_right)
        se.startAnimation(inright)

        //analytics slide in bottom animation
        an = view.findViewById(R.id.analytics_cv)
        val inbottom = AnimationUtils.loadAnimation(this.context,R.anim.slide_in_bottom)
        an.startAnimation(inbottom)

        //Student Database slide in bottom animation
        sd = view.findViewById(R.id.student_database_cv)
        sd.startAnimation(inbottom)

        ut.setOnClickListener{
            val action = HomeDirections.ToUT()
            Navigation.findNavController(it).navigate(action)
        }

        sd.setOnClickListener {
            val action = HomeDirections.STDB()
            Navigation.findNavController(it).navigate(action)
        }

        se.setOnClickListener {
            val action = HomeDirections.ToSemester()
            Navigation.findNavController(it).navigate(action)
        }


        return view
    }

}
