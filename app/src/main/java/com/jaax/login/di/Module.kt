package com.jaax.login.di

import android.app.Activity
import com.jaax.login.data.LoginMVP
import com.jaax.login.data.LoginPresenter
import com.jaax.login.data.network.LoginService
import com.jaax.login.ui.LoginActivity
import com.jaax.login.util.Utils
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
abstract class LoginMVPModule {
    @Binds
    abstract fun bindLoginView(view: LoginActivity): LoginMVP.View

    @Binds
    abstract fun bindLoginPresenter(presenter: LoginPresenter): LoginMVP.Presenter
}

@Module
@InstallIn(ActivityComponent::class)
object LoginModule {
    @Provides
    fun provideLoginActivity(activity: Activity) = activity as LoginActivity
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideService(): LoginService {
        return Retrofit
            .Builder()
            .baseUrl(Utils.BASEURL_LOGIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)
    }
}