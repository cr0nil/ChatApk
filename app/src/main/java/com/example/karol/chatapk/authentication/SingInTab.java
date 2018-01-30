package com.example.karol.chatapk.authentication;


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

import com.example.karol.chatapk.Main2Activity;
import com.example.karol.chatapk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by Karol on 24.01.2018.
 */

public class SingInTab extends Fragment implements View.OnClickListener {
    public final static String TAG1 = "SingInTab";
    private final int FACEBOOK_LOG_IN_REQUST_CODE = 64206;
    EditText emialText1;
    TextView failText;
    EditText passwordText1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sing_in_tab, container, false);
        Button singInBtn = (Button) rootView.findViewById(R.id.singInBtn1);

        singInBtn.setOnClickListener(this);
        emialText1 = (EditText) rootView.findViewById(R.id.emailText1);
        passwordText1 = (EditText) rootView.findViewById(R.id.passowrdText1);

        failText = (TextView) rootView.findViewById(R.id.failText);
        return rootView;
    }

    private FirebaseAuth firebaseAuth;
    //private FirebaseAuth.AuthStateListener mfirebaseAuthStateListener;
    public final static String TAG = "MainActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onStart() {
        super.onStart();
        //firebaseAuth.addAuthStateListener(mfirebaseAuthStateListener);

    }

    @Override
    public void onStop() {
        super.onStop();

    }


    public void singIn(String email, String password) {

        Log.d(TAG, "sing in: " + email);
        if (!validate()) {
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String messege;
                        if (task.isSuccessful()) {
                            Log.d(TAG, "singInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();


                            messege = "gut gut";
                            updateUI(user);
                            // startActivity(intent);
                        } else {
                            Log.d(TAG, "singIn WithEmail:failure");
                            Toast.makeText(getActivity(), "Authentication failde. ", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            // Intent intent = new Intent(getActivity(), Main3Activity.class);
                            // startActivity(intent);
                            messege = "fail fail";
                        }
                        failText.setText(messege);
                        // dialog.hide();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                failText.setText(e.getMessage());
                e.printStackTrace();
            }
        });
    }



    private void updateUI(FirebaseUser currentUser) {
//        //  dialog.hide();
        if (currentUser != null) {

            Intent intent = new Intent(getActivity(), Main2Activity.class);
            startActivity(intent);
        } else {

        }

    }

    public boolean validate() {
        boolean valid = true;
        String email = emialText1.getText().toString();
        String password = passwordText1.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emialText1.setError(" Wymagane!");
            valid = false;
        } else {
            emialText1.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordText1.setError("Wymagane!");
            valid = false;
        } else {
            passwordText1.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.singInBtn1) {
            singIn(emialText1.getText().toString(), passwordText1.getText().toString());


        }


    }


}
