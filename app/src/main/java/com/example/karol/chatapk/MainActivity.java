package com.example.karol.chatapk;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    public final static String TAG = "MainActivity";
    ProgressDialog dialog;
    EditText emialText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        emialText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passowrdText);

        findViewById(R.id.singInBtn).setOnClickListener(this);
        findViewById(R.id.singOutBtn).setOnClickListener(this);
        findViewById(R.id.createBtn).setOnClickListener(this);
        findViewById(R.id.verifiyBtn).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }


    public void createAccount(final String email, final String password) {
        Log.d(TAG,"create accunt: "+email);
        if(!validate()){
            return;
        }
     //   dialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.d(TAG, "createUserWithEmail:failure");
                            Toast.makeText(MainActivity.this, "Authentication failde. "+email+" "+password, Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                       // dialog.hide();
                    }
                });
    }

    public void singIn(String email, String password) {
        Log.d(TAG,"sing in: "+email);
        if(!validate()){
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "singInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.d(TAG, "singIn WithEmail:failure");
                            Toast.makeText(MainActivity.this, "Authentication failde. ", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        if (!task.isSuccessful()) {
                            //status textView
                        }
                       // dialog.hide();
                    }
                });
    }

    private void singOut() {
        firebaseAuth.signOut();
        updateUI(null);
    }

    //public void send
    private void updateUI(FirebaseUser currentUser) {
      //  dialog.hide();
        if(currentUser != null){
            //findViewById(R.id.)
            findViewById(R.id.verifiyBtn).setEnabled(!currentUser.isEmailVerified());
        }

    }

    public boolean validate() {
        boolean valid = true;
        String email = emialText.getText().toString();
        String password = passwordText.getText().toString();

        if(TextUtils.isEmpty(email)){
            emialText.setError(" Wymagane!");
            valid = false;
        }
        else{
            emialText.setError(null);
        }

        if(TextUtils.isEmpty(password)){
            passwordText.setError("Wymagane!");
            valid = false;
        }
        else{
            passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i  == R.id.singInBtn){
            singIn(emialText.getText().toString(),passwordText.getText().toString());

        }
        else if(i == R.id.createBtn){
            createAccount(emialText.getText().toString(),passwordText.getText().toString());
        }
        else if(i == R.id.singOutBtn ){
            singOut();
        }
        else if(i == R.id.verifiyBtn){
            sendEmailVerification();
        }


    }
    public void sendEmailVerification(){
         findViewById(R.id.verifiyBtn).setEnabled(false);
         final FirebaseUser user = firebaseAuth.getCurrentUser();
         user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 findViewById(R.id.verifiyBtn).setEnabled(true);
                 if(task.isSuccessful()){
                     Toast.makeText(MainActivity.this,"Weryfikacja wysłana na email"+user.getEmail(),Toast.LENGTH_SHORT).show();
                 }
                 else {
                     Log.e(TAG,"sendEmialVerification",task.getException());
                     Toast.makeText(MainActivity.this,"Weryfikacja nie powiodła się"+user.getEmail(),Toast.LENGTH_SHORT).show();

                 }
             }
         });
    }
}
