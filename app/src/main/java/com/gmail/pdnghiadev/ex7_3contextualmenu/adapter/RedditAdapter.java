package com.gmail.pdnghiadev.ex7_3contextualmenu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.gmail.pdnghiadev.ex7_3contextualmenu.R;
import com.gmail.pdnghiadev.ex7_3contextualmenu.database.DBAdapter;
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
    private DBAdapter dbAdapter;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public RedditAdapter(Context context, List<Children> mChildren, int isSticky, int isNotSticky) {
        this.listChildrend = mChildren;
        this.isSticky = isSticky;
        this.isNotSticky = isNotSticky;
        dbAdapter = new DBAdapter(context);
        dbAdapter.open();
    }

    @Override
    public int getItemViewType(int position) {
        return listChildrend.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        DateConverter dateConverter = new DateConverter();
        final Children children = listChildrend.get(position);

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
            ((RedditViewHolder) holder).mBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    children.setBookmark(!children.isBookmark());
                    if (children.isBookmark()) {
                        ((RedditViewHolder) holder).mBookmark.setBackgroundResource(R.drawable.ic_bookmark_star_selected);
                        dbAdapter.insertChildren(children);
                    } else {
                        ((RedditViewHolder) holder).mBookmark.setBackgroundResource(R.drawable.ic_bookmark_star_unselected);
                        dbAdapter.deleteChildren(children.getId());
                    }

                }
            });
        }else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }


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
        public Button mBookmark;

        public RedditViewHolder(View itemView) {
            super(itemView);

            this.mScore = (TextView) itemView.findViewById(R.id.txt_score);
            this.mAuthor = (TextView) itemView.findViewById(R.id.txt_author);
            this.mSubreddit = (TextView) itemView.findViewById(R.id.txt_subreddit);
            this.mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            this.mCountComment = (TextView) itemView.findViewById(R.id.txt_count_comment);
            this.mBookmark = (Button) itemView.findViewById(R.id.btn_bookmark);
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
