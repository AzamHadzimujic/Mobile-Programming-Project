package model.repository.user

import kotlinx.coroutines.flow.Flow
import model.data.local.entity.UserEntity

interface UserRepository {
    suspend fun insertUser(user: UserEntity)
    suspend fun getUser(username: String, password: String): UserEntity?
    fun observeUserById(id: Int): Flow<UserEntity?>
    suspend fun updateUser(user: UserEntity)
    suspend fun deleteUser(user: UserEntity)
}