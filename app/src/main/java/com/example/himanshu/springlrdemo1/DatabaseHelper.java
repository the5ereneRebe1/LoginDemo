package com.example.himanshu.springlrdemo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.himanshu.springlrdemo1.users.SmartUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseHelper {

    private  SmartUser userd;

    public static void init(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    public void writeEntry(SmartUser user, final Context context){
        userd = user;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");

        database.child(user.getUserId()).setValue((SmartUser)user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                Toast.makeText(context, "Written to db", Toast.LENGTH_SHORT).show();
                // ...
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to write"+userd.toString(), Toast.LENGTH_SHORT).show();
                        // ...
                    }
                });;
    }
    public SmartUser readEntry(SmartUser user, final Context context){

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");

        DatabaseReference userref= database.child(user.getUserId());

            userref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userd = dataSnapshot.getValue(SmartUser.class);
                    UserSessionManager.setUserSession(context,userd);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return UserSessionManager.getCurrentUser(context);
        }

    }

