package com.fauzighozali.cermatitest.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.fauzighozali.cermatitest.R;
import com.fauzighozali.cermatitest.fragment.FragmentResult;
import com.fauzighozali.cermatitest.model.Items;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

//    private MainPresenter mainPresenter;
//    private MainAdapter mainAdapter;
//    private List<Items> users;
//    private LinearLayoutManager layoutManager;

    @InjectView(R.id.empty_view) RelativeLayout mEmptyView;
    @InjectView(R.id.toolbar) Toolbar mToolbar;

    private String queryParamForFragment;
    private List<Items> users = new ArrayList<>();
    private String querySearch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);

//        mainPresenter = new MainPresenter(this);
//        initView();
//        searchUser();
    }

    public void setQuerySearch(String querySearch) {
        this.querySearch = querySearch;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mEmptyView.setVisibility(View.GONE);
                users.clear();
                setQuerySearch(query);
                queryParamForFragment = query;
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_result, FragmentResult.newInstance(query),"MyFragment").commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

//    private void initView() {
//        layoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setHasFixedSize(true);
//    }

//    private void searchUser() {
//        mSearchView.setQueryHint("Search Github Users");
//        mSearchView.setIconifiedByDefault(false);
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(final String query) {
//                mainPresenter.getUserList(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//    }

//    @Override
//    public void userList(List<Items> items) {
//        if (users != null) {
//            users.clear();
//        }
//        users = items;
//        mainAdapter = new MainAdapter(users,this);
//        mRecyclerView.setAdapter(mainAdapter);
//        mainAdapter.notifyDataSetChanged();
//    }

}
