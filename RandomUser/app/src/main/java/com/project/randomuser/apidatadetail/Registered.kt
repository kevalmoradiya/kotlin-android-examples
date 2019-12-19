package com.project.randomuser.apidatadetail

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Registered(
    @SerializedName("date")@ColumnInfo(name="rdate") var date: String?,
    @SerializedName("age")@ColumnInfo(name="rage")  var age: Int
): Serializable