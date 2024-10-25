package com.example.healthhelper.dietary.extension.longext

fun Long.toTimeAgo(anotherTime: Long): Int {
    val time = this
    val diff = (anotherTime - time) / 1000

    if(diff<0L){
        return -1
    }else if(diff==0L){
        return 0
    }else{
        return 1
    }
}