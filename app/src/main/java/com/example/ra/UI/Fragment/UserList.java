package com.example.ra.UI.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ra.R;
import com.example.ra.Utils.users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.ra.Utils.HelperKt.ANU;


public class UserList extends Fragment implements View.OnClickListener {
    private RecyclerView UsersList;
    private DatabaseReference UserRef;
    private ProgressBar pbul;
    private FloatingActionButton fabmain;
    public UserList() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, false);
        fabmain = fragmentView.findViewById(R.id.main);
        //Floating Button Setting
        fabmain.setOnClickListener(this);
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        UsersList = fragmentView.findViewById(R.id.rvlist);
        pbul = fragmentView.findViewById(R.id.pbul);
        UsersList.setLayoutManager(new LinearLayoutManager(getContext()));
        return fragmentView;
    }
    @Override
    public void onStart() {
        super.onStart();
        UsersList.setVisibility(View.INVISIBLE);
        pbul.setVisibility(View.VISIBLE);
        FirebaseRecyclerOptions<users> options = new FirebaseRecyclerOptions.Builder<users>()
                .setQuery(UserRef,users.class)
                .build();

        FirebaseRecyclerAdapter<users,UserViewHolder> adapter = new FirebaseRecyclerAdapter<users, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final UserViewHolder holder, final int position, @NonNull users model) {
                final String list_user_id = getRef(position).getKey();
                UserRef.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.username.setText(dataSnapshot.child("username").getValue().toString());
                        holder.email.setText(dataSnapshot.child("email").getValue().toString());
                        holder.role.setText(dataSnapshot.child("role").getValue().toString());
                        Picasso.get().load(dataSnapshot.child("profileuri").getValue().toString()).into(holder.upi);

                        final String r = dataSnapshot.child("role").getValue().toString();

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!r.isEmpty()){
                                    if( r.equals("Teacher")){
                                        String uid = getRef(position).getKey();
                                        UserListDirections.AssignSubject action = UserListDirections.AssignSubject();
                                        action.setUid(uid);
                                        Navigation.findNavController(v).navigate(action);
                                    }
                                }
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
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout,parent,false );
                UserViewHolder holder = new UserViewHolder(view);
                return holder;
            }
        };
        UsersList.setAdapter(adapter);
        pbul.setVisibility(View.INVISIBLE);
        UsersList.setVisibility(View.VISIBLE);
        adapter.startListening();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main:
                NavDirections action = UserListDirections.NewUserAdd();
                Navigation.findNavController(v).navigate(action);
                break;
        }

    }
    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView username,email,role;
        ImageView upi;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            upi = itemView.findViewById(R.id.user_layout_profile_img);
            username = itemView.findViewById(R.id.user_layout_name);
            email = itemView.findViewById(R.id.user_layout_email);
            role = itemView.findViewById(R.id.user_layout_role);
        }
    }

}
