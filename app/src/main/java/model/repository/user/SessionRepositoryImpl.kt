package model.repository.user

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore instance
private val Context.dataStore by preferencesDataStore(name = "user_session")
class SessionRepositoryImpl(
    private val context: Context
) : SessionRepository {
    companion object {
        private val USER_ID_KEY = intPreferencesKey("user_id")
    }
    override suspend fun saveUserId(userId: Int) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
        }
    }
    override fun observeUserId(): Flow<Int?> {
        return context.dataStore.data.map { prefs ->
            prefs[USER_ID_KEY]
        }
    }
    override suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}