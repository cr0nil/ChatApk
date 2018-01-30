package com.example.karol.chatapk.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karol.chatapk.Main2Activity;
import com.example.karol.chatapk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by Karol on 24.01.2018.
 */

public class CreateAccountTab extends Fragment implements View.OnClickListener {
    public final static String TAG2 = "CreateAccountTab";
    private FirebaseAuth firebaseAuth;
    public final static String TAG = "MainActivity";

    EditText emialText1;
    EditText passwordText1;

    Button veryfiyBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_account, container, false);

        Button createAccBtn = (Button) rootView.findViewById(R.id.createBtn1);
        veryfiyBtn = rootView.findViewById(R.id.verifiyBtn2);
        createAccBtn.setOnClickListener(this);
        emialText1 = (EditText) rootView.findViewById(R.id.emailText2);
        passwordText1 = (EditText) rootView.findViewById(R.id.passowrdText2);

        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        updateUI(currentUser);
    }


    // public static Toast toast ;
    public void createAccount(final String email, final String password) {
        Log.d(TAG, "create accunt: " + email);
        if (!validate()) {
            return;
        }
        //   dialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String messege;
                        if (task.isSuccessful()) {
                            Log.d(TAG, "singInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

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

                    }

                });
    }


    private void updateUI(FirebaseUser currentUser) {
        //  dialog.hide();
        if (currentUser != null) {
            sendEmailVerificatio();
            getActivity().findViewById(R.id.verifiyBtn2).setEnabled(!currentUser.isEmailVerified());
        } else {
            getActivity().findViewById(R.id.verifiyBtn2).setVisibility(View.GONE);
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
        if (i == R.id.createBtn1) {
            createAccount(emialText1.getText().toString(), passwordText1.getText().toString());

        } else if (i == R.id.verifiyBtn2) {
            Intent intent = new Intent(getActivity(), Main2Activity.class);
            startActivity(intent);

        }

    }

    public void sendEmailVerificatio() {
        veryfiyBtn.setEnabled(false);
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(),
                            "Verification email sent to " + user.getEmail(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(getActivity(),
                            "Failed to send verification email.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

