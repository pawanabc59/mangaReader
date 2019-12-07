package com.example.mangareader.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mangareader.Models.BookModel;
import com.example.mangareader.R;
import com.example.mangareader.SingleBookActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {
    private Context mContext;
    private List<BookModel> mData;

    public BookAdapter(Context mContext, List<BookModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public BookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_comic, null);

        return new BookAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv_comic_title.setText(mData.get(i).getTitle());
//        myViewHolder.img_comic_thumbnail.setImageResource(Integer.parseInt(mData.get(i).getThumbnail()));
        Picasso.get().load(mData.get(i).getThumbnail()).into(myViewHolder.img_comic_thumbnail);

        String favourite = mData.get(i).getFavourite();

//        final String finalFavourite = favourite;
//        System.out.println("Recycler view adapter favourite value : "+mData.get(i).getFavourite());

        if (favourite.equals("TRUE")){
            myViewHolder.btnShowAddFavourite.setVisibility(View.GONE);
            myViewHolder.btnShowRemoveFavourite.setVisibility(View.VISIBLE);
        }
        else{
            myViewHolder.btnShowRemoveFavourite.setVisibility(View.GONE);
            myViewHolder.btnShowAddFavourite.setVisibility(View.VISIBLE);
        }

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, SingleBookActivity.class);

//                passing data to the comic activity
                intent.putExtra("Title", mData.get(i).getTitle());
                intent.putExtra("Description", mData.get(i).getDescription());
                intent.putExtra("Thumbnail", mData.get(i).getThumbnail());
                intent.putExtra("book_id", mData.get(i).getBook_id());
                intent.putExtra("favourite", mData.get(i).getFavourite());
                intent.putExtra("book_path", mData.get(i).getBook_path());


//                start the activity
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_comic_title;
        ImageView img_comic_thumbnail;
        CardView cardView;
        Button btnShowAddFavourite, btnShowRemoveFavourite;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_comic_title = (TextView) itemView.findViewById(R.id.comic_title_id);
            img_comic_thumbnail = (ImageView) itemView.findViewById(R.id.comic_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            btnShowAddFavourite = itemView.findViewById(R.id.btnShow_add_favourite);
            btnShowRemoveFavourite = itemView.findViewById(R.id.btnShow_remove_favourite);
        }
    }
}
