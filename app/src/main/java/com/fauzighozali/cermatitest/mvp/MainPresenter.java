package com.fauzighozali.cermatitest.mvp;

import android.widget.Toast;

import com.fauzighozali.cermatitest.api.ApiClient;
import com.fauzighozali.cermatitest.api.ApiInterface;
import com.fauzighozali.cermatitest.model.AllUserModel;
import com.fauzighozali.cermatitest.model.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainView.GetUser {

    private MainView.InitView initView;
    public MainPresenter(MainView.InitView initView) {
        this.initView = initView;
    }

    @Override
    public void getUserList(final String keyword) {
        initView.showLoading();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Users> call = apiInterface.getUsers(keyword);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                initView.hideLoading();
                initView.userList(response.body().getItems());
                int totalCount = response.body().getTotalCount();

                if (!response.isSuccessful() || response.body().getItems() == null || totalCount == 0) {
                    initView.userListFailure("No Result for '" + keyword + "'", "Try Searching for Other Users");
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                initView.userListFailure("Error Loading for '" + keyword + "'", t.toString());
                initView.hideLoading();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getUserListWithPagination(final String keyword, int current_page) {
//        initView.showLoading();
//        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<AllUserModel> call = apiInterface.getUsersWithPagination(keyword, current_page, 15);
//        call.enqueue(new Callback<AllUserModel>() {
//            @Override
//            public void onResponse(Call<AllUserModel> call, Response<AllUserModel> response) {
//                initView.hideLoading();
//                initView.userList(response.body().getItems());
//                int totalCount = response.body().getTotalCount();
//
//                if (!response.isSuccessful() || response.body().getItems() == null || totalCount == 0) {
//                    initView.userListFailure("No Result for '" + keyword + "'", "Try Searching for Other Users");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AllUserModel> call, Throwable t) {
//                initView.userListFailure("Error Loading for '" + keyword + "'", t.toString());
//                initView.hideLoading();
//                t.printStackTrace();
//            }
//        });
    }
}
