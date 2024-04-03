package com.falikiali.tmdbapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("success") val success: Boolean,
    @field:SerializedName("status_code") val statusCode: Int,
    @field:SerializedName("status_message") val statusMessage: String
)
