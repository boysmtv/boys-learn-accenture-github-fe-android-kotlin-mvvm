package com.boys.assets.accenture.activity.fragment.popular.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PopularModel (

        @SerializedName("total_count")
        var total_count : Int?,

        @SerializedName("incomplete_results")
        var incomplete_results : Boolean?,

        @SerializedName("items")
        var items : List<Users>?,

)

@Entity(tableName = "users")
class Users {

        @PrimaryKey
        @SerializedName("id")
        var id: Int? = null

        @SerializedName("login")
        var login: String? = null

        @SerializedName("node_id")
        var node_id: String? = null

        @SerializedName("avatar_url")
        var avatar_url: String? = null

        @SerializedName("gravatar_id")
        var gravatar_id: String? = null

        @SerializedName("url")
        var url: String? = null

        @SerializedName("html_url")
        var html_url: String? = null

        @SerializedName("followers_url")
        var followers_url: String? = null

        @SerializedName("following_url")
        var following_url: String? = null

        @SerializedName("gists_url")
        var gists_url: String? = null

        @SerializedName("starred_url")
        var starred_url: String? = null

        @SerializedName("subscriptions_url")
        var subscriptions_url: String? = null

        @SerializedName("organizations_url")
        var organizations_url: String? = null

        @SerializedName("repos_url")
        var repos_url: String? = null

        @SerializedName("events_url")
        var events_url: String? = null

        @SerializedName("received_events_url")
        var received_events_url: String? = null

        @SerializedName("type")
        var type: String? = null

        @SerializedName("site_admin")
        var site_admin: Boolean? = null

}