package com.jaax.login.di

import com.jaax.login.data.network.LoginService
import com.jaax.login.data.network.RegisterService
import com.jaax.login.data.network.UserService
import com.jaax.login.util.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(Utils.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit) = retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideRegisterService(retrofit: Retrofit) = retrofit.create(RegisterService::class.java)

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit) = retrofit.create(UserService::class.java)
}