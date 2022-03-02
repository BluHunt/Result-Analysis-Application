package com.example.ra.UI.Fragment

import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.util.Patterns.WEB_URL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ra.R
import com.example.ra.Utils.toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_new_analysis.*
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class NewAnalysis : Fragment() {
    private val sdc : DatabaseReference = FirebaseDatabase.getInstance().reference.child("STUDENT")
    private val y : Int = Calendar.getInstance().get(Calendar.YEAR)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_analysis, container, false)
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

        new_analysis_pb.visibility = View.INVISIBLE
        checkurl.visibility = View.VISIBLE

        checkurl.setOnClickListener{
            new_analysis_pb.visibility = View.VISIBLE
            checkurl.visibility = View.INVISIBLE
            var url_s = url.text.toString()
            if (url_s.isEmpty()) {
                url.error = "Empty"
                url.requestFocus()
                new_analysis_pb.visibility = View.INVISIBLE
                checkurl.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if (!WEB_URL.matcher(url_s).matches()) {
                url.error = "Invalid URL"
                url.requestFocus()
                new_analysis_pb.visibility = View.INVISIBLE
                checkurl.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if(!url_s.contains("1234")) {
                url.error = "Please Check The URL"
                url.requestFocus()
                //context?.toast("Please Check The URL")
                new_analysis_pb.visibility = View.INVISIBLE
                checkurl.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val selectedID = selectyear.checkedRadioButtonId

            if(selectedID == fy.id){
                check_student_database("FY COMPUTER",url_s)
            }else if(selectedID == sy.id){
                check_student_database("SY COMPUTER",url_s)
            }else if (selectedID == ty.id){
                check_student_database("TY COMPUTER",url_s)
            }else{
                selectyear.requestFocus()
                context?.toast("Please Select Year")
                new_analysis_pb.visibility = View.INVISIBLE
                checkurl.visibility = View.VISIBLE
                return@setOnClickListener
            }
            new_analysis_pb.visibility = View.INVISIBLE
            checkurl.visibility = View.VISIBLE
        }
    }

    private fun check_student_database(selectedYear: String, urlS: String) {
        sdc.child((y-1).toString()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.hasChild(selectedYear)){
                    var ate = AsyncTheProcess(this@NewAnalysis)
                    ate.execute(urlS)
                    new_analysis_pb.visibility = View.INVISIBLE
                    checkurl.visibility = View.VISIBLE
                }else{
                    context?.toast("Student Database Not Found")
                    new_analysis_pb.visibility = View.INVISIBLE
                    checkurl.visibility = View.VISIBLE
                }
            }
        })
    }

    private class AsyncTheProcess internal constructor(context: NewAnalysis) : AsyncTask<String, String, String>() {
        private val smi : DatabaseReference = FirebaseDatabase.getInstance().reference.child("MARKS DB")
        private val ssr : DatabaseReference = FirebaseDatabase.getInstance().reference.child("SUBJECT")
        private val sdc : DatabaseReference = FirebaseDatabase.getInstance().reference.child("STUDENT")
        private val sdr : DatabaseReference = FirebaseDatabase.getInstance().reference.child("STUDENT")
        private val y : Int = Calendar.getInstance().get(Calendar.YEAR)
        private val ref = WeakReference<NewAnalysis>(context)
        private var t_s : String = ""
        private var esm : String = ""
        private var esmt : String = ""
        private var eim : String = ""
        private var cr : String = ""
        private var psm : String = ""
        private var psmt : String = ""
        private var pim : String = ""
        private var tsc : Int = 0
        private var tfsc : Int = 0
        private var per_1 : Double = 00.00
        private var per_2 : Double = 00.00
        private var per_3 : Double = 00.00
        private var per_4 : Double = 00.00
        private var per_5 : Double = 00.00
        private var per_6 : Double = 00.00
        private var first_en : String = ""
        private var second_en : String = ""
        private var third_en : String = ""
        private var first_per : Double = 00.00
        private var second_per : Double = 00.00
        private var third_per : Double = 00.00

        override fun doInBackground(vararg params: String?): String {
            var url_s = params[0].toString()
            Log.d("IN BACKGROUND", "In Back")
            sdc.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.hasChild((y-1).toString())){
                        sdr.child((y-1).toString()).child("TY COMPUTER").addListenerForSingleValueEvent(object :ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {
                            }
                            override fun onDataChange(p0: DataSnapshot) {
                                for(s in p0.children){
                                    tsc++
                                    Log.d("IN BACKGROUND FIREBASE", "In Dataread")
                                    val ss = s.child("Enrollment No").value.toString()
                                    if(ss.substring(0,2) == "15"){
                                        url_s = url_s.replace(url_s.substring(url_s.indexOf("EnrollmentNumber")+17,url_s.indexOf("EnrollmentNumber")+19),"15")
                                        data_feed(url_s,ss)
                                    }

                                    if(ss.substring(0,2) == "18"){
                                        url_s = url_s.replace(url_s.substring(url_s.indexOf("EnrollmentNumber")+17,url_s.indexOf("EnrollmentNumber")+19),"18")
                                        data_feed(url_s,ss)
                                    }

                                    if(ss.substring(0,2) == "17"){
                                        url_s = url_s.replace(url_s.substring(url_s.indexOf("EnrollmentNumber")+17,url_s.indexOf("EnrollmentNumber")+19),"17")
                                        data_feed(url_s,ss)
                                        Log.d("IN 17", "In 17")
                                    }

                                    if(ss.substring(0,2) == "16"){
                                        url_s = url_s.replace(url_s.substring(url_s.indexOf("EnrollmentNumber")+17,url_s.indexOf("EnrollmentNumber")+19),"16")
                                        data_feed(url_s,ss)
                                        //emtytext.text = url_s
                                    }

                                    if(ss.substring(0,2) == "19"){
                                        url_s = url_s.replace(url_s.substring(url_s.indexOf("EnrollmentNumber")+17,url_s.indexOf("EnrollmentNumber")+19),"19")
                                        data_feed(url_s,ss)
                                    }

                                    if(ss.substring(0,2) == "20"){
                                        url_s = url_s.replace(url_s.substring(url_s.indexOf("EnrollmentNumber")+17,url_s.indexOf("EnrollmentNumber")+19),"20")
                                        data_feed(url_s,ss)
                                    }
                                }
                                smi.child(t_s).child("TY COMPUTER").child("FIRST").child("ENROLLMENT").setValue(first_en)
                                smi.child(t_s).child("TY COMPUTER").child("FIRST").child("PERCENTAGE").setValue(first_per)
                                smi.child(t_s).child("TY COMPUTER").child("SECOND").child("ENROLLMENT").setValue(second_en)
                                smi.child(t_s).child("TY COMPUTER").child("SECOND").child("PERCENTAGE").setValue(second_per)
                                smi.child(t_s).child("TY COMPUTER").child("THIRD").child("ENROLLMENT").setValue(third_en)
                                smi.child(t_s).child("TY COMPUTER").child("THIRD").child("PERCENTAGE").setValue(third_per)
                            }
                        })
                    }
                }

            })

            return "Done"
        }

        private fun check_mark(s: String): String {
            val m : String = when {
                s.contains("#") -> { //for checking the marks
                    s.replace("#","")
                }
                s.contains("*") -> {
                    s.replace("*","")
                }
                s.contains("@") -> {
                    s.replace("@","")
                }
                else -> {
                    s
                }
            }
            return m
        }

        private fun data_feed(s:String,en:String){
            var fullString = ""
            var url_s : String?
            url_s = s.replace("1234",en)
            //ref.get()?.context?.toast(url_s)
            try {
                val url = URL(url_s)
                val `in` = BufferedReader(InputStreamReader(url.openStream()))
                var line: String?
                while (`in`.readLine().also { line = it } != null) {
                    fullString += line
                }
                `in`.close()
            } catch (e: MalformedURLException) {
                ref.get()?.context?.toast(e.message.toString())
            } catch (e: IOException) {
                ref.get()?.context?.toast(e.message.toString())
            }
            fullString =  Jsoup.parse(fullString).text()
            fullString = fullString.replace("Maharashtra State Board of Technical Education Statement of Marks","")
            fullString = fullString.replace("INSTRUCTIONS","")
            fullString = fullString.replace("TOTAL MAX. MARKS RESULT WITH % TOTAL MARKS OBTAINED TOTAL CREDIT","")
            fullString = fullString.replace("This Marksheet is Downloaded from Internet SECRETARY MAHARASHTRA STATE BOARD OF TECHNICAL EDUCATION","")
            fullString = fullString.replace("1.Report Discrepancy in this certificate to Head of the institution.","")
            fullString = fullString.replace("2.This certificate of marks is issued as per prevaling rules and regulations of MSBTE at the time of this exam.","")
            fullString = fullString.replace("3.Eligibility for III semester is based on total number of failure subjects in I & II semesters taken together.","")
            fullString = fullString.replace("4.Candidate is eligible for admission to V/VII Semester only if he/she is fully passed in I & II /III & IV semesters & availed benefit of A.T.K.T/PASS at III & IV /V & VI semesters taken together respectively.","")
            fullString = fullString.replace("5.Class awarded for Diploma is based on aggregate marks obtained in pre-final & final semester.","")
            fullString = fullString.replace("ABBREVATION DETAILS","")
            fullString = fullString.replace("TH Theory TW Team Work AB Absent % Percentage of Marks TM Theory Test Marks PJ Project Work EX Exemption WFLY Result Withheld Due to Pending Lower Year PR Practical SW Sessional OTP Optional PM Practical Test Marks IT Industrial Training LSP Lower Semester Pending CON Condoned AG Aggregate @ Condoned Marks PLY Pending Lower Year FT Failure But Allowed To Keep Term AP Additional Practical * Failure Marks WFLS Result Withheld Due To Pending Lower Semester A.T.K.T Allowed to Keep Term OR Oral C# Carry Forward Marks DIST Distinction ESE End Semester Exam PA Progressive Assessment Ref:Formerly known as The board of Technical Examinations Maharashtra State of Technical Education Act 1997(Mah XXXVIII of 1997) and Maharashtra Government Gazette Notification Section IV-B issued on march 31,1999(-172/16/200/20-04:07:2007 12:00:28)","")
            fullString = fullString.replace("Url:-http://www.msbte.org.in E & OE","")
            if(fullString.contains("SUMMER")){
                t_s = fullString.substring(fullString.indexOf("SUMMER"),(fullString.indexOf("SUMMER")+11)).trim()
                smi.child(fullString.substring(fullString.indexOf("SUMMER"),fullString.indexOf("SUMMER")+11)).setValue(fullString.substring(fullString.indexOf("SUMMER"),fullString.indexOf("SUMMER")+11))
            }
            if(fullString.contains("WINTER")){
                t_s = fullString.substring(fullString.indexOf("WINTER"),(fullString.indexOf("WINTER")+11)).trim()
                smi.child(fullString.substring(fullString.indexOf("WINTER"),fullString.indexOf("WINTER")+11)).setValue(fullString.substring(fullString.indexOf("WINTER"),fullString.indexOf("WINTER")+11))
            }

            smi.child(t_s).child("TY COMPUTER").child(en.trim()).child("LINK").setValue(url_s)

            if(fullString.contains("FIRST SEMESTER")){
                val seat_no = fullString.substring(fullString.indexOf("FIRST SEMESTER")-7,fullString.indexOf("FIRST SEMESTER")-1).trim()

                val eng_str = "ENG "+ fullString.substring(fullString.indexOf("ENGLISH")+8,fullString.indexOf("BASIC SCIENCE")-1).trim()
                val bsc_str = "BSC "+ fullString.substring(fullString.indexOf("BASIC SCIENCE")+14,fullString.indexOf("BASIC MATHEMATICS")-1).trim()
                val bms_str = "BMS "+ fullString.substring(fullString.indexOf("BASIC MATHEMATICS")+18,fullString.indexOf("FUNDAMENTALS OF ICT")-1).trim()
                val ict_str = "ICT "+ fullString.substring(fullString.indexOf("FUNDAMENTALS OF ICT")+20,fullString.indexOf("ENGINEERING GRAPHICS")-1).trim()
                val ege_str = "EGE "+ fullString.substring(fullString.indexOf("ENGINEERING GRAPHICS")+21,fullString.indexOf("WORKSHOP PRACTICE")-1).trim()
                val wpc_str = "WPC "+ fullString.substring(fullString.indexOf("WORKSHOP PRACTICE")+18,fullString.indexOf("WORKSHOP PRACTICE")+61).trim()

                val eng_arr = eng_str.split(" ")
                val bsc_arr = bsc_str.split(" ")
                val bms_arr = bms_str.split(" ")
                val ict_arr = ict_str.split(" ")
                val ege_arr = ege_str.split(" ")
                val wpc_arr = wpc_str.split(" ")

                var sub_list = ArrayList<List<String>>()

                sub_list.add(eng_arr)
                sub_list.add(bsc_arr)
                sub_list.add(bms_arr)
                sub_list.add(ict_arr)
                sub_list.add(ege_arr)
                sub_list.add(wpc_arr)

                check_update("FIRST SEMESTER",en,seat_no,sub_list)

                smi.child(t_s).child("TY COMPUTER").child(en).child("FIRST SEMESTER").child("TOTAL CREDIT").setValue(fullString.substring(fullString.indexOf("CO1I")-10,fullString.indexOf("CO1I")-8))
                smi.child(t_s).child("TY COMPUTER").child(en).child("FIRST SEMESTER").child("TOTAL MARKS").setValue(fullString.substring(fullString.indexOf("CO1I")-14,fullString.indexOf("CO1I")-11))
                smi.child(t_s).child("TY COMPUTER").child(en).child("FIRST SEMESTER").child("OUT OF").setValue("700")

                if(fullString.substring(fullString.indexOf("CO1I")-20,fullString.indexOf("CO1I")-15).toDouble() > 00.00 && fullString.substring(fullString.indexOf("CO1I")-20,fullString.indexOf("CO1I")-15).toDouble() < 100.00){
                    per_1 = fullString.substring(fullString.indexOf("CO1I")-20,fullString.indexOf("CO1I")-15).toDouble()
                    smi.child(t_s).child("TY COMPUTER").child(en).child("FIRST SEMESTER").child("PERCENTAGE").setValue(per_1.toString())
                    if (fullString.contains("WINTER")){
                        if (per_1 > first_per){
                            third_per = second_per
                            second_per = first_per
                            first_per = per_1
                            third_en = second_en
                            second_en = first_en
                            first_en = en
                        }
                    }
                }else{
                    smi.child(t_s).child("TY COMPUTER").child(en).child("FIRST SEMESTER").child("PERCENTAGE").setValue("-")
                }
            }

            if(fullString.contains("SECOND SEMESTER")){
                val seat_no = fullString.substring(fullString.indexOf("SECOND SEMESTER")-7,fullString.indexOf("SECOND SEMESTER")-1).trim()

                val eec_str = "EEC "+ fullString.substring(fullString.indexOf("ELEMENTS OF ELECTRICAL ENGINEERING")+35,fullString.indexOf("APPLIED MATHEMATICS")-1).trim()
                val ami_str = "AMI "+ fullString.substring(fullString.indexOf("APPLIED MATHEMATICS")+20,fullString.indexOf("BASIC ELECTRONICS")-1).trim()
                val bec_str = "BEC "+ fullString.substring(fullString.indexOf("BASIC ELECTRONICS")+18,fullString.indexOf("Programming in 'C'")-1).trim()
                val pci_str = "PCI "+ fullString.substring(fullString.indexOf("Programming in 'C'")+19,fullString.indexOf("BUSINESS COMMUNICATION USING COMPUTERS")-1).trim()
                val bcc_str = "BCC "+ fullString.substring(fullString.indexOf("BUSINESS COMMUNICATION USING COMPUTERS")+39,fullString.indexOf("COMPUTER PERIPHERAL AND HARDWARE MAINTENANCE")-1).trim()
                val cph_str = "CPH "+ fullString.substring(fullString.indexOf("COMPUTER PERIPHERAL AND HARDWARE MAINTENANCE")+45,fullString.indexOf("WEB PAGE DESIGNING WITH HTML")-1).trim()
                val wpd_str = "WPD "+ fullString.substring(fullString.indexOf("WEB PAGE DESIGNING WITH HTML")+29,fullString.indexOf("WEB PAGE DESIGNING WITH HTML")+72).trim()

                val eec_arr = eec_str.split(" ")
                val ami_arr = ami_str.split(" ")
                val bec_arr = bec_str.split(" ")
                val pci_arr = pci_str.split(" ")
                val bcc_arr = bcc_str.split(" ")
                val cph_arr = cph_str.split(" ")
                val wpd_arr = wpd_str.split(" ")

                var sub_list = ArrayList<List<String>>()

                sub_list.add(eec_arr)
                sub_list.add(ami_arr)
                sub_list.add(bec_arr)
                sub_list.add(pci_arr)
                sub_list.add(bcc_arr)
                sub_list.add(cph_arr)
                sub_list.add(wpd_arr)

                check_update("SECOND SEMESTER",en,seat_no,sub_list)

                smi.child(t_s).child("TY COMPUTER").child(en).child("SECOND SEMESTER").child("TOTAL CREDIT").setValue(fullString.substring(fullString.indexOf("CO2I")-10,fullString.indexOf("CO2I")-8))

                smi.child(t_s).child("TY COMPUTER").child(en).child("SECOND SEMESTER").child("TOTAL MARKS").setValue(fullString.substring(fullString.indexOf("CO2I")-14,fullString.indexOf("CO2I")-11))

                smi.child(t_s).child("TY COMPUTER").child(en).child("SECOND SEMESTER").child("OUT OF").setValue("700")

                if(fullString.substring(fullString.indexOf("CO2I")-20,fullString.indexOf("CO2I")-15).toDouble() > 00.00 && fullString.substring(fullString.indexOf("CO2I")-20,fullString.indexOf("CO2I")-15).toDouble() < 100.00){
                    per_2 = fullString.substring(fullString.indexOf("CO2I")-20,fullString.indexOf("CO2I")-15).toDouble()
                    smi.child(t_s).child("TY COMPUTER").child(en).child("SECOND SEMESTER").child("PERCENTAGE").setValue(per_2.toString())
                    if (fullString.contains("SUMMER")){
                        if (per_2 > first_per){
                            third_per = second_per
                            second_per = first_per
                            first_per = per_2
                            third_en = second_en
                            second_en = first_en
                            first_en = en
                        }
                    }
                }else{
                    smi.child(t_s).child("TY COMPUTER").child(en).child("SECOND SEMESTER").child("PERCENTAGE").setValue("-")
                }

            }

            if(fullString.contains("THIRD SEMESTER")){
                val seat_no = fullString.substring(fullString.indexOf("THIRD SEMESTER")-7,fullString.indexOf("THIRD SEMESTER")-1).trim()

                val oop_str = "OOP "+ fullString.substring(fullString.indexOf("OBJECT ORIENTED PROGRAMMING USING C++")+38,fullString.indexOf("DATA STRUCTURE USING ‘C’")-1).trim()
                val dsu_str = "DSU "+ fullString.substring(fullString.indexOf("DATA STRUCTURE USING ‘C’")+25,fullString.indexOf("COMPUTER GRAPHICS")-1).trim()
                val cgr_str = "CGR "+ fullString.substring(fullString.indexOf("COMPUTER GRAPHICS")+18,fullString.indexOf("DATABASE MANAGEMENT SYSTEM")-1).trim()
                val dms_str = "DMS "+ fullString.substring(fullString.indexOf("DATABASE MANAGEMENT SYSTEM")+27,fullString.indexOf("DIGITAL TECHNIQUES")-1).trim()
                val dte_str = "DTE "+ fullString.substring(fullString.indexOf("DIGITAL TECHNIQUES")+19,fullString.indexOf("DIGITAL TECHNIQUES")+105).trim()

                val oop_arr = oop_str.split(" ")
                val dsu_arr = dsu_str.split(" ")
                val cgr_arr = cgr_str.split(" ")
                val dms_arr = dms_str.split(" ")
                val dte_arr = dte_str.split(" ")

                var sub_list = ArrayList<List<String>>()

                sub_list.add(oop_arr)
                sub_list.add(dsu_arr)
                sub_list.add(cgr_arr)
                sub_list.add(dms_arr)
                sub_list.add(dte_arr)

                check_update("THIRD SEMESTER",en,seat_no,sub_list)

                smi.child(t_s).child("TY COMPUTER").child(en).child("THIRD SEMESTER").child("TOTAL CREDIT").setValue(fullString.substring(fullString.indexOf("CO3I")-10,fullString.indexOf("CO3I")-8))

                smi.child(t_s).child("TY COMPUTER").child(en).child("THIRD SEMESTER").child("TOTAL MARKS").setValue(fullString.substring(fullString.indexOf("CO3I")-14,fullString.indexOf("CO3I")-11))

                smi.child(t_s).child("TY COMPUTER").child(en).child("THIRD SEMESTER").child("OUT OF").setValue("750")

                if(fullString.substring(fullString.indexOf("CO3I")-20,fullString.indexOf("CO3I")-15).toDouble() > 00.00 && fullString.substring(fullString.indexOf("CO3I")-20,fullString.indexOf("CO3I")-15).toDouble() < 100.00){
                    per_3 = fullString.substring(fullString.indexOf("CO3I")-20,fullString.indexOf("CO3I")-15).toDouble()
                    smi.child(t_s).child("TY COMPUTER").child(en).child("THIRD SEMESTER").child("PERCENTAGE").setValue(per_3.toString())
                    if (fullString.contains("WINTER")){
                        if (per_3 > first_per){
                            third_per = second_per
                            second_per = first_per
                            first_per = per_3
                            third_en = second_en
                            second_en = first_en
                            first_en = en
                        }
                    }
                }else{
                    smi.child(t_s).child("TY COMPUTER").child(en).child("THIRD SEMESTER").child("PERCENTAGE").setValue("-")
                }

            }

            if(fullString.contains("FOURTH SEMESTER")){
                val seat_no = fullString.substring(fullString.indexOf("FOURTH SEMESTER")-7,fullString.indexOf("FOURTH SEMESTER")-1).trim()

                val jpr_str = "JPR "+ fullString.substring(fullString.indexOf("JAVA PROGRAMMING")+17,fullString.indexOf("SOFTWARE ENGINEERING")-1).trim()
                val sen_str = "SEN "+ fullString.substring(fullString.indexOf("SOFTWARE ENGINEERING")+21,fullString.indexOf("DATA COMMUNICATION")-1).trim()
                val dcc_str = "DCC "+ fullString.substring(fullString.indexOf("DATA COMMUNICATION")+40,fullString.indexOf("MICRO")-1).trim()
                val mic_str = "MIC "+ fullString.substring(fullString.indexOf("MICROPROCESSORS")+16,fullString.indexOf("GUI")-1).trim()
                val gad_str = "GAD "+ fullString.substring(fullString.indexOf("GUI")+41,fullString.indexOf("GUI")+84).trim()

                val jpr_arr = jpr_str.split(" ")
                val sen_arr = sen_str.split(" ")
                val dcn_arr = dcc_str.split(" ")
                val mic_arr = mic_str.split(" ")
                val gad_arr = gad_str.split(" ")

                var sub_list = ArrayList<List<String>>()

                sub_list.add(jpr_arr)
                sub_list.add(sen_arr)
                sub_list.add(dcn_arr)
                sub_list.add(mic_arr)
                sub_list.add(gad_arr)

                check_update("FOURTH SEMESTER",en,seat_no,sub_list)

                smi.child(t_s).child("TY COMPUTER").child(en).child("EXTRA DATA").child("FOURTH SEMESTER").child("TOTAL CREDIT").setValue(fullString.substring(fullString.indexOf("CO4I")-10,fullString.indexOf("CO4I")-8))
                smi.child(t_s).child("TY COMPUTER").child(en).child("EXTRA DATA").child("FOURTH SEMESTER").child("TOTAL MARKS").setValue(fullString.substring(fullString.indexOf("CO4I")-14,fullString.indexOf("CO4I")-11))
                smi.child(t_s).child("TY COMPUTER").child(en).child("EXTRA DATA").child("FOURTH SEMESTER").child("OUT OF").setValue("750")

                if(fullString.substring(fullString.indexOf("CO4I")-20,fullString.indexOf("CO4I")-15).toDouble() > 00.00 && fullString.substring(fullString.indexOf("CO4I")-20,fullString.indexOf("CO4I")-15).toDouble() < 100.00){
                    per_4 = fullString.substring(fullString.indexOf("CO4I")-20,fullString.indexOf("CO4I")-15).toDouble()
                    smi.child(t_s).child("TY COMPUTER").child(en).child("EXTRA DATA").child("FOURTH SEMESTER").child("PERCENTAGE").setValue(per_4.toString())
                    if (fullString.contains("SUMMER")){
                        if (per_4 > first_per){
                            third_per = second_per
                            second_per = first_per
                            first_per = per_4
                            third_en = second_en
                            second_en = first_en
                            first_en = en
                        }
                    }
                }else{
                    smi.child(t_s).child("TY COMPUTER").child(en).child("EXTRA DATA").child("FOURTH SEMESTER").child("PERCENTAGE").setValue("-")
                }

            }

            if(fullString.contains("FIFTH SEMESTER")){
                val seat_no = fullString.substring(fullString.indexOf("FIFTH SEMESTER")-7,fullString.indexOf("FIFTH SEMESTER")-1).trim()

                val est_str = "EST "+ fullString.substring(fullString.indexOf("ENVIRONMENTAL STUDIES")+22,fullString.indexOf("OPERATING SYSTEMS")-1).trim()
                val osy_str = "OSY "+ fullString.substring(fullString.indexOf("OPERATING SYSTEMS")+18,fullString.indexOf("ADVANCED JAVA PROGRAMMING")-1).trim()
                val ajp_str = "AJP "+ fullString.substring(fullString.indexOf("ADVANCED JAVA PROGRAMMING")+26,fullString.indexOf("SOFTWARE TESTING")-1).trim()
                var ste_str : String = ""
                var css_str : String = ""
                if(fullString.contains("CLIENT SIDE SCRIPTING LANGUAGE")){
                    css_str = "CSS "+ fullString.substring(fullString.indexOf("CLIENT SIDE SCRIPTING LANGUAGE")+31,fullString.indexOf("INDUSTRIAL TRAINING")-1).trim()
                    ste_str = "STE "+ fullString.substring(fullString.indexOf("SOFTWARE TESTING")+17,fullString.indexOf("CLIENT SIDE SCRIPTING LANGUAGE")-1).trim()
                }

                var acn_str : String = ""
                if(fullString.contains("ADVANCED COMPUTER NETWORK")){
                    ste_str = "STE "+ fullString.substring(fullString.indexOf("SOFTWARE TESTING")+17,fullString.indexOf("ADVANCED COMPUTER NETWORK")-1).trim()
                    acn_str = "ACN "+ fullString.substring(fullString.indexOf("ADVANCED COMPUTER NETWORK")+26,fullString.indexOf("INDUSTRIAL TRAINING")-1).trim()
                }

                var adm_str : String = ""
                if(fullString.contains("ADVANCED COMPUTER NETWORK")){
                    ste_str = "STE "+ fullString.substring(fullString.indexOf("SOFTWARE TESTING")+17,fullString.indexOf("ADVANCED DATABASE MANAGEMENT SYSTEMS")-1).trim()
                    adm_str = "ADM "+ fullString.substring(fullString.indexOf("ADVANCED DATABASE MANAGEMENT SYSTEMS")+37,fullString.indexOf("INDUSTRIAL TRAINING")-1).trim()
                }

                val itr_str = "ITR "+ fullString.substring(fullString.indexOf("INDUSTRIAL TRAINING")+20,fullString.indexOf("CAPSTONE PROJECT PLANNING")-1).trim()
                val cpp_str = "CPP "+ fullString.substring(fullString.indexOf("CAPSTONE PROJECT PLANNING")+26,fullString.indexOf("CAPSTONE PROJECT PLANNING")+69).trim()

                val est_arr = est_str.split(" ")
                val osy_arr = osy_str.split(" ")
                val ajp_arr = ajp_str.split(" ")
                val ste_arr = ste_str.split(" ")
                val css_arr = css_str.split(" ")
                val acn_arr = acn_str.split(" ")
                val adm_arr = adm_str.split(" ")
                val itr_arr = itr_str.split(" ")
                val cpp_arr = cpp_str.split(" ")

                var sub_list = ArrayList<List<String>>()

                sub_list.add(est_arr)
                sub_list.add(osy_arr)
                sub_list.add(ajp_arr)
                sub_list.add(ste_arr)
                sub_list.add(itr_arr)
                sub_list.add(cpp_arr)

                if(fullString.contains("CLIENT SIDE SCRIPTING LANGUAGE")){
                    sub_list.add(css_arr)
                }
                if(fullString.contains("ADVANCED COMPUTER NETWORK")){
                    sub_list.add(acn_arr)
                }
                if(fullString.contains("ADVANCED DATABASE MANAGEMENT SYSTEMS")){
                    sub_list.add(adm_arr)
                }
                //?.toast(cpp_str)
                smi.child(t_s).child("TY COMPUTER").child(en).child("FIFTH SEMESTER").child("TOTAL CREDIT").setValue(fullString.substring(fullString.indexOf("CO5I")-10,fullString.indexOf("CO5I")-8))
                smi.child(t_s).child("TY COMPUTER").child(en).child("FIFTH SEMESTER").child("TOTAL MARKS").setValue(fullString.substring(fullString.indexOf("CO5I")-14,fullString.indexOf("CO5I")-11))
                smi.child(t_s).child("TY COMPUTER").child(en).child("FIFTH SEMESTER").child("OUT OF").setValue("900")

                if(fullString.substring(fullString.indexOf("CO5I")-20,fullString.indexOf("CO5I")-15).toDouble() > 00.00 && fullString.substring(fullString.indexOf("CO5I")-20,fullString.indexOf("CO5I")-15).toDouble() < 100.00){
                    per_5 = fullString.substring(fullString.indexOf("CO5I")-20,fullString.indexOf("CO5I")-15).toDouble()
                    smi.child(t_s).child("TY COMPUTER").child(en).child("FIFTH SEMESTER").child("PERCENTAGE").setValue(fullString.substring(fullString.indexOf("CO5I")-20,fullString.indexOf("CO5I")-15))
                    if (fullString.contains("WINTER")){
                        if (per_5 > first_per){
                            third_per = second_per
                            second_per = first_per
                            first_per = per_5
                            third_en = second_en
                            second_en = first_en
                            first_en = en
                        }
                    }
                }else{
                    smi.child(t_s).child("TY COMPUTER").child(en).child("FIFTH SEMESTER").child("PERCENTAGE").setValue("-")
                }
                check_update("FIFTH SEMESTER",en,seat_no,sub_list)
            }

            if(fullString.contains("SIX SEMESTER")){

                val seat_no = fullString.substring(fullString.indexOf("SIX SEMESTER")-7,fullString.indexOf("SIX SEMESTER")-1).trim()

                val mgt_str = "MGT "+ fullString.substring(fullString.indexOf("MANAGEMENT")+11,fullString.indexOf("PROGRAMMING WITH PYTHON")-1).trim()
                val pwp_str = "PWP "+ fullString.substring(fullString.indexOf("PROGRAMMING WITH PYTHON")+24,fullString.indexOf("MOBILE APPLICATION DEVELOPMENT")-1).trim()
                val mad_str = "MAD "+ fullString.substring(fullString.indexOf("MOBILE APPLICATION DEVELOPMENT")+31,fullString.indexOf("EMERGING TRENDS IN COMPUTER")-1).trim()
                var eti_str = ""
                var wbp_str = ""
                var nis_str = ""
                var dwm_str = ""

                if(fullString.contains("WEB BASED APPLICATION DEVELOPMENT WITH PHP")){
                    wbp_str = "WBP "+ fullString.substring(fullString.indexOf("WEB BASED APPLICATION DEVELOPMENT WITH PHP")+43,fullString.indexOf("ENTERPRENEURSHIP DEVELOPMENT")-1).trim()
                    eti_str = "ETI "+ fullString.substring(fullString.indexOf("EMERGING TRENDS IN COMPUTER")+28,fullString.indexOf("WEB BASED APPLICATION DEVELOPMENT WITH PHP")-1).trim()
                }
                if(fullString.contains("NETWORK AND SECURITY INFORMATION")){
                    nis_str = "NIS "+ fullString.substring(fullString.indexOf("NETWORK AND SECURITY INFORMATION")+33,fullString.indexOf("ENTERPRENEURSHIP DEVELOPMENT")-1).trim()
                    eti_str = "ETI "+ fullString.substring(fullString.indexOf("EMERGING TRENDS IN COMPUTER")+28,fullString.indexOf("NETWORK AND SECURITY INFORMATION")-1).trim()
                }
                if(fullString.contains("DATA WAREHOUSING AND MINING TECHNIQUES")){
                    dwm_str = "DWM "+ fullString.substring(fullString.indexOf("DATA WAREHOUSING AND MINING TECHNIQUES")+39,fullString.indexOf("ENTERPRENEURSHIP DEVELOPMENT")-1).trim()
                    eti_str = "ETI "+ fullString.substring(fullString.indexOf("EMERGING TRENDS IN COMPUTER")+28,fullString.indexOf("DATA WAREHOUSING AND MINING TECHNIQUES")-1).trim()
                }

                val ede_str = "EDE"+ fullString.substring(fullString.indexOf("ENTERPRENEURSHIP DEVELOPMENT")+29,fullString.indexOf("CAPSTONE PROJECT EXECUTION")-1).trim()
                val cpe_str = "CPE"+ fullString.substring(fullString.indexOf("CAPSTONE PROJECT EXECUTION")+27,fullString.indexOf("CAPSTONE PROJECT EXECUTION")+70).trim()

                val mgt_arr = mgt_str.split(" ")
                val pwp_arr = pwp_str.split(" ")
                val mad_arr = mad_str.split(" ")
                val eti_arr = eti_str.split(" ")
                val wbp_arr = wbp_str.split(" ")
                val nis_arr = nis_str.split(" ")
                val dwm_arr = dwm_str.split(" ")
                val ede_arr = ede_str.split(" ")
                val cpe_arr = cpe_str.split(" ")

                var sub_list = ArrayList<List<String>>()

                sub_list.add(mgt_arr)
                sub_list.add(pwp_arr)
                sub_list.add(mad_arr)
                sub_list.add(eti_arr)
                sub_list.add(ede_arr)
                sub_list.add(cpe_arr)

                if(fullString.contains("WEB BASED APPLICATION DEVELOPMENT WITH PHP")){
                    sub_list.add(wbp_arr)
                }
                if(fullString.contains("NETWORK AND SECURITY INFORMATION")){
                    sub_list.add(nis_arr)
                }
                if(fullString.contains("DATA WAREHOUSING AND MINING TECHNIQUES")){
                    sub_list.add(dwm_arr)
                }
                check_update("SIX SEMESTER",en,seat_no,sub_list)

                smi.child(t_s).child("TY COMPUTER").child(en).child("SIX SEMESTER").child("TOTAL CREDIT").setValue(fullString.substring(fullString.indexOf("CO6I")-10,fullString.indexOf("CO6I")-8))
                smi.child(t_s).child("TY COMPUTER").child(en).child("SIX SEMESTER").child("TOTAL MARKS").setValue(fullString.substring(fullString.indexOf("CO6I")-14,fullString.indexOf("CO6I")-11))
                smi.child(t_s).child("TY COMPUTER").child(en).child("SIX SEMESTER").child("OUT OF").setValue("850")

                if(fullString.substring(fullString.indexOf("CO6I")-20,fullString.indexOf("CO6I")-15).toDouble() > 00.00 && fullString.substring(fullString.indexOf("CO6I")-20,fullString.indexOf("CO6I")-15).toDouble() < 100.00){
                    per_6 = fullString.substring(fullString.indexOf("CO6I")-20,fullString.indexOf("CO6I")-15).toDouble()
                    smi.child(t_s).child("TY COMPUTER").child(en).child("SIX SEMESTER").child("PERCENTAGE").setValue(per_6.toString())
                    if (fullString.contains("SUMMER")){
                        if (per_6 > first_per){
                            third_per = second_per
                            second_per = first_per
                            first_per = per_6
                            third_en = second_en
                            second_en = first_en
                            first_en = en
                        }
                    }
                }else{
                    smi.child(t_s).child("TY COMPUTER").child(en).child("SIX SEMESTER").child("PERCENTAGE").setValue("-")
                }
            }

        }

        private fun check_update(sem: String, enrollment: String ,seat_no: String , subList: java.util.ArrayList<List<String>>) {
            var bl = 0
            for (sub in subList){
                //  smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child("BACK LOG").removeValue()
                ssr.child(sub[0].trim()).child("name").addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        var sn = p0.value.toString()
                        if(sub.size > 23  ){ // checking the size for TH  = 13 , TH & PR = 24 , PR = 13
                            smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child("SEAT NO").setValue(seat_no)
                            esm =  check_mark(sub[5])//for checking the marks
                            smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("ESE OBT").setValue(esm)

                            esmt = check_mark(sub[7])//for checking total marks
                            smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("ESE TOTAL OBT").setValue(esmt)
                            //for checking the credits
                            cr = sub[8]
                            if(cr.toInt() == 0){
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("CREDITS").setValue(cr)
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("STATUS").setValue("FAIL")
                                bl += 1
                                tfsc++
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child("BACK LOG").setValue(bl)
                            }else{
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("CREDITS").setValue(cr)
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("STATUS").setValue("PASS")
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child("BACK LOG").setValue(bl)
                            }
                            eim = check_mark(sub[12])//for checking internal marks
                            smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("ESE PA").setValue(eim)

                            psm = check_mark(sub[17])//for checking practical marks
                            smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("PA OBT").setValue(psm)

                            psmt = check_mark(sub[19])//for checking practical total marks
                            smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("PA TOTAL OBT").setValue(psmt)

                            pim = check_mark(sub[23])//for checking practical internal marks
                            smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("PA PA OBT").setValue(pim)

                        }else{
                            smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child("SEAT NO").setValue(seat_no)
                            esm =  check_mark(sub[5])//for checking the marks
                            smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("ESE OBT").setValue(esm)

                            esmt = check_mark(sub[7])//for checking total marks
                            smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("ESE TOTAL OBT").setValue(esmt)
                            //for checking the credits
                            cr = sub[8]
                            if(cr.toInt() == 0){
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("CREDITS").setValue(cr)
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("STATUS").setValue("FAIL")
                                bl += 1
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child("BACK LOG").setValue(bl)
                            }else{
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("CREDITS").setValue(cr)
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("STATUS").setValue("PASS")
                                smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child("BACK LOG").setValue(bl)
                            }

                            eim = check_mark(sub[12])//for checking internal marks
                            smi.child(t_s).child("TY COMPUTER").child(enrollment).child(sem).child(sn).child("ESE PA").setValue(eim)
                        }
                    }
                })
            }
        }
    }
}
