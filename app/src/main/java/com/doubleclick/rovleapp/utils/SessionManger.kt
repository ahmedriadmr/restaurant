package com.doubleclick.restaurant.utils

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.doubleclick.restaurant.utils.Constant.ID_KEY
import com.doubleclick.restaurant.utils.Constant.TOKEN_KEY
import com.doubleclick.restaurant.utils.DataStore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

object SessionManger {

    private val TAG = "SessionManger"

    suspend fun setToken(context: Context, token: String) {
        val tokenKey = stringPreferencesKey(TOKEN_KEY)
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }


    /*
    * to get token stored in preferences by (key name)
    * */
    fun Context.getToken(): Flow<String> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
//                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->

            preferences[stringPreferencesKey(TOKEN_KEY)] ?: ""
        }
    }

    suspend fun setId(context: Context, id: Int) {
        val idKey = intPreferencesKey(ID_KEY)
        context.dataStore.edit { preferences ->
            preferences[idKey] = id
        }
    }

    /*
    * to get id stored in preferences by (key name)
    * */
    fun Context.getId(): Flow<String> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
//                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[stringPreferencesKey(ID_KEY)] ?: ""
        }
    }

    /*
    * delete all data by logout
    * */
    suspend fun logout(context: Context) {
        context.dataStore.edit {
            it.clear()
        }
    }

}