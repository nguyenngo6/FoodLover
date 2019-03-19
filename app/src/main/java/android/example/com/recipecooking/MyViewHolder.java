package android.example.com.recipecooking;

import android.example.com.recipecooking.R;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public TextView descriptionTextView;
    public ImageView coverImageView;
    CardView cardView;

    public MyViewHolder(View v) {
        super(v);
        titleTextView = v.findViewById(R.id.titleTextView);
        coverImageView = v.findViewById(R.id.coverImageView);
        cardView = v.findViewById(R.id.row_item_card);
        descriptionTextView = v.findViewById(R.id.descriptionTextView);
    }
}