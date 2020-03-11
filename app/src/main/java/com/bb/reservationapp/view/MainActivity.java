package com.bb.reservationapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.bb.reservationapp.R;
import com.bb.reservationapp.adapter.GuestAdapter;
import com.bb.reservationapp.database.GuestDatabaseHelper;
import com.bb.reservationapp.model.Guest;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements GuestAdapter.Reservation {

    private GuestDatabaseHelper guestDatabaseHelper;

    private ImageButton deleteGuest;

    private List guestList = new ArrayList<Guest>();

    public static final int REQUEST_CODE = 707;

    private List<Guest> guests = new ArrayList<>();


    @BindView(R.id.main_recyclerview)
    RecyclerView guestRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        guestDatabaseHelper = new GuestDatabaseHelper(this, null, null, 0);


        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(guestRecyclerView);
   }


    @Override
    protected void onResume() {
        super.onResume();
        readFromDatabase();
    }

    private void readFromDatabase() {
        Cursor guestCursor = guestDatabaseHelper.readAllGuests();
        guestCursor.moveToPosition(-1);

        guestList.clear();
        Log.d("TAG_X", "list cleared..");
        while(guestCursor.moveToNext()) {

            Log.d("TAG_X", ".....readiing.....");
            String guestName = guestCursor.getString(guestCursor.getColumnIndex(GuestDatabaseHelper.COLUMN_GUEST_NAME));
            int guestId = guestCursor.getInt(guestCursor.getColumnIndex(GuestDatabaseHelper.COLUMN_GUEST_ID));
            String guestGender = guestCursor.getString(guestCursor.getColumnIndex(GuestDatabaseHelper.COLUMN_GUEST_GENDER));
            String guestDate = guestCursor.getString(guestCursor.getColumnIndex(GuestDatabaseHelper.COLUMN_GUEST_CHECK_DATE));
            String guestRoom = guestCursor.getString(guestCursor.getColumnIndex(GuestDatabaseHelper.COLUMN_ROOM_NUMBER));

            guestList.add(new Guest(guestId, guestName, guestGender, guestDate, guestRoom));
        }
        Log.d("TAG_X", "READ COMPLETE");
        updateRecyclerView();
        guestCursor.close();
    }


    @OnClick(R.id.add_guest_button)
    public void AddNewGuest(View view){
        Intent addGuest = new Intent(this, AddGuestActivity.class);
        startActivityForResult(addGuest, REQUEST_CODE);
    }

//    @OnClick(R.id.delete_imageButton)
//    public void deleteGuest() {
//        deleteGuest();
//    }


    @Override
    public void deleteGuest(Guest deleteGuest) {
        guestDatabaseHelper.deleteGuest(deleteGuest);
        readFromDatabase();
    }

    private void updateRecyclerView() {
        Log.d("TAG_X", "updateRecyclerView "+guestList.size());
        GuestAdapter adapter = new GuestAdapter(guestList, this);
        guestRecyclerView.setAdapter(adapter);
        guestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }




    @Override
    public void getGuest(Guest guest) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG_X", "onDestroy");
        guestDatabaseHelper.close();
    }
}
