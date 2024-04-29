package com.doubleclick.rovleapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

object DataStore {

    /*
  *  dataStore -> extension function
  * */
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("session_manager")


}