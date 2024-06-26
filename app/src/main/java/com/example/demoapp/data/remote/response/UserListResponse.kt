package com.example.demoapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("data")
    val userData: List<UserInfo>?,
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    val total: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    val support: Support?
)