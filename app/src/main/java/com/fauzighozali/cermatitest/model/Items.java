package com.fauzighozali.cermatitest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Items {

    @SerializedName("login")
    @Expose
    private String name;
    @SerializedName("avatar_url")
    @Expose
    private String avatar;

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

}
