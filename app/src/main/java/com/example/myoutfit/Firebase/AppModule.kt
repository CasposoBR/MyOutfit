package com.example.myoutfit.Firebase

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(context: Context): GoogleSignInClient {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("253613913863-i76s4mrirgclb8or61cre7q7mh45ksu5.apps.googleusercontent.com") // ⚠️ MUITO IMPORTANTE: pegar o ID token do Firebase
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, options)
    }
    @Provides
    @Singleton
    fun provideOneTapSignInClient(context: Context): SignInClient {
        return Identity.getSignInClient(context)
    }
}