package android.example.com.recipecooking;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<CookingStep> mListData;

    public ViewPagerAdapter(Context context, List<CookingStep> listDate) {
        mContext = context;
        mListData = listDate;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.step_detail, container, false);

        CookingStep currentStep = mListData.get(position);
        final TextView textView = view.findViewById(R.id.txtCookingStep);
        final ImageView imageView = view.findViewById(R.id.imgCookingStep);
        textView.setText(currentStep.getStep());
//        int resID = mContext.getResources().getIdentifier(currentStep.getImageURL(),
//                "drawable", mContext.getPackageName());
//        imageView.setImageResource(resID);

        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReferenceFromUrl("gs://recipecooking-3e9cd.appspot.com").
//                child(currentStep.getImageURL());
        StorageReference storageRef = storage.getReferenceFromUrl("gs://videourl-751c3.appspot.com").
                child(currentStep.getImageURL());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).placeholder(R.drawable.loadingplaceholder).into(imageView);
            }
        });

        container.addView(view);
        return view;
    }
}