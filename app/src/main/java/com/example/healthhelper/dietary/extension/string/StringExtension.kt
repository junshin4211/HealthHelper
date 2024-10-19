package com.example.healthhelper.dietary.extension.string

import com.example.healthhelper.dietary.util.querylanguage.QueryLanguageHandler

fun String.isIdentifier():Boolean {
    if(!this.first().isLetter()){
        return false
    }
    return this.all { it.isLetterOrDigit() || it == '_' }
}

fun String.mightBeValidInQueryLanguage():Boolean{
    val separatorForPairs = QueryLanguageHandler.separatorForPairs
    val separatorBetweenKeyValue = QueryLanguageHandler.separatorBetweenKeyValue

    if(this.isEmpty()){
        return true
    }

    if(!this.first().isLetter()){
        return false
    }

    return this.all { it.isLetterOrDigit() || it == separatorForPairs || it == separatorBetweenKeyValue }
}

fun String.isValidString(): Boolean = this.trim().isNotBlank()

fun String.hasLeadingAndTrailing(char: Char = ' '): Boolean =
    this.startsWith(char) && this.endsWith(char)

fun String.insertAtBothSide(println: String): String = "${println}${this}${println}"

fun String.insertAtBothSide(char: Char = ' '): String = this.insertAtBothSide(println = char.toString())

fun String.tryToInsertAtBothSide(char: Char): String =
    if(this.hasLeadingAndTrailing(char))
        this
    else
        this.insertAtBothSide(char)

fun String.patternizeValue():String = this.tryToInsertAtBothSide('\'')

fun String.patternizeTable():String = this.tryToInsertAtBothSide('`')
