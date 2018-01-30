package com.example.karol.chatapk;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.karol.chatapk.authentication.MainActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.URI;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        ImageView img = (ImageView) findViewById(R.id.imageView);
//        img.setImageResource(R.drawable.duck);
findViewById(R.id.outBtn).setOnClickListener(this);

    }
    public void singOut(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser currentUser) {
//        //  dialog.hide();
        if (currentUser != null) {


        } else {
            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.outBtn){
            singOut();
            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}