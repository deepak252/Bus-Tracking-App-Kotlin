package com.example.bustrackingapp.core.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.bustrackingapp.core.domain.repository.UserPrefsRepository
import com.example.bustrackingapp.core.util.LoggerUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class UserPrefsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPrefsRepository {
    private val logger = LoggerUtil(c = "UserPrefsRepositoryImpl")
    private object PreferencesKey{
        val userToken  = stringPreferencesKey("user_token")
    }

    override suspend fun updateToken(token: String?) {
        logger.info("$token", "updateToken")

        dataStore.edit { preferences ->
            preferences[PreferencesKey.userToken] = token?:""
        }
    }

    override val getToken : Flow<String> = dataStore.data
        .catch {
            if(this is Exception){
                logger.info("${this.message}", "getToken")
                emit(emptyPreferences())
            }
        }.map { preference ->
            preference[PreferencesKey.userToken] ?: ""
        }
}