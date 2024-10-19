package com.example.healthhelper.dietary.util.querylanguage

object QueryLanguageHandler {
    const val separatorForPairs = '&'
    const val separatorBetweenKeyValue = ':'
    fun tryToHandleStatement(
        statement: String
    ): Map<String, String>{
        return if(isValidStatement(statement)){
            handleStatement(statement)
        }else{
            emptyMap()
        }
    }

    private fun handleStatement(
        statement: String,
    ): Map<String, String>{
        val pairs = statement.split(separatorForPairs)
        var map = mutableMapOf<String,String>()

        pairs.forEach { pair ->
            val list1 = pair.split(separatorForPairs)
            map += list1[0] to list1[1]
        }
        return map
    }

    fun isValidStatement(
        statement: String
    ): Boolean{
        if(statement.isBlank()) {
            return false
        }
        
        val onlyContainQueryLanguageExpressionSymbol = statement.all{
            it.isLetterOrDigit() || it == '_' || it == separatorForPairs || it == separatorBetweenKeyValue
        }

        if(!onlyContainQueryLanguageExpressionSymbol){
            return false
        }

        val pairs = statement.split(separatorForPairs)
        if(pairs.isEmpty()){
            return false
        }

        pairs.forEach { pair ->
            val list1 = pair.split(separatorBetweenKeyValue)
            if(list1.size != 2){
                return false
            }
        }
        return true
    }
}