package com.example.tazemeta.izlemelik;
import android.content.Context;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;



public class movieAdapter extends RecyclerView.Adapter<movieAdapter.ViewHolder> {

    private Context context;
    private List<movie> list;

    public movieAdapter(Context context, List<movie> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       final movie m = list.get(position);

        holder.textTitle.setText(m.getMtitle());
        holder.textRating.setText(String.valueOf(m.getmType()));
        holder.textYear.setText(String.valueOf(m.getmYear()));
        Glide.with(context)
                .load(m.getmPosterUrl())
                .transform(new CircleTransform(context))
                .into(holder.circle);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  itemValue    = m.getMtitle();
                Intent i;
                i= new Intent(context.getApplicationContext(),filmActivity.class);
                i.putExtra("movie",itemValue);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle, textRating, textYear;
        ConstraintLayout parentLayout;
        ImageView circle;

        public ViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.main_title);
            textRating = itemView.findViewById(R.id.main_rating);
            textYear = itemView.findViewById(R.id.main_year);
            parentLayout=itemView.findViewById(R.id.parent_layout);
            circle=itemView.findViewById(R.id.circleImage);

        }
    }

}