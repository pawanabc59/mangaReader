package com.example.mangareader;

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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class comicAdapter extends RecyclerView.Adapter<comicAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<comicModel> mList;

    comicAdapter(Context context, ArrayList<comicModel> list){
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.rv_comic_items, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        ImageView image = viewHolder.comic_image;
        TextView comic_name, comic_category;

        comic_name = viewHolder.comic_name;
        comic_category = viewHolder.comic_category;

//        image.setImageResource(mList.get(i).getImage());
        comic_name.setText(mList.get(i).getName());
        comic_category.setText(mList.get(i).getCategory());

        Picasso.get().load(mList.get(i).getImage()).into(image);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, comic_activity.class);

//                passing data to the comic activity
                intent.putExtra("Title", mList.get(i).getName());
                intent.putExtra("Description", mList.get(i).getCategory());
                intent.putExtra("Thumbnail", mList.get(i).getImage());
                intent.putExtra("Manga_id", mList.get(i).getManga_id());
                intent.putExtra("favourite", mList.get(i).getFavourite());

//                start the activity
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView comic_name, comic_category;
        ImageView comic_image;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            comic_name = itemView.findViewById(R.id.comic_name);
            comic_category = itemView.findViewById(R.id.comic_category);
            comic_image = itemView.findViewById(R.id.comic_image);

            cardView = itemView.findViewById(R.id.comic_cardview_list);
        }
    }
}
