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
import com.example.adminapp.FeaturedPlaceData;
import com.example.adminapp.R;
import com.example.adminapp.ReviewPlaceData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class FeatureAdaptor extends FirebaseRecyclerAdapter<PlaceHelperClass,FeatureAdaptor.myviewholder> {
    Context context;
    public FeatureAdaptor(@NonNull FirebaseRecyclerOptions<PlaceHelperClass> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public FeatureAdaptor.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card, parent, false);
        return new FeatureAdaptor.myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull PlaceHelperClass model) {
        holder.Placestitle.setText(model.getName());
        holder.Placesdesc.setText(model.getDescription());
        Glide.with(holder.Placesimage.getContext()).load(model.getImageUrl()).into(holder.Placesimage);


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, FeaturedPlaceData.class);
            intent.putExtra("name",model.getName());
            intent.putExtra("des",model.getDescription());
            intent.putExtra("image",model.getImageUrl());
            intent.putExtra("category",model.getCategory());
            intent.putExtra("featured",model.isFeatured());
            context.startActivity(intent);
        });
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
