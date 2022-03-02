package com.example.ra.UI.Fragment


    import android.icu.lang.UCharacterEnums
    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.navigation.Navigation

    import com.example.ra.R
    import com.example.ra.Utils.toast
    import com.google.firebase.database.*
    import kotlinx.android.synthetic.main.fragment_edit_student_info.*
    class EditStudentInfo : Fragment() {
        private var db: DatabaseReference = FirebaseDatabase.getInstance().reference.child("STUDENT")
        private lateinit var year_n: String
        private lateinit var year_w: String
        private lateinit var sid: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = EditStudentInfoArgs.fromBundle(arguments!!)
        sid = args.sid
        year_n = args.yearN
        year_w = args.yearW
        db.child(year_n).child(year_w).child(sid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    if(p0.hasChild("Enrollment No")){
                        student_name.setText(p0.child("Name").value.toString())
                        student_roll_no.setText(p0.child("Roll No").value.toString())
                        student_enrollment_no.setText(p0.child("Enrollment No").value.toString())
                    }else{
                        student_name.setText(p0.child("Name").value.toString())
                        student_roll_no.setText(p0.child("Roll No").value.toString())
                        student_enrollment_no.setText("-")
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }

        })
        //student_name.setText(s_name)
        //student_roll_no.setText(s_roll)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_student_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updatebtn.setOnClickListener{
            val s_n_n = student_name.text.toString()
            val s_n_r = student_roll_no.text.toString()
            val s_n_e = student_enrollment_no.text.toString()
            if(s_n_n.isEmpty()){
                student_name.error = "Username is Empty"
                student_name.requestFocus()
                return@setOnClickListener
            }
            if(s_n_r.isEmpty()){
                student_roll_no.error = "Roll No is Empty"
                student_roll_no.requestFocus()
                return@setOnClickListener
            }
            if(s_n_e.isEmpty()){
                student_enrollment_no.error = "Enrollment No is Empty"
                student_enrollment_no.requestFocus()
                return@setOnClickListener
            }
            db.child(year_n).child(year_w).child(sid).child("Roll No").setValue(s_n_r)
            db.child(year_n).child(year_w).child(sid).child("Name").setValue(s_n_n)
            db.child(year_n).child(year_w).child(sid).child("Enrollment No").setValue(s_n_e)
            context?.toast("Successfully Information Saved")
            val action = EditStudentInfoDirections.ToBack()
            Navigation.findNavController(it).navigate(action)

        }

    }

}
