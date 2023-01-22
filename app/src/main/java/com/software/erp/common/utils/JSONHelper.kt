package com.software.erp.common.utils

import android.content.Context
//import com.google.gson.Gson


/*
inline fun <reified T> loadJSONFromAsset(mContext: Context, path: String?): ResultHandler<T> {
    return try {
        val inputStream = mContext.assets.open(path!!)
        val bytes = ByteArray(inputStream.available())
        inputStream.read(bytes, 0, bytes.size)
        val response: T = Gson().fromJson(String(bytes), T::class.java)
        ResultHandler.success(response)
    } catch (e: Exception) {
        ResultHandler.error(e.localizedMessage)
    }
}
*/
