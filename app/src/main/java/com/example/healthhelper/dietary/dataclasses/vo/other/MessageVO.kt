package com.example.healthhelper.dietary.dataclasses.vo.other

import com.google.gson.annotations.SerializedName

data class MessageVO(
    @SerializedName("errorMessage")
    val errorMessage:String,

    @SerializedName("result")
    val result:String, // boolean that represents result or
)