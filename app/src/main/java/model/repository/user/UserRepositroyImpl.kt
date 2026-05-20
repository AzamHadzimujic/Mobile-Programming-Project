package model.repository.user

import kotlinx.coroutines.flow.Flow
import model.data.local.dao.UserDao
import model.data.local.entity.UserEntity

class UserRepositoryImpl(
    private val dao: UserDao
) : UserRepository {
    override suspend fun insertUser(user: UserEntity) {
        dao.insertUser(user)
    }
    override suspend fun getUser(username: String, password: String): UserEntity? {
        return dao.getUser(username, password)
    }
    override fun observeUserById(id: Int): Flow<UserEntity?> {
        return dao.observeUserById(id)
    }
    override suspend fun updateUser(user: UserEntity) {
        dao.updateUser(user)
    }
    override suspend fun deleteUser(user: UserEntity) {
        dao.deleteUser(user)
    }
}