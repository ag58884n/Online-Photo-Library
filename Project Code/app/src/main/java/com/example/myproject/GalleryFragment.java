package com.example.myproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment implements View.OnClickListener, RecyclerViewAdapter.OnItemClickListener {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;
    List<Image> list = new ArrayList<>();
    FirebaseStorage mStorage;
    ValueEventListener mDBListener;


    String userId;
    Button search;
    private EditText tags;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search = view.findViewById(R.id.button4);
        tags = view.findViewById(R.id.editText15);
        search.setOnClickListener(this);

        displayGallery();
    }

    private void displayGallery() {
        recyclerView = getActivity().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Images From Firebase.");
        progressDialog.show();

        userId = ((Display) getActivity()).getUserId();
        adapter = new RecyclerViewAdapter(getActivity().getApplicationContext(), list);


        recyclerView.setAdapter(adapter);
        ((RecyclerViewAdapter) adapter).setOnItemClickListener(GalleryFragment.this);
        mStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("images");

        mDBListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Image imageUploadInfo = postSnapshot.getValue(Image.class);
                    imageUploadInfo.setImageId(postSnapshot.getKey());
                    if (imageUploadInfo.getUserId().equals(userId) && imageUploadInfo.getTags().contains(tags.getText().toString())) {
                        list.add(imageUploadInfo);
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view == search) {
            displayGallery();
        }
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Image selectedItem = list.get(position);

        final String selectedKey = selectedItem.getImageId();
        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(selectedKey).removeValue();
                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mDBListener);
    }
}