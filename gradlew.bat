package com.haanhgs.app.simplechat.holders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haanhgs.app.simplechat.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatHolder extends RecyclerView.ViewHolder{

    private final String TAG = "CA/ChatHolder";
    private Activity activity;
    private View view;
    private Context context;
    private DatabaseReference userDatabase;
    private ValueEventListener userListener;

    private TextView tvUserName;
    private TextView tvUserStatus;
    private TextView userTime;
    private CircleImageView userImage;
    private ImageView userOnline;


    public ChatHolder(Activity activity, View view, Context context){
        super(view);
        this.activity = activity;
        this.view = view;
        this.context = context;
    }

    private void initViews(View view){
        tvUserName = view.findViewById(R.id.user_name);
        tvUserStatus = view.findViewById(R.id.user_status);
        userTime = view.findViewById(R.id.user_timestamp);
        userImage = view.findViewById(R.id.user_image);
        userOnline = view.findViewById(R.id.user_online);
    }

    private void updateViews(String message, long timestamp){
        tvUserStatus.setText(message);
        userTime.setVisibility(View.VISIBLE);
        String date = new SimpleDateFormat("MMM d, HH:mm", Locale.getDefault()).format(timestamp);
        userTime.setText(date);
    }

