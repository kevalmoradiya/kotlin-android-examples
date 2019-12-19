package com.project.randomuser.apidatadetail

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DOB(
    @SerializedName("date") var date: String?,
    @SerializedName("age") var age: Int
): Serializable