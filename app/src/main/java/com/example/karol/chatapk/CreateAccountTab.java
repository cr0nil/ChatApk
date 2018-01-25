package com.example.karol.chatapk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class CreateAccountTab extends Fragment implements View.OnClickListener{
    public final static String TAG2 = "CreateAccountTab";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_account, container, false);

        return rootView;
    }

    private FirebaseAuth firebaseAuth;
    public final static String TAG = "MainActivity";

    EditText emialText1;
    EditText passwordText1;


    Button veryfiyBtn;

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();

        Button createAccBtn = getView().findViewById(R.id.createBtn1);
         veryfiyBtn = getView().findViewById(R.id.verifiyBtn2);
        createAccBtn.setOnClickListener(this);
        emialText1 = (EditText)getView().findViewById(R.id.emailText2);
        passwordText1 =(EditText)getView().findViewById(R.id.passowrdText2);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }



    // public static Toast toast ;
    public void createAccount(final String email, final String password) {
        Log.d(TAG,"create accunt: "+email);
        if(!validate()){
            return;
        }
        //   dialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG2, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.d(TAG2, "createUserWithEmail:failure");
                            Toast.makeText(getContext(), "Authentication failde. "+email+" "+password, Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        // dialog.hide();
                    }
                });
    }



    //public void send
    private void updateUI(FirebaseUser currentUser) {
        //  dialog.hide();
        if(currentUser != null){

            //findViewById(R.id.)
            // findViewById(R.id.verifiyBtn).setEnabled(!currentUser.isEmailVerified());
            //setContentView(R.layout.activity_main3);

        }
        else{

        }

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
        if (i == R.id.createBtn1) {
            createAccount(emialText1.getText().toString(), passwordText1.getText().toString());


        }
    }
    public void sendEmailVerification(){
      veryfiyBtn.setEnabled(false);
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener((Executor) this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                veryfiyBtn.setEnabled(true);
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Weryfikacja wysłana na email"+user.getEmail(),Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e(TAG,"sendEmialVerification",task.getException());
                    Toast.makeText(getContext(),"Weryfikacja nie powiodła się"+user.getEmail(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
