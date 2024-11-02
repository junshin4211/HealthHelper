package com.example.healthhelper.community


import java.sql.Timestamp

class Post(
    var userIcon: Int,
    var userName: String,
    var title: String,
    var content: String,
    var likesAmount: Int,
    var commentAmount: Int,
    var img: Int,
    var postTime: String
    )