package com.yuqirong.searchbar;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private SearchBarView searchbarview;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        searchbarview = (SearchBarView) findViewById(R.id.searchbarview);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        appBarLayout.addOnOffsetChangedListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(new MyAdapter());
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float alpha = Math.abs((float) verticalOffset / appBarLayout.getTotalScrollRange());
        toolbar.setAlpha(alpha);
        if (alpha >= 1) {
            searchbarview.startOpen();
        } else {
            searchbarview.startClose();
        }
    }


    private static class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_HEADER = 1;
        private static final int TYPE_NORMAL = 1 << 2;

        @Override
        public int getItemViewType(int position) {
            return TYPE_NORMAL;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView;
            if (viewType == TYPE_HEADER) {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
                return new HeaderViewHolder(itemView);
            } else if (viewType == TYPE_NORMAL) {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal, parent, false);
                return new NormalViewHolder(itemView);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class HeaderViewHolder extends RecyclerView.ViewHolder {

            HeaderViewHolder(View itemView) {
                super(itemView);
            }

        }

        class NormalViewHolder extends RecyclerView.ViewHolder {

            NormalViewHolder(View itemView) {
                super(itemView);
            }

        }

    }

}
