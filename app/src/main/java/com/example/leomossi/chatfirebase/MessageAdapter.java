package com.example.leomossi.chatfirebase;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by LeoMossi on 10/10/2017.
 */

public class MessageAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Message> mMessageList;
    private int mLayout;
    private String mUsername;


    public MessageAdapter(Context context, int resource, List<Message> messagesList) {
        super(context, resource, messagesList);
        mContext = context;
        mMessageList = messagesList;
        mLayout = resource;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mLayout, parent, false);
        }
        TextView myMessage = (TextView) view.findViewById(R.id.my_message);
        TextView otherMessage = (TextView) view.findViewById(R.id.other_message);
        TextView tvUsername = (TextView) view.findViewById(R.id.tv_username);
        ImageView imageView = (ImageView) view.findViewById(R.id.other_image);

        if (mUsername.equals(mMessageList.get(position).getUsername())) {
            myMessage.setText(mMessageList.get(position).getText());
            otherMessage.setText("");
            tvUsername.setText("");
            imageView.setVisibility(View.GONE);

        } else {
            otherMessage.setText(mMessageList.get(position).getText());
            tvUsername.setText(mMessageList.get(position).getUsername());
            myMessage.setText("");
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
