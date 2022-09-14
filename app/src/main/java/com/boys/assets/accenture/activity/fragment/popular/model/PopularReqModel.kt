package com.boys.assets.accenture.activity.fragment.popular.model

import com.google.gson.annotations.SerializedName

data class PopularReqModel (

        @SerializedName("q")
        val q : String?,

        @SerializedName("sort")
        val sort : String?,

        @SerializedName("per_page")
        val per_page : Int?,

        @SerializedName("page")
        val page : Int?,

)
