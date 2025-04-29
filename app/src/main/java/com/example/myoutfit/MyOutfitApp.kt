package com.example.myoutfit//classe principal que inicia antes de tudo no app
import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyOutfitApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializa o Firebase
        FirebaseApp.initializeApp(this)

        // Inicializa o AdMob
        MobileAds.initialize(this) {
            val requestConfiguration = RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("EMULATOR"))
                .build()
            MobileAds.setRequestConfiguration(requestConfiguration)
            // Aqui você pode verificar o status da inicialização se necessário
            // initializationStatus.adapterStatuses.forEach { /* Log ou Debug */ }
        }
    }
}
