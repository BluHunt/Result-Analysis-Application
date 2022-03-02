package com.example.ra.UI.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ra.R;
import com.example.ra.Utils.teacher_subject;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.ra.Utils.HelperKt.toast;

public class UnitTest extends Fragment {
    private View SubjectListView;
    private RecyclerView SubjectList;
    private DatabaseReference sref = FirebaseDatabase.getInstance().getReference().child("Users");
    private DatabaseReference ver = FirebaseDatabase.getInstance().getReference().child("Users");
    private DatabaseReference sdref = FirebaseDatabase.getInstance().getReference().child("SUBJECT");
    private FirebaseAuth mAuth;
    private String currentUserID;
    private String currentRole = "";
    private ProgressBar subpb;
    public UnitTest() {// Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        SubjectListView = inflater.inflate(R.layout.fragment_unit_test, container, false);
        subpb = SubjectListView.findViewById(R.id.unit_test_pb);
        SubjectList = SubjectListView.findViewById(R.id.tslist);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        SubjectList.setLayoutManager(new LinearLayoutManager(getContext()));
        return SubjectListView;
    }
    @Override
    public void onStart() {
        super.onStart();
        SubjectList.setVisibility(View.INVISIBLE);
        subpb.setVisibility(View.VISIBLE);
        ver.child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Toast.makeText(getContext(),dataSnapshot.child("role").getValue().toString(),Toast.LENGTH_SHORT).show();
                if(dataSnapshot.child("role").getValue().toString().equals("Teacher")){
                    FirebaseRecyclerOptions<teacher_subject> options = new FirebaseRecyclerOptions.Builder<teacher_subject>()
                            .setQuery(sref.child(currentUserID).child("SUBJECTS"),teacher_subject.class)
                            .build();
                    FirebaseRecyclerAdapter<teacher_subject,SubjectViewHolder> adapter = new FirebaseRecyclerAdapter<teacher_subject, SubjectViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull final SubjectViewHolder holder, int position, @NonNull teacher_subject model) {
                            final String list_user_id = getRef(position).getKey();
                            sref.child(currentUserID).child("SUBJECTS").child(list_user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    SubjectList.setVisibility(View.VISIBLE);
                                    subpb.setVisibility(View.INVISIBLE);
                                    final String n = dataSnapshot.child("name").getValue().toString();
                                    final String c = dataSnapshot.child("code").getValue().toString();
                                    holder.name.setText(n);
                                    holder.code.setText(c);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                        @NonNull
                        @Override
                        public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject,parent,false);
                            SubjectViewHolder holder = new SubjectViewHolder(view);
                            return holder;
                        }
                    };
                    SubjectList.setAdapter(adapter);
                    adapter.startListening();
                }else {
                    FirebaseRecyclerOptions<teacher_subject> options = new FirebaseRecyclerOptions.Builder<teacher_subject>()
                            .setQuery(sdref,teacher_subject.class)
                            .build();
                    FirebaseRecyclerAdapter<teacher_subject,SubjectViewHolder> adapter = new FirebaseRecyclerAdapter<teacher_subject, SubjectViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull final SubjectViewHolder holder, int position, @NonNull teacher_subject model) {
                            final String list_user_id = getRef(position).getKey();
                            sdref.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    SubjectList.setVisibility(View.VISIBLE);
                                    subpb.setVisibility(View.INVISIBLE);
                                    final String n = dataSnapshot.child("name").getValue().toString();
                                    final String c = dataSnapshot.child("code").getValue().toString();
                                    holder.name.setText(n);
                                    holder.code.setText(c);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                        @NonNull
                        @Override
                        public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject,parent,false);
                            SubjectViewHolder holder = new SubjectViewHolder(view);
                            return holder;
                        }
                    };
                    SubjectList.setAdapter(adapter);
                    adapter.startListening();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



    }
    public static class SubjectViewHolder extends RecyclerView.ViewHolder{
        TextView name,code;
        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sub_name);
            code = itemView.findViewById(R.id.sub_code);
        }
    }
}


