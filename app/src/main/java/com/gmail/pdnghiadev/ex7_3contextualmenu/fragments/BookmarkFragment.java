package com.gmail.pdnghiadev.ex7_3contextualmenu.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.pdnghiadev.ex7_3contextualmenu.R;
import com.gmail.pdnghiadev.ex7_3contextualmenu.adapter.BookmarkAdapter;
import com.gmail.pdnghiadev.ex7_3contextualmenu.database.DBAdapter;
import com.gmail.pdnghiadev.ex7_3contextualmenu.model.Children;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PDNghiaDev on 12/26/2015.
 */
public class BookmarkFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private DBAdapter dbAdapter;
    private List<Children> mList = new ArrayList<>();
    private BookmarkAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new DBAdapter(getActivity());
        dbAdapter.open();
        mList = dbAdapter.getAllBookmark();
        dbAdapter.close();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        loadComponents(view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mGridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setHasFixedSize(true);
        int ori = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        if (ori == Surface.ROTATION_0 || ori == Surface.ROTATION_180) {
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
        } else if (ori == Surface.ROTATION_90) {
            mRecyclerView.setLayoutManager(mGridLayoutManager);
        }

        mAdapter = new BookmarkAdapter(mList, getResources().getColor(R.color.colorStickyPost), getResources().getColor(R.color.colorTitle));
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void loadComponents(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.bookmark_list);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setLayoutManager(mGridLayoutManager);
        }
    }

}
