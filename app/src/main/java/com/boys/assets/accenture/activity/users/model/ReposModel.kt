package com.boys.assets.accenture.activity.users.model

import com.google.gson.annotations.SerializedName

data class ReposModel (

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("node_id")
    val node_id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("full_name")
    val full_name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("updated_at")
    val updated_at: String? = null,

    @SerializedName("watchers")
    val watchers: Int? = null,

    @SerializedName("owner")
    val owner: OwnerModel? = null

)

data class OwnerModel (

    @SerializedName("avatar_url")
    val avatar_url: String? = null

)
