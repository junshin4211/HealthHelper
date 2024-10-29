package com.example.healthhelper.dietary.classhandler

class ClassInspector(
    val cls: Class<*>,
) {
    fun getFieldsName():List<String>{
        val fieldsName = mutableListOf<String>()

        val declaredFields = cls.declaredFields
        val fields = cls.fields
        for (declaredField in declaredFields) {
            if (declaredField.name !in fields.map { it.name }) {
                fieldsName.add(declaredField.name)
            }
        }

        return fieldsName.toList()
    }

    fun getFieldsValue(obj:Any):List<Any?>{
        val fieldsValue = mutableListOf<Any?>()

        val declaredFields = cls.declaredFields
        val fields = cls.fields
        for (declaredField in declaredFields) {
            if (declaredField.name !in fields.map { it.name }) {
                declaredField.isAccessible = true
                val value = declaredField.get(obj)
                fieldsValue.add(value)
            }
        }

        return fieldsValue.toList()
    }

    fun getFielsType():List<Class<*>>{
        val fieldsType = mutableListOf<Class<*>>()

        val declaredFields = cls.declaredFields
        val fields = cls.fields
        for (declaredField in declaredFields) {
            if (declaredField.name !in fields.map { it.name }) {
                declaredField.isAccessible = true
                val type = declaredField.type
                fieldsType.add(type)
            }
        }

        return fieldsType
    }

}