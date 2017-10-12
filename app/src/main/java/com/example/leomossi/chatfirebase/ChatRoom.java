package com.example.leomossi.chatfirebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChatRoom extends AppCompatActivity {

    private TextView textConversation;
    private Button btnSend;
    private EditText inputText;
    private DatabaseReference root;
    private String room_name,user_name;
    private String temp_key;

    ListView listViewMessage;
    MessageAdapter  adapter;
    List<Message> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        btnSend = (Button) findViewById(R.id.btn_send);
        inputText = (EditText) findViewById(R.id.inputText);

        SharedPreferences prefs = getBaseContext().getSharedPreferences("Username", Context.MODE_PRIVATE);
        user_name = prefs.getString("username", null);

//        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();

        setTitle("Room - " + room_name);

        root = FirebaseDatabase.getInstance().getReference().child(room_name);

        listViewMessage = (ListView) findViewById(R.id.listViewMessage);
        data = new ArrayList<>();
        adapter = new MessageAdapter(getBaseContext(),R.layout.item_message, data);
        adapter.setmUsername(user_name);
        listViewMessage.setAdapter(adapter);


        inputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    Message message  = new Message();
                    message.setText(inputText.getText().toString());
                    message.setUsername(user_name);

                    root.push().setValue(message);
                    inputText.getText().clear();
                    return true;
                }
                return false;
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message message  = new Message();
                message.setText(inputText.getText().toString());
                message.setUsername(user_name);

                root.push().setValue(message);
                inputText.getText().clear();



            }
        });

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                appendChatConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String chatMsg, chatUsername;
    private void appendChatConversation(DataSnapshot dataSnapshot) {

        Message message = dataSnapshot.getValue(Message.class);
        data.add(message);
        adapter.notifyDataSetChanged();
        listViewMessage.setSelection(data.size()-1);
        //listViewMessage.smoothScrollToPosition(data.size()-1);
        //Toast.makeText(ChatRoom.this, data.size()+"", Toast.LENGTH_SHORT).show();
    }
}