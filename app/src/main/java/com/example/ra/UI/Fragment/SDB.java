package com.example.ra.UI.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ra.R;
import com.example.ra.Utils.stu;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SDB extends Fragment {
    private RecyclerView sl;
    private DatabaseReference sref;
    private ProgressBar p;
    private String year_in_n,year_in_w;
    public SDB() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View sdv = inflater.inflate(R.layout.fragment_sdb, container, false);
        SDBArgs args = SDBArgs.fromBundle(getArguments());
        p = sdv.findViewById(R.id.slpb);
        year_in_n = args.getYearInN();
        year_in_w = args.getYearInW();
        sref = FirebaseDatabase.getInstance().getReference().child("STUDENT").child(year_in_n).child(year_in_w);
        sl = sdv.findViewById(R.id.sdrclist);
        sl.setLayoutManager(new LinearLayoutManager(getContext()));
        return sdv;
    }
    @Override
    public void onStart() {
        super.onStart();
        sl.setVisibility(View.INVISIBLE);
        p.setVisibility(View.VISIBLE);
        FirebaseRecyclerOptions<stu> stuFirebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<stu>()
                .setQuery(sref,stu.class)
                .build();
        FirebaseRecyclerAdapter<stu,StuViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<stu, StuViewHolder>(stuFirebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final StuViewHolder holder, final int position, @NonNull stu model) {
                final String stu_id = getRef(position).getKey();
                sref.child(stu_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sl.setVisibility(View.VISIBLE);
                        p.setVisibility(View.INVISIBLE);
                        if(dataSnapshot.hasChild("Enrollment No")){
                            holder.name.setText(dataSnapshot.child("Name").getValue().toString());
                            holder.rollno.setText(dataSnapshot.child("Roll No").getValue().toString());
                            holder.enrollment.setText(dataSnapshot.child("Enrollment No").getValue().toString());
                        }else {
                            holder.name.setText(dataSnapshot.child("Name").getValue().toString());
                            holder.rollno.setText(dataSnapshot.child("Roll No").getValue().toString());
                            holder.enrollment.setText("-");
                        }
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String stu_id = getRef(position).getKey();
                                SDBDirections.ToEdit action = SDBDirections.ToEdit();
                                action.setSid(stu_id);
                                action.setYearN(year_in_n);
                                action.setYearW(year_in_w);
                                Navigation.findNavController(v).navigate(action);
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            @NonNull
            @Override
            public StuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_view,parent,false);
                StuViewHolder stuViewHolder = new StuViewHolder(v);
                return stuViewHolder;
            }
        };
        sl.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class StuViewHolder extends RecyclerView.ViewHolder{
        TextView name,rollno,enrollment;
        public StuViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.stu_name);
            rollno = itemView.findViewById(R.id.stu_roll);
            enrollment = itemView.findViewById(R.id.stu_enrollment);
        }
    }

}
