package com.project.randomuser.apidatadetail
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Id(
        @SerializedName("name") var name: String?,
        @SerializedName("value") var value: String?
): Serializable