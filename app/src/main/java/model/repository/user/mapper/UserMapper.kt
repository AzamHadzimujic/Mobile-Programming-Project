package model.repository.user.mapper

import model.data.local.entity.UserEntity
import model.repository.user.model.*

fun RegisterUserData.toEntity(): UserEntity {
    return UserEntity(
        username = username,
        password = password
    )
}
fun UserEntity.toProfileData(): ProfileData {
    return ProfileData(
        username = username,
        createdAt = createdAt
    )
}
fun UserEntity.toEditProfileData(): EditProfileData {
    return EditProfileData(
        username = username,
        password = password
    )
}
fun EditProfileData.toEntity(existing: UserEntity): UserEntity {
    return existing.copy(
        username = username,
        password = password
    )
}