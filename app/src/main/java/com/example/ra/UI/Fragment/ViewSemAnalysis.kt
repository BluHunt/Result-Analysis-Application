package com.example.ra.UI.Fragment


import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Patterns.WEB_URL
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.ra.R
import kotlinx.android.synthetic.main.fragment_new_analysis.*
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL

/**
 * A simple [Fragment] subclass.
 */
class ViewSemAnalysis : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_sem_analysis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
        }
        checkurl.setOnClickListener{
            var url_s = url.text.toString()
            if (url_s.isEmpty()) {
                url.error = "Empty"
                url.requestFocus()
                return@setOnClickListener
            }
            if (!WEB_URL.matcher(url_s).matches()) {
                url.error = "Invalid URL"
                url.requestFocus()
                return@setOnClickListener
            }
            var fullString: String? = ""
            url_s = url_s.replace("1705390008","1705390010")
            try {
                val url = URL(url_s)
                // read text returned by server
                val `in` = BufferedReader(InputStreamReader(url.openStream()))
                var line: String?
                while (`in`.readLine().also { line = it } != null) {
                    fullString += line
                }
                `in`.close()
            } catch (e: MalformedURLException) {
            } catch (e: IOException) {
            }
            /*url_s.replace("1705390008","1705390010")
            val url = URL(url_s)
                val reader = BufferedReader(InputStreamReader(url.openStream()))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                fullString += line
            }
            reader.close()*/
            fullString =  Jsoup.parse(fullString).text()
            emtytext.text = fullString
        }
    }

}
