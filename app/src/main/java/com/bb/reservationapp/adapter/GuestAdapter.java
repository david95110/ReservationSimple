package com.bb.reservationapp.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.reservationapp.R;
import com.bb.reservationapp.model.Guest;
import com.bb.reservationapp.view.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder> {

    private List<Guest> guestList;
    private Reservation reservation;

    public GuestAdapter(List<Guest>guestList, Reservation reservation){
        this.guestList = guestList;
        this.reservation = reservation;
    }

    public interface Reservation{
        void getGuest(Guest guest);
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guest_item_layout, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, final int position) {
        Log.d("TAG_X", "onBindViewHolder");
        holder.itemView.setOnClickListener((View view)-> {
            reservation.getGuest(guestList.get(position));
        });

        holder.nameTextView.setText(guestList.get(position).getName());
        holder.dateTextView.setText(guestList.get(position).getCheckDate());
        holder.roomTextView.setText(guestList.get(position).getRoom());
        Log.d("TAG_X", "onBindViewHolder0");
        Drawable drawable = null;

        switch (guestList.get(position).getGender()){
            case "M":
                drawable = holder.itemView.getContext().getDrawable(R.drawable.icon_male);
                break;
            case "F":
                drawable = holder.itemView.getContext().getDrawable(R.drawable.icon_female);
                break;
        }

        Glide.with(holder.itemView.getContext())
            .load(drawable).into(holder.genderImageView);
        Log.d("TAG_X", "onBindViewHolder2");
    }

    @Override
    public int getItemCount() {
        return  guestList.size();
    }

    public class GuestViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.name_textview)
        TextView nameTextView;

        @BindView(R.id.date_textview)
        TextView dateTextView;

        @BindView(R.id.card_image)
        ImageView genderImageView;

        @BindView(R.id.room_textview)
        TextView roomTextView;

        public GuestViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

