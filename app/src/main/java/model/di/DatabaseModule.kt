package model.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import model.data.local.dao.ExpenseDao
import model.data.local.dao.PastTripDao
import model.data.local.dao.TripExpenseDao
import model.data.local.dao.UserDao
import model.data.local.db.AppDatabase
import model.data.local.dao.TripDao
import model.repository.currenttrip.TripRepository
import model.repository.currenttrip.TripRepositoryImpl
import model.repository.expense.ExpenseRepository
import model.repository.expense.ExpenseRepositoryImpl
import model.repository.pasttrip.PastTripRepository
import model.repository.pasttrip.PastTripRepositoryImpl
import model.repository.user.SessionRepository
import model.repository.user.SessionRepositoryImpl
import model.repository.user.UserRepository
import model.repository.user.UserRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // DATABASE
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideExpenseDao(db: AppDatabase): ExpenseDao =
        db.expenseDao()

    @Provides
    fun providePastTripDao(db: AppDatabase): PastTripDao =
        db.pastTripDao()

    @Provides
    fun provideTripExpenseDao(db: AppDatabase): TripExpenseDao =
        db.tripExpenseDao()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao =
        db.userDao()

    @Provides
    fun provideTripDao(db: AppDatabase): TripDao =
        db.tripDao()


    @Provides
    @Singleton
    fun provideExpenseRepository(
        dao: ExpenseDao
    ): ExpenseRepository {
        return ExpenseRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun providePastTripRepository(
        dao: PastTripDao
    ): PastTripRepository {
        return PastTripRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        dao: UserDao
    ): UserRepository {
        return UserRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideSessionRepository(
        @ApplicationContext context: Context
    ): SessionRepository {
        return SessionRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideTripRepository(
        dao: TripDao
    ): TripRepository {
        return TripRepositoryImpl(dao)
    }
}