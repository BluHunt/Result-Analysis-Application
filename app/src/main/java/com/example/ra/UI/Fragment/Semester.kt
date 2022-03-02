package com.example.ra.UI.Fragment


import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Patterns.WEB_URL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ra.R
import kotlinx.android.synthetic.main.fragment_semester.*
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL


/**
 * A simple [Fragment] subclass.
 */
class Semester : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_semester, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        new_analysis.setOnClickListener{
            val action = SemesterDirections.ToNewAnalysis()
            Navigation.findNavController(it).navigate(action)
        }
    }

}
