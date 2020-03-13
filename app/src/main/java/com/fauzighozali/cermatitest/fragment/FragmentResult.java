package com.fauzighozali.cermatitest.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzighozali.cermatitest.Pagination;
import com.fauzighozali.cermatitest.R;
import com.fauzighozali.cermatitest.adapter.MainAdapter;
import com.fauzighozali.cermatitest.api.ApiClient;
import com.fauzighozali.cermatitest.api.ApiInterface;
import com.fauzighozali.cermatitest.model.AllUserModel;
import com.fauzighozali.cermatitest.model.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentResult extends Fragment {

    @InjectView(R.id.recycler_main) RecyclerView mRecyclerView;
    @InjectView(R.id.progress_main) ProgressBar mProgressBar;
    @InjectView(R.id.empty_view) RelativeLayout mEmptyView;
    @InjectView(R.id.text_view_error_title) TextView mErrorTitle;
    @InjectView(R.id.text_view_error_message) TextView mErrorMessage;

    private static String ARG_PARAM;
    private String mQueryParam;
    private MainAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Items> itemsList = new ArrayList<>();

    public FragmentResult() {}

    public static FragmentResult newInstance(String param) {
        FragmentResult fragment = new FragmentResult();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_result, container, false);
        ButterKnife.inject(this, view);

        if (getArguments() != null) {
            mQueryParam = getArguments().getString(ARG_PARAM);
        }

        mAdapter = new MainAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), itemsList, this);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new Pagination(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                getData(current_page);
            }
        });

        getData(1);

        return view;
    }

    private void getData(int current_page) {
        showLoading();
        mEmptyView.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "page:" + current_page, Toast.LENGTH_SHORT).show();
        ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);
        service.getUsersWithPagination(mQueryParam,current_page,15)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllUserModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AllUserModel allUserModel) {
                        itemsList.addAll(allUserModel.getItems());
                        int totalCount = allUserModel.getTotalCount();

                        if (allUserModel.getItems() == null || totalCount == 0) {
                            userListFailure("No Result for '" + mQueryParam + "'", "Try Searching for Other Users");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void userListFailure(String errorMessage, String keyword) {
        errorView(View.VISIBLE,  errorMessage, keyword);
    }

    private void errorView(int visibility, String title, String message){
        mEmptyView.setVisibility(visibility);
        mErrorTitle.setText(title);
        mErrorMessage.setText(message);
    }

    private void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }
}
