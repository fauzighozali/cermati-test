package com.fauzighozali.cermatitest.api;

import com.fauzighozali.cermatitest.model.Users;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search/users")
    Call<Users> getUsers (@Query("q") String keyword);

}
