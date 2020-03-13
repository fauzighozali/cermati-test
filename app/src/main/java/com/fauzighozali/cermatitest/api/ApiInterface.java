package com.fauzighozali.cermatitest.api;

import com.fauzighozali.cermatitest.model.AllUserModel;
import com.fauzighozali.cermatitest.model.Users;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search/users")
    Call<Users> getUsers (@Query("q") String keyword);

//    @GET("search/users")
//    Call<AllUserModel> getUsersWithPagination(@Query("q") String q, @Query("page")int page, @Query("per_page")int perpage);

    @GET("search/users")
    Observable<AllUserModel> getUsersWithPagination(@Query("q") String q, @Query("page")int page, @Query("per_page")int perpage);

}
