package com.gmail.pdnghiadev.ex7_3contextualmenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.gmail.pdnghiadev.ex7_3contextualmenu.R;
import com.gmail.pdnghiadev.ex7_3contextualmenu.model.Children;
import com.gmail.pdnghiadev.ex7_3contextualmenu.ultils.DateConverter;

import java.util.List;


/**
 * Created by PDNghiaDev on 11/2/2015.
 * Class Adapter
 */
public class RedditAdapter extends RecyclerView.Adapter {
    private List<Children> listChildrend;
    private int isSticky;
    private int isNotSticky;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public RedditAdapter(List<Children> mChildren, int isSticky, int isNotSticky) {
        this.listChildrend = mChildren;
        this.isSticky = isSticky;
        this.isNotSticky = isNotSticky;
    }

    @Override
    public int getItemViewType(int position) {
        return listChildrend.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


//    @Override
//    public RedditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
//
//        return new RedditViewHolder(v);
//    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        // Return a new holder instance
        if (viewType == VIEW_ITEM){
            // Inflate the custom layout
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);

            vh = new RedditViewHolder(v);
        }else {
            // Inflate the custom layout
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DateConverter dateConverter = new DateConverter();
        Children children = listChildrend.get(position);

        if (holder instanceof RedditViewHolder){
            ((RedditViewHolder) holder).mScore.setText(String.valueOf(children.getScore()));
            ((RedditViewHolder) holder).mAuthor.setText(children.getAuthor());
            ((RedditViewHolder) holder).mSubreddit.setText(children.getSubreddit());
            if (children.isStickyPost()){
                ((RedditViewHolder) holder).mTitle.setTextColor(isSticky);
            }else {
                ((RedditViewHolder) holder).mTitle.setTextColor(isNotSticky);
            }
            ((RedditViewHolder) holder).mTitle.setText(children.getTitle());
            String comment = String.valueOf(children.getCommentCount())
                    + " Comments • reddit • "
                    + dateConverter.displayTime(children.getCreateUTC());
            ((RedditViewHolder) holder).mCountComment.setText(comment);
        }else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

//    @Override
//    public void onBindViewHolder(RedditViewHolder holder, int position) {
//        DateConverter dateConverter = new DateConverter();
//        Children children = listChildrend.get(position);
//
//
//        holder.mScore.setText(String.valueOf(children.getScore()));
//        holder.mAuthor.setText(children.getAuthor());
//        holder.mSubreddit.setText(children.getSubreddit());
//        if (children.isStickyPost()) {
//            holder.mTitle.setTextColor(isSticky);
//        } else {
//            holder.mTitle.setTextColor(isNotSticky);
//        }
//        holder.mTitle.setText(children.getTitle());
//        String comment = String.valueOf(children.getCommentCount())
//                + " Comments • reddit • "
//                + dateConverter.displayTime(children.getCreateUTC());
//        holder.mCountComment.setText(comment);
//    }

    public void clearAdapter() {
        listChildrend.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (listChildrend != null ? listChildrend.size() : 0);
    }

    public static class RedditViewHolder extends RecyclerView.ViewHolder {
        public TextView mScore, mAuthor, mSubreddit, mTitle, mCountComment;

        public RedditViewHolder(View itemView) {
            super(itemView);

            this.mScore = (TextView) itemView.findViewById(R.id.txt_score);
            this.mAuthor = (TextView) itemView.findViewById(R.id.txt_author);
            this.mSubreddit = (TextView) itemView.findViewById(R.id.txt_subreddit);
            this.mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            this.mCountComment = (TextView) itemView.findViewById(R.id.txt_count_comment);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View view) {
            super(view);

            this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }

}
