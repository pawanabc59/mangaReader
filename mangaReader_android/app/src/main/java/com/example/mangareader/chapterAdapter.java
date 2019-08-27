package com.example.mangareader;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class chapterAdapter extends RecyclerView.Adapter<chapterAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<chapterModel> mList;

    public chapterAdapter(Context mContext, ArrayList<chapterModel> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.rv_chapter_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        TextView name = viewHolder.chapter_name;
        final String chapter_path;

        name.setText(mList.get(i).getChapter_name());
        chapter_path = mList.get(i).getChapter_path();

        viewHolder.chapter_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String text = "One Piece "+(i+1);
                String chapter_url = chapter_path;
                Intent intent = new Intent(mContext, PDFViewer.class);
                intent.putExtra("Chapter_Name", mList.get(i).getChapter_name());
                intent.putExtra("Chapter_path", chapter_url);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView chapter_name;
        CardView chapter_list_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chapter_name = itemView.findViewById(R.id.chapter_name);
            chapter_list_item = itemView.findViewById(R.id.chapter_list_id);
        }
    }

}
