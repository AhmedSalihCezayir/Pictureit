package com.example.pictureit.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pictureit.R;
import com.example.pictureit.models.Photo;

import java.util.List;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder> {

    //Constants
    private static final String TAG = "CardRecyclerViewAdapter";

    //Variables
    private List<Photo> mPhotos;
    private Context mContext;
    private UniversalImageLoader universalImageLoader;

    public CardRecyclerViewAdapter(Context context, List<Photo> photo) {
        mContext = context;
        mPhotos = photo;
        universalImageLoader = new UniversalImageLoader(mContext);
    }

    /**
     * Method for inflating the recycler view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_cardview, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Method for filling the recycler view
     *
     * @param holder   item
     * @param position position of the item in the recycler view
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        universalImageLoader.setImage(mPhotos.get(position).getImage_path(), holder.image, null, "");
        holder.text.setText(mPhotos.get(position).getDate_created());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: you clicked.");
                Toast.makeText(mContext, mPhotos.get(position).getDate_created(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @return The item count
     */
    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    /**
     * ViewHolder Inner class
     * It is used for filling the recycler view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        //Widgets
        ImageView image;
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textView);
            image = itemView.findViewById(R.id.imageView);
        }

    }

}
