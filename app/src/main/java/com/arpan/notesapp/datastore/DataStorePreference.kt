package com.arpan.notesapp.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStorePreference @Inject constructor(private val preferences: DataStore<Preferences>) {

    suspend fun saveViewType(isLinear: Boolean) {
        val dataStoreKey = booleanPreferencesKey(LAYOUT_TYPE_KEY)
        preferences.edit { viewPreference ->
            viewPreference[dataStoreKey] = isLinear
        }
    }

    suspend fun fetchViewType(): Boolean {
        val dataStoreKey = booleanPreferencesKey(LAYOUT_TYPE_KEY)
        val viewPreference =  preferences.data.first()
        return viewPreference[dataStoreKey] ?: true
    }


    companion object{
        const val LAYOUT_TYPE_KEY = "ViewType"
    }
}