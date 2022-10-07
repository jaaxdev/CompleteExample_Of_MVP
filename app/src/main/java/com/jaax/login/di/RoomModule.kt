package com.jaax.login.di

import android.content.Context
import com.jaax.login.data.db.DatabaseLogin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = DatabaseLogin.invoke(context)

    @Singleton
    @Provides
    fun provideLoggedUsersDao(db: DatabaseLogin) = db.loggedUsersDao()
}