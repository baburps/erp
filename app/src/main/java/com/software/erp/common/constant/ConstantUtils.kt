package com.software.erp.common.constant

class ConstantUtils {

    companion object {
        fun getFabricStructureList(): ArrayList<String> {
            val fabricList = ArrayList<String>()
            fabricList.add("Single Jersey")
            fabricList.add("Ribbed")
            return fabricList
        }
    }
}