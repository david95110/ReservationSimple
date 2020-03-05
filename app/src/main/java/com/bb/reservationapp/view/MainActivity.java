package com.bb.reservationapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bb.reservationapp.R;
import com.bb.reservationapp.adapter.GuestAdapter;
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

    private Button closeApp;

    public static final int REQUEST_CODE = 707;

    private List<Guest> guests = new ArrayList<>();

    private String fileName = "Guests.txt";

    private String delimiter = "|";


    @BindView(R.id.main_recyclerview)
    RecyclerView guestRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //closeActivity();

// fix method to close application
//    @OnClick(R.id.close_app_button)
//    void closeActivity() {
//        closeApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        }
//        );
   }


    @OnClick(R.id.add_guest_button)
    public void AddNewGuest(View view){
        Intent addGuest = new Intent(this, AddGuestActivity.class);
        startActivityForResult(addGuest, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            Guest guest = null;
            if (data != null){
                guest = (Guest) data.getSerializableExtra(AddGuestActivity.GUEST_KEY);
                guests.add(guest);

            try {
                writeToInternalStorage(guest);
                readFromInternalStorage();
            } catch (IOException e) {
                Log.d("TAG_X", e.getLocalizedMessage());
                e.printStackTrace();
            }
            }
            updateRecyclerView();
        }
    }

    private void updateRecyclerView() {

        GuestAdapter adapter = new GuestAdapter(guests, this);

        guestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        guestRecyclerView.setAdapter(null);
        guestRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG_X", "onDestroy");
    }

    private void writeToInternalStorage(Guest guest) throws IOException {

        FileOutputStream fileOS = openFileOutput(fileName, Context.MODE_APPEND);
        String guestText = guest.getName() + delimiter + guest.getGender() + delimiter
                + guest.getCheckDate() + delimiter + guest.getRoom() + "\n";

        Log.d("TAG_X", "Write started.");

        byte[] bytes = guestText.getBytes();
        fileOS.write(bytes);
        fileOS.close();
        Log.d("TAG_X", "Write complete.");
    }

    private void readFromInternalStorage() throws IOException {

        FileReader fileReader = new FileReader(getFilesDir().getPath() + "/" + fileName);
        BufferedReader reader = new BufferedReader(fileReader);

        String readIn = null;

        while ((readIn = reader.readLine()) != null) {

            Log.d("TAG_X", "Line from file : " + readIn);

            // split data from readIn string and add into new array
            String [] g = readIn.split("\\" + delimiter);
            Guest gt = new Guest(g[0], g[1], g[2], g[3]);
            guests.add(gt);
        }
        Log.d("TAG_X", "guest check " + guests.get(0).getName());

        updateRecyclerView();
        reader.close();

    }

    @Override
    public void getGuest(Guest guest) {

    }
}
