package com.example.adminapp.HelpClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminapp.R;
import com.example.adminapp.ReviewPlaceData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.net.URI;


public class ReviewAdaptor extends FirebaseRecyclerAdapter<PlaceHelperClass,ReviewAdaptor.myviewholder> {
    Context context;
    public ReviewAdaptor(@NonNull FirebaseRecyclerOptions<PlaceHelperClass> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull PlaceHelperClass model) {

        holder.Placestitle.setText(model.getName());
        holder.Placesdesc.setText(model.getDescription());
        Glide.with(holder.Placesimage.getContext()).load(model.getImageUrl()).into(holder.Placesimage);


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ReviewPlaceData.class);
            intent.putExtra("name",model.getName());
            intent.putExtra("des",model.getDescription());
            intent.putExtra("image",model.getImageUrl());
            intent.putExtra("category",model.getCategory());
            intent.putExtra("latitude",model.getLatitude());
            intent.putExtra("longitude",model.getLongitude());
            intent.putExtra("userid",model.getUser());
            intent.putExtra("year",model.getYear());
            intent.putExtra("month",model.getMonth());
            intent.putExtra("day",model.getDay());
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {
        ImageView Placesimage;
        TextView Placestitle, Placesdesc;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            Placesimage = (ImageView) itemView.findViewById(R.id.list_image);
            Placestitle = (TextView) itemView.findViewById(R.id.list_title);
            Placesdesc = (TextView) itemView.findViewById(R.id.list_description);
        }
    }
}
