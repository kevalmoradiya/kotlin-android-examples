package com.project.randomuser.networking


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.project.randomuser.apidatadetail.*
import java.io.Serializable
@Entity
data class UserDetail (
    @SerializedName("gender") var gender: String?,
    @SerializedName("name")@Embedded var name: Name,
    @SerializedName("location")@Embedded var location: Location,
    @PrimaryKey @SerializedName("email") var email: String,
    @SerializedName("login")@Embedded var login: Login,
    @SerializedName("dob")@Embedded var dob: DOB,
    @SerializedName("registered")@Embedded var registered: Registered,
    @SerializedName("phone") var phone: String?,
    @SerializedName("cell") var cell: String?,
    @SerializedName("id")@Embedded var id: Id,
    @SerializedName("picture")@Embedded var picture: Picture,
    @SerializedName("nat") var nat: String?



):Serializable
