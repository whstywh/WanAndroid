package com.wh.wanandroid.utils

import android.content.Context
import android.text.TextUtils
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.wh.wanandroid.base.App
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object DataStoreUtils {

    val username = stringPreferencesKey("username")

    //未登录
    suspend fun isNoLogin(context: Context?): Boolean {
        return TextUtils.isEmpty(getUsername(context))
    }

    //登录
    suspend fun login(name: String?) {
        App.instance().applicationContext.dataStore.edit { setting ->
            setting[username] = name ?: ""
        }
    }

    //退出登录
    suspend fun logout(context: Context?) {
        context?.dataStore?.edit { setting ->
            setting[username] = ""
        }
    }

    suspend fun getUsername(context: Context?): String {
        return context?.dataStore?.data?.map { preferences ->
            preferences[username] ?: ""
        }?.first() ?: ""
    }
}