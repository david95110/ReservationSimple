package com.bb.reservationapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bb.reservationapp.R;
import com.bb.reservationapp.adapter.GuestAdapter;
import com.bb.reservationapp.model.Guest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Button closeApp;

    public static final int REQUEST_CODE = 707;

    private List<Guest> guests = new ArrayList<>();

    @BindView(R.id.main_recyclerview)
    private RecyclerView guestRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_guest_button)
    public void AddNewGuest(View view){
        Intent addGuest = new Intent(this, AddGuestActivity.class);
        startActivityForResult(addGuest, REQUEST_CODE);
    }

    private void updateRecyclerView() {

        GuestAdapter adapter = new GuestAdapter(guests, this);

        guestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        guestRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.close_app_button)
    public void closeActivity (View view){
        closeApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG_X", "onDestroy");
    }
}
