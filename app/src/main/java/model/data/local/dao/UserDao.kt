package model.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import model.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("""
        SELECT * FROM users 
        WHERE username = :username 
        AND password = :password
        LIMIT 1
    """)
    suspend fun getUser(
        username: String,
        password: String
    ): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id")
    fun observeUserById(id: Int): Flow<UserEntity?>

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)
}