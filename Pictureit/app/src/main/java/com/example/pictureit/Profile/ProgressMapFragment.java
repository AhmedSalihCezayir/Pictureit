package com.example.pictureit.Profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pictureit.R;
import com.example.pictureit.Utils.CardRecyclerViewAdapter;
import com.example.pictureit.Utils.StringManipulation;
import com.example.pictureit.models.Photo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProgressMapFragment extends Fragment {

    //Constants
    private static final String TAG = "ProgressMapFragment";

    //Firebase
    private DatabaseReference reference;

    //Variables
    private List<Photo> photoList;
    private Context mContext;
    private CardRecyclerViewAdapter adapter;

    //Widgets
    private RecyclerView recyclerView;
    private ImageView backArrow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_map, container, false);

        photoList = new ArrayList<>();
        mContext = getContext();
        reference = FirebaseDatabase.getInstance().getReference();
        setPhotos();
        backArrow = view.findViewById(R.id.backArrow);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new CardRecyclerViewAdapter(mContext, photoList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating back to profile activity");
                backToActivity();
            }
        });

        return view;
    }

    /**
     * Method that setups the photos of the progress map. It loops through the database's all_photos node
     */
    private void setPhotos() {
        reference.child(getString(R.string.dbname_all_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        photoList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Photo photo = new Photo();
                            try {
                                photo.setUser_id(ds.child(getActivity().getString(R.string.field_user_id)).getValue().toString());
                                photo.setDate_created(StringManipulation.timeStampConverter(ds.child(getActivity().getString(R.string.field_date_created)).getValue().toString()));
                                photo.setImage_path(ds.child(getActivity().getString(R.string.field_image_path)).getValue().toString());
                                photo.setImage_id(ds.child(getActivity().getString(R.string.field_user_id)).getValue().toString());
                                photo.setTag1(ds.child(getActivity().getString(R.string.field_tag1)).getValue().toString());
                                photo.setTag2(ds.child(getActivity().getString(R.string.field_tag2)).getValue().toString());

                                photoList.add(photo);
                            } catch (NullPointerException e) {
                                Log.d(TAG, "onDataChange: NullPointerException: " + e.getMessage());
                            }
                        }
                        recyclerView.setAdapter(adapter);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled: cancelled.");
                    }
                });
    }

    private void backToActivity() {
        getFragmentManager().popBackStack();
    }
}
