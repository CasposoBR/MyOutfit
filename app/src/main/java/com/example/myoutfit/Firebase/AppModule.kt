package com.example.myoutfit.Firebase

import android.app.Application
import android.content.Context
import com.example.myoutfit.R
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
        val clientId = context.getString(R.string.google_client_id) // Pega o client ID do strings.xml
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId) // Usando o client ID que está no strings.xml
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