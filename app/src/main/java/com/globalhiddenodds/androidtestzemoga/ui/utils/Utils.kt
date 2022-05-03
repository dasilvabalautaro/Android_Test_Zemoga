package com.globalhiddenodds.androidtestzemoga.ui.utils

import android.content.Context
import android.widget.Toast
import java.io.File

object Utils {
    fun notify(context: Context, message: String){
        val appContext = context.applicationContext ?: return
        Toast.makeText(appContext,
            message, Toast.LENGTH_LONG).show()
    }

    fun databaseFileExists(context: Context): Boolean {
        return try {
            File(context.getDatabasePath("android_test_db").absolutePath).exists()
        }catch (e: Exception){
            false
        }
    }
}