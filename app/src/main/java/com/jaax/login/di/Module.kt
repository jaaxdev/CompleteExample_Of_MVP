package com.jaax.login.di

import android.app.Activity
import com.jaax.login.data.login.LoginMVP
import com.jaax.login.data.login.LoginPresenter
import com.jaax.login.data.network.LoginService
import com.jaax.login.data.network.RegisterService
import com.jaax.login.data.network.UserService
import com.jaax.login.data.register.RegisterMVP
import com.jaax.login.data.register.RegisterPresenter
import com.jaax.login.data.showusers.ShowUsersMVP
import com.jaax.login.data.showusers.ShowUsersPresenter
import com.jaax.login.ui.LoginActivity
import com.jaax.login.ui.RegisterActivity
import com.jaax.login.ui.ShowUsersActivity
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

    @Binds
    abstract fun bindRegisterView(view: RegisterActivity): RegisterMVP.View

    @Binds
    abstract fun bindRegisterPresenter(presenter: RegisterPresenter): RegisterMVP.Presenter

    @Binds
    abstract fun bindShowUsersView(view: ShowUsersActivity): ShowUsersMVP.View

    @Binds
    abstract fun bindShowUsersPresenter(presenter: ShowUsersPresenter): ShowUsersMVP.Presenter
}

@Module
@InstallIn(ActivityComponent::class)
object LoginModule {
    @Provides
    fun provideLoginActivity(activity: Activity) = activity as LoginActivity

    @Provides
    fun provideRegisterActivity(activity: Activity) = activity as RegisterActivity

    @Provides
    fun provideShowUsersActivity(activity: Activity) = activity as ShowUsersActivity
}