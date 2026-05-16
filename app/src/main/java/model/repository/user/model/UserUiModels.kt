package model.repository.user.model

data class RegisterUserData(
    val username: String,
    val password: String
)
data class ProfileData(
    val username: String,
    val createdAt: Long
)
data class EditProfileData(
    val username: String,
    val password: String
)