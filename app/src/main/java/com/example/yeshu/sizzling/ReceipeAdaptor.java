package com.example.yeshu.sizzling;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.yeshu.sizzling.JsonData.ReceipeJsonDate;

import java.util.ArrayList;

/*
 * Created by Yeshu on 07-06-2018.
 */

public class ReceipeAdaptor extends RecyclerView.Adapter<ReceipeAdaptor.MyReceipeViewHolder> {

    private Context c;
    private ArrayList<ReceipeJsonDate> receipeJsonDates;

    ReceipeAdaptor(Context c, ArrayList<ReceipeJsonDate> receipeJsonDates) {
        this.c = c;
        this.receipeJsonDates = receipeJsonDates;
    }

    @NonNull
    @Override
    public ReceipeAdaptor.MyReceipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.receipe_list,parent,false);
        return new MyReceipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceipeAdaptor.MyReceipeViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(c).load(R.drawable.cake_image).into(holder.imageView);
        final ReceipeJsonDate receipeJsonDate=receipeJsonDates.get(position);
        holder.textView.setText(receipeJsonDate.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(c,ItemListActivity.class);
                intent.putExtra("results",receipeJsonDates.get(position));
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return receipeJsonDates.size();
    }

    class MyReceipeViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        CardView cardView;
        MyReceipeViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.receipeName);
            imageView=itemView.findViewById(R.id.cake_receipe_image);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
