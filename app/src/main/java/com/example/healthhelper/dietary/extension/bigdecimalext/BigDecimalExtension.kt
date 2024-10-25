package com.example.healthhelper.dietary.extension.bigdecimalext

import java.math.BigDecimal

fun List<BigDecimal>.sum(): BigDecimal {
    var total = BigDecimal.ZERO
    this.forEach{
        total = total.add(it)
    }
    return total
}