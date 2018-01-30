package com.example.karol.chatapk.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.karol.chatapk.Main2Activity;
import com.example.karol.chatapk.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    public final static String TAG = "MainActivity";
    public CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.singOutBtn).setOnClickListener(this);
      //  findViewById(R.id.createBtn).setOnClickListener(this);
        findViewById(R.id.FbBtn).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        updateUI(currentUser);
    }

//
public void initFacebookSingIn(){// Initialize Facebook Login button
    mCallbackManager = CallbackManager.Factory.create();
    LoginButton loginButton = findViewById(R.id.FbBtn);
    loginButton.setReadPermissions("email", "public_profile");
    loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d(TAG, "facebook:onSuccess:" + loginResult);
            handleFacebookAccessToken(loginResult.getAccessToken());
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "facebook:onCancel");
            // ...
        }

        @Override
        public void onError(FacebookException error) {
            Log.d(TAG, "facebook:onError", error);
            // ...
        }
    });

// ...

 }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {

            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        } else {

        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.singOutBtn ){
//            singOut();
            Intent intent = new Intent(MainActivity.this, Main3Activity.class);

            startActivity(intent);
        }
        else if(i == R.id.FbBtn){
            initFacebookSingIn();
           // sendEmailVerification();
        }


    }


}
