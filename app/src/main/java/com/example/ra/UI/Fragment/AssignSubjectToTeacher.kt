package com.example.ra.UI.Fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.ra.R
import com.example.ra.Utils.teacher_subject
import com.example.ra.Utils.toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_assign_subject_to_teacher.*

class AssignSubjectToTeacher : Fragment() {
    private var db: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
    private var subread: DatabaseReference = FirebaseDatabase.getInstance().reference.child("SUBJECT")
    private lateinit var uid: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = AssignSubjectToTeacherArgs.fromBundle(arguments!!)
        uid = args.uid
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assign_subject_to_teacher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db.child(uid).child("SUBJECTS").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                Log.w("IN SUBJECT CHECK", "IN DATA CHANGE METHOD")
                if(p0.hasChild("eng")){ eng.isCheckable = true }
                if(p0.hasChild("bsc")){ bsc.isCheckable = true }
                if(p0.hasChild("ict")){ ict.isCheckable = true }
                if(p0.hasChild("bms")){ bms.isCheckable = true }
                if(p0.hasChild("ege")){ ege.isCheckable = true }
                if(p0.hasChild("wpc")){ wpc.isCheckable = true }
                if(p0.hasChild("eec")){ eec.isCheckable = true }
                if(p0.hasChild("ami")){ ami.isCheckable = true }
                if(p0.hasChild("bec")){ bec.isCheckable = true }
                if(p0.hasChild("pci")){ pci.isCheckable = true }
                if(p0.hasChild("bcc")){ bcc.isCheckable = true }
                if(p0.hasChild("cph")){ cph.isCheckable = true }
                if(p0.hasChild("wpd")){ wpd.isCheckable = true }
                if(p0.hasChild("oop")){ oop.isCheckable = true }
                if(p0.hasChild("dsu")){ dsu.isCheckable = true }
                if(p0.hasChild("cgr")){ cgr.isCheckable = true }
                if(p0.hasChild("dms")){ dms.isCheckable = true }
                if(p0.hasChild("dte")){ dte.isCheckable = true }
                if(p0.hasChild("jpr")){ jpr.isCheckable = true }
                if(p0.hasChild("sen")){ sen.isCheckable = true }
                if(p0.hasChild("dcc")){ dcc.isCheckable = true }
                if(p0.hasChild("mic")){ mic.isCheckable = true }
                if(p0.hasChild("gad")){ gad.isCheckable = true }
                if(p0.hasChild("est")){ est.isCheckable = true }
                if(p0.hasChild("osy")){ osy.isCheckable = true }
                if(p0.hasChild("ajp")){ ajp.isCheckable = true }
                if(p0.hasChild("ste")){ ste.isCheckable = true }
                if(p0.hasChild("css")){ css.isCheckable = true }
                if(p0.hasChild("acn")){ acn.isCheckable = true }
                if(p0.hasChild("adm")){ adm.isCheckable = true }
                if(p0.hasChild("itr")){ itr.isCheckable = true }
                if(p0.hasChild("cpp")){ cpp.isCheckable = true }
                if(p0.hasChild("mgt")){ mgt.isCheckable = true }
                if(p0.hasChild("pwp")){ pwp.isCheckable = true }
                if(p0.hasChild("mad")){ mad.isCheckable = true }
                if(p0.hasChild("eti")){ eti.isCheckable = true }
                if(p0.hasChild("wbp")){ wbp.isCheckable = true }
                if(p0.hasChild("nis")){ nis.isCheckable = true }
                if(p0.hasChild("dmw")){ dmw.isCheckable = true }
                if(p0.hasChild("ede")){ ede.isCheckable = true }
                if(p0.hasChild("cpe")){ cpe.isCheckable = true }
            }
        })
        assign_subject_btn.setOnClickListener{
            //db.child(uid).child("SUBJECTS").removeValue()
            /*
            subread.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    if(eng.isChecked){db.child(uid).child("SUBJECTS").child("eng").setValue(p0.child("eng").value)}
                    if(bsc.isChecked){db.child(uid).child("SUBJECTS").child("bsc").setValue(p0.child("bsc").value)}
                    if(ict.isChecked){db.child(uid).child("SUBJECTS").child("ict").setValue(p0.child("ict").value)}
                    if(bms.isChecked){db.child(uid).child("SUBJECTS").child("bms").setValue(p0.child("bms").value)}
                    if(ege.isChecked){db.child(uid).child("SUBJECTS").child("ege").setValue(p0.child("ege").value)}
                    if(wpc.isChecked){db.child(uid).child("SUBJECTS").child("wpc").setValue(p0.child("wpc").value)}
                    if(eec.isChecked){db.child(uid).child("SUBJECTS").child("eec").setValue(p0.child("eec").value)}
                    if(ami.isChecked){db.child(uid).child("SUBJECTS").child("ami").setValue(p0.child("ami").value)}
                    if(bec.isChecked){db.child(uid).child("SUBJECTS").child("bec").setValue(p0.child("bec").value)}
                    if(pci.isChecked){db.child(uid).child("SUBJECTS").child("pci").setValue(p0.child("pci").value)}
                    if(bcc.isChecked){db.child(uid).child("SUBJECTS").child("bcc").setValue(p0.child("bcc").value)}
                    if(cph.isChecked){db.child(uid).child("SUBJECTS").child("cph").setValue(p0.child("cph").value)}
                    if(wpd.isChecked){db.child(uid).child("SUBJECTS").child("wpd").setValue(p0.child("wpd").value)}
                    if(oop.isChecked){db.child(uid).child("SUBJECTS").child("oop").setValue(p0.child("oop").value)}
                    if(dsu.isChecked){db.child(uid).child("SUBJECTS").child("dsu").setValue(p0.child("dsu").value)}
                    if(cgr.isChecked){db.child(uid).child("SUBJECTS").child("cgr").setValue(p0.child("cgr").value)}
                    if(dms.isChecked){db.child(uid).child("SUBJECTS").child("dms").setValue(p0.child("dms").value)}
                    if(dte.isChecked){db.child(uid).child("SUBJECTS").child("dte").setValue(p0.child("dte").value)}
                    if(jpr.isChecked){db.child(uid).child("SUBJECTS").child("jpr").setValue(p0.child("jpr").value)}
                    if(sen.isChecked){db.child(uid).child("SUBJECTS").child("sen").setValue(p0.child("sen").value)}
                    if(dcc.isChecked){db.child(uid).child("SUBJECTS").child("dcc").setValue(p0.child("dcc").value)}
                    if(mic.isChecked){db.child(uid).child("SUBJECTS").child("mic").setValue(p0.child("mic").value)}
                    if(gad.isChecked){db.child(uid).child("SUBJECTS").child("gad").setValue(p0.child("gad").value)}
                    if(est.isChecked){db.child(uid).child("SUBJECTS").child("est").setValue(p0.child("est").value)}
                    if(osy.isChecked){db.child(uid).child("SUBJECTS").child("osy").setValue(p0.child("osy").value)}
                    if(ajp.isChecked){db.child(uid).child("SUBJECTS").child("ajp").setValue(p0.child("ajp").value)}
                    if(ste.isChecked){db.child(uid).child("SUBJECTS").child("ste").setValue(p0.child("ste").value)}
                    if(css.isChecked){db.child(uid).child("SUBJECTS").child("css").setValue(p0.child("css").value)}
                    if(acn.isChecked){db.child(uid).child("SUBJECTS").child("acn").setValue(p0.child("acn").value)}
                    if(adm.isChecked){db.child(uid).child("SUBJECTS").child("adm").setValue(p0.child("adm").value)}
                    if(itr.isChecked){db.child(uid).child("SUBJECTS").child("itr").setValue(p0.child("itr").value)}
                    if(cpp.isChecked){db.child(uid).child("SUBJECTS").child("cpp").setValue(p0.child("cpp").value)}
                    if(mgt.isChecked){db.child(uid).child("SUBJECTS").child("mgt").setValue(p0.child("mgt").value)}
                    if(pwp.isChecked){db.child(uid).child("SUBJECTS").child("pwp").setValue(p0.child("pwp").value)}
                    if(mad.isChecked){db.child(uid).child("SUBJECTS").child("mad").setValue(p0.child("mad").value)}
                    if(eti.isChecked){db.child(uid).child("SUBJECTS").child("eti").setValue(p0.child("eti").value)}
                    if(wbp.isChecked){db.child(uid).child("SUBJECTS").child("wbp").setValue(p0.child("wbp").value)}
                    if(nis.isChecked){db.child(uid).child("SUBJECTS").child("nis").setValue(p0.child("nis").value)}
                    if(dmw.isChecked){db.child(uid).child("SUBJECTS").child("dmw").setValue(p0.child("dmw").value)}
                    if(ede.isChecked){db.child(uid).child("SUBJECTS").child("ede").setValue(p0.child("ede").value)}
                    if(cpe.isChecked){db.child(uid).child("SUBJECTS").child("cpe").setValue(p0.child("cpe").value)}
                }
                override fun onCancelled(p0: DatabaseError) {

                }
            })*/
            /*if(eng.isChecked){
                subread.child("ENG").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                 
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("ENG").setValue(post)
                    }
                })
            }*/
            if(eng.isChecked){
                subread.child("eng").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("eng").setValue(post)
                    }
                })
            }
            if(bsc.isChecked){
                subread.child("bsc").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("bsc").setValue(post)
                    }
                })
            }
            if(ict.isChecked){
                subread.child("ict").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("ict").setValue(post)
                    }
                })
            }
            if(bms.isChecked){
                subread.child("bms").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("bms").setValue(post)
                    }
                })
            }
            if(ege.isChecked){
                subread.child("ege").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("ege").setValue(post)
                    }
                })
            }
            if(wpc.isChecked){
                subread.child("wpc").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("wpc").setValue(post)
                    }
                })
            }
            if(eec.isChecked){
                subread.child("eec").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("eec").setValue(post)
                    }
                })
            }
            if(ami.isChecked){
                subread.child("ami").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("ami").setValue(post)
                    }
                })
            }
            if(bec.isChecked){
                subread.child("bec").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("bec").setValue(post)
                    }
                })
            }
            if(pci.isChecked){
                subread.child("pci").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("pci").setValue(post)
                    }
                })
            }
            if(bcc.isChecked){
                subread.child("bcc").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("bcc").setValue(post)
                    }
                })
            }
            if(cph.isChecked){
                subread.child("cph").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("cph").setValue(post)
                    }
                })
            }
            if(wpd.isChecked){
                subread.child("wpd").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("wpd").setValue(post)
                    }
                })
            }
            if(oop.isChecked){
                subread.child("oop").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("oop").setValue(post)
                    }
                })
            }
            if(dsu.isChecked){
                subread.child("dsu").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("dsu").setValue(post)
                    }
                })
            }
            if(cgr.isChecked){
                subread.child("cgr").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("cgr").setValue(post)
                    }
                })
            }
            if(dms.isChecked){
                subread.child("dms").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("dms").setValue(post)
                    }
                })
            }
            if(dte.isChecked){
                subread.child("dte").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("dte").setValue(post)
                    }
                })
            }
            if(jpr.isChecked){
                subread.child("jpr").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("jpr").setValue(post)
                    }
                })
            }
            if(sen.isChecked){
                subread.child("sen").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("sen").setValue(post)
                    }
                })
            }
            if(dcc.isChecked){
                subread.child("dcc").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("dcc").setValue(post)
                    }
                })
            }
            if(mic.isChecked){
                subread.child("mic").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("mic").setValue(post)
                    }
                })
            }
            if(gad.isChecked){
                subread.child("gad").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("gad").setValue(post)
                    }
                })
            }
            if(est.isChecked){
                subread.child("est").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("est").setValue(post)
                    }
                })
            }
            if(osy.isChecked){
                subread.child("osy").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("osy").setValue(post)
                    }
                })
            }
            if(ajp.isChecked){
                subread.child("ajp").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("ajp").setValue(post)
                    }
                })
            }
            if(ste.isChecked){
                subread.child("ste").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("ste").setValue(post)
                    }
                })
            }
            if(css.isChecked){
                subread.child("css").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("css").setValue(post)
                    }
                })
            }
            if(acn.isChecked){
                subread.child("acn").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("acn").setValue(post)
                    }
                })
            }
            if(adm.isChecked){
                subread.child("adm").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("adm").setValue(post)
                    }
                })
            }
            if(itr.isChecked){
                subread.child("itr").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("itr").setValue(post)
                    }
                })
            }
            if(cpp.isChecked){
                subread.child("cpp").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("cpp").setValue(post)
                    }
                })
            }
            if(mgt.isChecked){
                subread.child("mgt").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("mgt").setValue(post)
                    }
                })
            }
            if(pwp.isChecked){
                subread.child("pwp").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("pwp").setValue(post)
                    }
                })
            }
            if(mad.isChecked){
                subread.child("mad").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("mad").setValue(post)
                    }
                })
            }
            if(eti.isChecked){
                subread.child("eti").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("eti").setValue(post)
                    }
                })
            }
            if(wbp.isChecked){
                subread.child("wbp").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("wbp").setValue(post)
                    }
                })
            }
            if(nis.isChecked){
                subread.child("nis").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("nis").setValue(post)
                    }
                })
            }
            if(dmw.isChecked){
                subread.child("dmw").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("dmw").setValue(post)
                    }
                })
            }
            if(ede.isChecked){
                subread.child("ede").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("ede").setValue(post)
                    }
                })
            }
            if(cpe.isChecked){
                subread.child("cpe").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(teacher_subject::class.java)
                        db.child(uid).child("SUBJECTS").child("cpe").setValue(post)
                    }
                })
            }
            context?.toast("Successfully Subject Added")
            val action = AssignSubjectToTeacherDirections.SubjectAssigned()
            Navigation.findNavController(it).navigate(action)
        }
    }
}
