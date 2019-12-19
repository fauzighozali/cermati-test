package com.fauzighozali.cermatitest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Users {

    @SerializedName("items")
    @Expose
    private List<Items> items;

    @SerializedName("total_count")
    @Expose
    private int totalCount;

    public List<Items> getItems() {
        return items;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
