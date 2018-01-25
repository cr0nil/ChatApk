package com.example.karol.chatapk;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

/**
 * Created by Karol on 24.01.2018.
 */

public class SingInTab extends Fragment implements View.OnClickListener{
    public final static String TAG1 = "SingInTab";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sing_in_tab, container, false);

        return rootView;
    }

    private FirebaseAuth firebaseAuth;
    public final static String TAG = "MainActivity";

    EditText emialText1;
    EditText passwordText1;
    TabLayout tabLayout;




    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();

        Button singInBtn = getView().findViewById(R.id.singInBtn1);
        singInBtn.setOnClickListener(this);
        emialText1 = (EditText)getView().findViewById(R.id.emailText1);
        passwordText1 =(EditText)getView().findViewById(R.id.passowrdText1);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }



   // public static Toast toast ;
    public void singIn(String email, String password) {

        Log.d(TAG,"sing in: "+email);
        if(!validate()){
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "singInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(getActivity(), Main2Activity.class);


        //                    updateUI(user);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "singIn WithEmail:failure");
                         // Toast.makeText(getContext(), "Authentication failde. ", Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                           Intent intent = new Intent(getActivity(), Main3Activity.class);
                           startActivity(intent);
                        }
                        if (!task.isSuccessful()) {
                            //status textView
                        }
                        // dialog.hide();
                    }
                });
    }



    //public void send
    private void updateUI(FirebaseUser currentUser) {
//        //  dialog.hide();
//        if(currentUser != null){
//
//            //findViewById(R.id.)
//           // findViewById(R.id.verifiyBtn).setEnabled(!currentUser.isEmailVerified());
//            //setContentView(R.layout.activity_main3);
//
//        }
//        else{
//
//        }

    }

    public boolean validate() {
        boolean valid = true;
        String email = emialText1.getText().toString();
        String password = passwordText1.getText().toString();

        if(TextUtils.isEmpty(email)){
            emialText1.setError(" Wymagane!");
            valid = false;
        }
        else{
            emialText1.setError(null);
        }

        if(TextUtils.isEmpty(password)){
            passwordText1.setError("Wymagane!");
            valid = false;
        }
        else{
            passwordText1.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i  == R.id.singInBtn1){
            singIn(emialText1.getText().toString(),passwordText1.getText().toString());


        }



    }


}
