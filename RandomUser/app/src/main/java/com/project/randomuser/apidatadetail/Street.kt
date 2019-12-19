package com.project.randomuser.apidatadetail

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Street(
    @SerializedName("number") var number: Int,
    @SerializedName("name")@ColumnInfo(name = "streetname") var name: String?
): Serializable