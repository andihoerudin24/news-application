package com.example.news_application.baseview.model

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("data")
    val `data`: Data?
) : BaseModel() {
    data class Data(
        @SerializedName("error")
        val error: Error? = null
    ) {
        data class Error(
            @SerializedName("confirm_password")
            val confirmPassword: String? = null,
            @SerializedName("email_error")
            val emailError: String? = null,
            @SerializedName("username_error")
            val usernameError: String? = null,
            @SerializedName("number_phone_error")
            val numberPhoneError: String? = null,
            @SerializedName("get_verified_user_error")
            val getVerifiedUserError: String? = null,
            @SerializedName("password_error")
            val passwordWrong: String? = null
        )
    }
}
