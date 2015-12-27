package com.gmail.pdnghiadev.ex7_3contextualmenu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.gmail.pdnghiadev.ex7_3contextualmenu.R;
import com.gmail.pdnghiadev.ex7_3contextualmenu.database.DBAdapter;
import com.gmail.pdnghiadev.ex7_3contextualmenu.model.Children;
import com.gmail.pdnghiadev.ex7_3contextualmenu.ultils.DateConverter;

import java.util.List;

/**
 * Created by PDNghiaDev on 12/27/2015.
 */
public class BookmarkAdapter extends RecyclerView.Adapter{
    private List<Children> listChildrend;
    private int isSticky;
    private int isNotSticky;
    private DBAdapter dbAdapter;

    public BookmarkAdapter(Context context, List<Children> listChildrend, int isSticky, int isNotSticky) {
        this.listChildrend = listChildrend;
        this.isSticky = isSticky;
        this.isNotSticky = isNotSticky;
        dbAdapter = new DBAdapter(context);
        dbAdapter.open();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DateConverter dateConverter = new DateConverter();
        final Children children = listChildrend.get(position);

        if (holder instanceof BookmarkViewHolder) {
            ((BookmarkViewHolder) holder).mTitle.setText(String.valueOf(children.getScore()));
            ((BookmarkViewHolder) holder).mAuthor.setText(children.getAuthor());
            ((BookmarkViewHolder) holder).mSubreddit.setText(children.getSubreddit());
            if (children.isStickyPost()) {
                ((BookmarkViewHolder) holder).mTitle.setTextColor(isSticky);
            } else {
                ((BookmarkViewHolder) holder).mTitle.setTextColor(isNotSticky);
            }
            ((BookmarkViewHolder) holder).mTitle.setText(children.getTitle());
            String comment = String.valueOf(children.getCommentCount())
                    + " Comments • reddit • "
                    + dateConverter.displayTime(children.getCreateUTC());
            ((BookmarkViewHolder) holder).mCountComment.setText(comment);
//            ((BookmarkViewHolder) holder).mBookmark.setBackgroundResource(R.drawable.ic_bookmark_star_selected);
            ((BookmarkViewHolder) holder).mOverflowIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.inflate(R.menu.context_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.delete_file) {
                                dbAdapter.deleteChildren(children.getId());
                                listChildrend.remove(position);
                                notifyDataSetChanged();
                            }

                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return listChildrend.size();
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder{
        public TextView mScore, mAuthor, mSubreddit, mTitle, mCountComment;
//        public Button mBookmark;
        public ImageView mOverflowIcon;

        public BookmarkViewHolder(View itemView) {
            super(itemView);

            this.mScore = (TextView) itemView.findViewById(R.id.txt_score);
            this.mAuthor = (TextView) itemView.findViewById(R.id.txt_author);
            this.mSubreddit = (TextView) itemView.findViewById(R.id.txt_subreddit);
            this.mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            this.mCountComment = (TextView) itemView.findViewById(R.id.txt_count_comment);
//            this.mBookmark = (Button) itemView.findViewById(R.id.btn_bookmark);
            this.mOverflowIcon = (ImageView) itemView.findViewById(R.id.context_menu);
        }

    }
}
