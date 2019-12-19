package com.fauzighozali.cermatitest.mvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import com.fauzighozali.cermatitest.R;
import com.fauzighozali.cermatitest.adapter.MainAdapter;
import com.fauzighozali.cermatitest.model.Items;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements MainView.InitView {

    private MainPresenter mainPresenter;
    private MainAdapter mainAdapter;
    private List<Items> users;

    @InjectView(R.id.progress_main) ProgressBar mProgressBar;
    @InjectView(R.id.text_view_error_title) TextView mErrorTitle;
    @InjectView(R.id.text_view_error_message) TextView mErrorMessage;
    @InjectView(R.id.empty_view) RelativeLayout mEmptyView;
    @InjectView(R.id.search_view_main) SearchView mSearchView;
    @InjectView(R.id.recycler_main) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mainPresenter = new MainPresenter(this);
        initView();
        searchUser();
    }

    private void initView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    private void searchUser() {
        mSearchView.setQueryHint("Search Github Users");
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainPresenter.getUserList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        errorView(View.INVISIBLE, "", "");
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void userList(List<Items> items) {
        if (users != null) {
            users.clear();
        }
        users = items;
        mainAdapter = new MainAdapter(users,this);
        mRecyclerView.setAdapter(mainAdapter);
        mainAdapter.notifyDataSetChanged();
    }

    @Override
    public void userListFailure(String errorMessage, String keyword) {
        errorView(View.VISIBLE,  errorMessage, keyword);
    }

    private void errorView(int visibility, String title, String message){
        mEmptyView.setVisibility(visibility);
        mErrorTitle.setText(title);
        mErrorMessage.setText(message);
    }
}
