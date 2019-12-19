package com.project.randomuser.apidatadetail

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Location(
        @SerializedName("street") @Embedded var street: Street,
        @SerializedName("city") var city: String?,
        @SerializedName("state") var state: String?
): Serializable