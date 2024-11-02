package com.example.healthhelper.dietary.dataclasses.vo.other

import com.google.gson.annotations.SerializedName

// see [MessageVO.md](https://github.com/junshin4211/HealthHelper/blob/17e6aa1584bd43d06b8aedf668c643d24c13a24c/app/src/main/java/com/example/healthhelper/attachment/code/vo/messagevo/MessageVO.md)
data class MessageVO(
    @SerializedName("errorMessage")
    val errorMessage:String,

    @SerializedName("result")
    val result:String,

    @SerializedName("affectedRows")
    val affectedRows:Int,
)