package com.hrishikeshdarshan.ablertsonshrishiassignment.data.models

import com.google.gson.annotations.SerializedName

class AcromineResponse : ArrayList<AcromineResponseItem>()

data class AcromineResponseItem(
    @SerializedName("lfs")
    val longForms: List<Lf>,

    @SerializedName("sf")
    val shortForms: String
)

data class Lf(
    @SerializedName("freq")
    val frequency: Int,

    @SerializedName("lf")
    val longForm: String,

    val since: Int,

    @SerializedName("vars")
    val variations: List<Var>
)

data class Var(
    @SerializedName("freq")
    val frequency: Int,

    @SerializedName("lf")
    val longForm: String,

    val since: Int
)