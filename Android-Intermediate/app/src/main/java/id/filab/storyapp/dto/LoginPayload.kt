package id.filab.storyapp.dto

import com.google.gson.annotations.SerializedName

data class LoginPayload(
    @field:SerializedName("email")
    var email: String = "",

    @field:SerializedName("password")
    var password: String = "",
)
