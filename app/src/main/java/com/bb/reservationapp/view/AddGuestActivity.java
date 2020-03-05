package com.bb.reservationapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bb.reservationapp.R;
import com.bb.reservationapp.model.Guest;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddGuestActivity extends AppCompatActivity {

        public static final String GUEST_KEY = "guest.key";

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

        private String gender = "F";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);
        ButterKnife.bind(this);

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
        if (checkedFields()) {
            String name = nameEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            String room = roomEditText.getText().toString().trim();

            Guest guest = new Guest(name, gender, date, room);

            Intent guestIntent = new Intent();
            guestIntent.putExtra(GUEST_KEY, guest);

            setResult(MainActivity.REQUEST_CODE, guestIntent);
            finish();
        }
    }

    private boolean checkedFields() {

        if (nameEditText.getText().toString().trim().length() == 0 ||
                dateEditText.getText().toString().trim().length() == 0 ||
                roomEditText.getText().toString().trim().length() == 0
        ) {
            Toast.makeText(this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;

    }
}
