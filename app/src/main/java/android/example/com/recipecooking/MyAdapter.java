package android.example.com.recipecooking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//http://square.github.io/picasso/
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Recipe> list;
    private Context context;
    List<CardView>cardViewList = new ArrayList<>();

    public MyAdapter(Context context, List<Recipe> Data) {
        list = Data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_items, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (!cardViewList.contains(holder.cardView)) {
            cardViewList.add(holder.cardView);
        }

        final Recipe item = list.get(position);
        holder.titleTextView.setText(item.getName());
        holder.descriptionTextView.setText(item.getDescription());

//        int resID = context.getResources().getIdentifier(item.getImageURL(),
//                "drawable", context.getPackageName());
//        holder.coverImageView.setImageResource(resID);

        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReferenceFromUrl("gs://recipecooking-3e9cd.appspot.com").child(item.getImageURL());
                StorageReference storageRef = storage.getReferenceFromUrl("gs://videourl-751c3.appspot.com").child(item.getImageURL());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Handle whatever you're going to do with the URL here
                Picasso.get().load(uri).placeholder(R.drawable.loadingplaceholder).into(holder.coverImageView);

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //All card color is set to colorDefault
                for(CardView cardView : cardViewList){
                    cardView.setCardBackgroundColor(Color.parseColor("#567845"));
                }
                //The selected card is set to colorSelected
                holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));

                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recipeName", item.getName());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}