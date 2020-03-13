package com.bb.reservationapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bb.reservationapp.R;
import com.bb.reservationapp.database.GuestDatabaseHelper;
import com.bb.reservationapp.model.Guest;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddGuestActivity extends AppCompatActivity {

        GuestDatabaseHelper databaseHelper;

        @BindView(R.id.gender_imageview)
        ImageView genderImageView;

        @BindView(R.id.name_edittext)
        EditText nameEditText;

        @BindView(R.id.checkdate_edittext)
        EditText dateEditText;

        @BindView(R.id.room_edittext)
        EditText roomEditText;

        @BindView(R.id.gender_select_radio_group)
        RadioGroup genderRadioGroup;

        private String gender = "M";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);
        ButterKnife.bind(this);

//        databaseHelper = new GuestDatabaseHelper(this, null, null, 0);
        databaseHelper = new GuestDatabaseHelper(this);
        genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.male_radio_button:
                    gender = "M";
                    genderImageView.setImageDrawable(getDrawable(R.drawable.icon_male));
                    break;
                case R.id.female_radio_button:
                    gender = "F";
                    genderImageView.setImageDrawable(getDrawable(R.drawable.icon_female));
                    break;
            }
        });
    }

    @OnClick(R.id.save_guest_button)
    public void saveGuest(View view){
            String name = nameEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            String room = roomEditText.getText().toString().trim();

            Guest newGuest = new Guest(name, gender, date, room);
            databaseHelper.saveGuest(newGuest);

            nameEditText.setText("");
            dateEditText.setText("");
            roomEditText.setText("");

            finish();
        }

}
