package com.fauzighozali.cermatitest.mvp;

import com.fauzighozali.cermatitest.model.Items;

import java.util.List;

public interface MainView {

    interface InitView {
        void showLoading();
        void hideLoading();
        void userList(List<Items> users);
        void userListFailure(String errorMessage, String keyword);
    }

    interface GetUser {
        void getUserList(String keyword);
    }

}
