package com.example.healthhelper.dietary.classhandler

class ClassesValueHandler<T>(
    private val classes: List<T>,
){
    private val classInspector = ClassInspector(classes[0]!!::class.java)
    private val fieldsName = classInspector.getFieldsName().toList()

    fun sumByField(): Map<String,Double> {
        val totals: MutableMap<String,Double> = mutableMapOf()

        this.fieldsName.forEach {fieldName ->
            totals[fieldName] = 0.0
        }

        this.classes.forEach{ obj ->
            val fieldsValue = classInspector.getFieldsValue(obj as Any).toList()
            fieldsValue.forEachIndexed{ index,fieldValue ->
                val fieldName = this.fieldsName[index]
                if(fieldValue is Number){
                    if(totals.containsKey(fieldName)) {
                        totals[fieldName] = totals[fieldName]!! + fieldValue.toDouble()
                    }else{
                        totals[fieldName] = 0.0
                    }
                }
            }
        }
        return totals.toMap()
    }
}