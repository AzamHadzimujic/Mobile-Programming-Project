package model.repository.user

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun saveUserId(userId: Int)
    fun observeUserId(): Flow<Int?>
    suspend fun clearSession()
}