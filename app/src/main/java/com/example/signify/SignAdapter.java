package com.example.signify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

//import com.example.recycleview.R;
//
//import java.util.ArrayList;

public class SignAdapter extends RecyclerView.Adapter<SignAdapter.SignViewHold>  {

    ArrayList<SignHelper> signloc;
    final private ListItemClickListener SignClickListener;
    private Context mContext;

//    public SignAdapter(ArrayList<SignHelper>signloc, ListItemClickListener listener) {
//        this.signloc = signloc;
//        SignClickListener = listener;
//    }
    public SignAdapter(Context mContext,ArrayList<SignHelper>signloc,ListItemClickListener listener){
        this.mContext = mContext;
        this.signloc = signloc;
        SignClickListener = listener;
    }

    @NonNull

    @Override
    public SignViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sign_card, parent, false);
        return new SignViewHold(view);

    }
    @Override
    public void onBindViewHolder(@NonNull SignViewHold holder, int position) {


//        SignHelper signhelper = signloc.get(position);
//        holder.image.setImageResource(signhelper.getImageUrl());
//        holder.title.setText(signhelper.getTitle());

        /** Testing New Stuff **/
        holder.show_more_Btn.setTag(position);
        Glide.with(mContext).load(signloc.get(position).getImageUrl()).into(holder.image);
        holder.title.setText(signloc.get(position).getTitle());
        /** End Of Testing **/

        holder.show_more_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer cardPosition = (Integer) view.getTag();
                String signName = signloc.get(cardPosition).getTitle();
                String signImage = signloc.get(cardPosition).getImageUrl();
                String whatItMeans = signloc.get(cardPosition).getDescription1();
                String whatToDo = signloc.get(cardPosition).getDescription2();
                String severityValue = signloc.get(cardPosition).getSeverityValue();
                Intent intent = new Intent(mContext,SignInfo.class);
                Bundle bundle = new Bundle();
                bundle.putString("signName",signName);
                bundle.putString("signImage",signImage);
                bundle.putString("whatItMeans",whatItMeans);
                bundle.putString("whatToDo",whatToDo);
                bundle.putString("severityValue",severityValue);
                intent.putExtra("allSignsData",bundle);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return signloc.size();

    }

    public interface ListItemClickListener {
        void onsignListClick(int clickedItemIndex);
    }

    public class SignViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView image;
        TextView  title;
        Button show_more_Btn;
        RelativeLayout relativeLayout;


        public SignViewHold(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //hooks
            image = itemView.findViewById(R.id.sign_image);
            title = itemView.findViewById(R.id.s_title);
            show_more_Btn = itemView.findViewById(R.id.show_info);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            SignClickListener.onsignListClick(clickedPosition);
        }
    }

}
