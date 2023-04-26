package com.example.signify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        Glide.with(mContext).load(signloc.get(position).getImageUrl()).into(holder.image);
        holder.title.setText(signloc.get(position).getTitle());
        /** End Of Testing **/

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
        RelativeLayout relativeLayout;


        public SignViewHold(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //hooks
            image = itemView.findViewById(R.id.sign_image);
            title = itemView.findViewById(R.id.s_title);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            SignClickListener.onsignListClick(clickedPosition);
        }
    }

}
