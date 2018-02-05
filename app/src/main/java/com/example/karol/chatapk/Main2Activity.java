package com.example.karol.chatapk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.karol.chatapk.authentication.MainActivity;
import com.facebook.login.LoginManager;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    // MFirebaseListAdapter adapter;
    private FirebaseListAdapter<ChatMessege> adapter;
    RelativeLayout activity_main2;
    FloatingActionButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        send = (FloatingActionButton) findViewById(R.id.sendBtn);
        findViewById(R.id.outBtn).setOnClickListener(this);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputText = (EditText) findViewById(R.id.inputText);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessege(inputText.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                inputText.setText("");
            }
        });

        activity_main2 = (RelativeLayout) findViewById(R.id.activiti2);
        //  Check if not sign-in then navigate Signin page
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            Snackbar.make(activity_main2, "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_SHORT).show();
            //Load content
            displayChatMessage();
        }

    }

    private void displayChatMessage() {
        ListView listView = (ListView) findViewById(R.id.list_of_message);
        adapter = new FirebaseListAdapter<ChatMessege>(this, ChatMessege.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessege model, int position) {
                TextView messageText, messageUser, messageTime;
                messageText = (TextView) v.findViewById(R.id.messege_text);
                messageUser = (TextView) v.findViewById(R.id.messege_user);
                messageTime = (TextView) v.findViewById(R.id.messege_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
            }


        };
        listView.setAdapter(adapter);
    }

    public void singOut() {
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
        if (id == R.id.outBtn) {
            singOut();
            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}