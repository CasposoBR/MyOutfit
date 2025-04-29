package com.example.myoutfit.Activitys

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.myoutfit.ui.theme.MyOutfitTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        // Inicializar o SDK do Google Mobile Ads
        MobileAds.initialize(this) {}

        // Carregar o anúncio intersticial
        loadInterstitialAd()

        setContent {
            val navController = rememberNavController()
            MyOutfitTheme {  // Aplica o tema corretamente
                AppNavigation(
                    navController = navController,
                    onLoginSuccess = { showAdIfAvailable() })
            }
        }
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-8385242277547474/7571261429", // Substitua pelo ID do seu bloco de anúncios intersticiais
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    // Logando a falha no carregamento
                    Log.d("AdMob", "Falha ao carregar o anúncio: ${adError.message}")
                    Toast.makeText(this@MainActivity, "Falha ao carregar o anúncio.", Toast.LENGTH_SHORT).show()
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    // O anúncio foi carregado com sucesso
                    mInterstitialAd = ad
                    // Logando o sucesso no carregamento
                    Log.d("AdMob", "Anúncio carregado com sucesso!")
                }
            }
        )
    }

    // Mostrar o anúncio intersticial quando necessário
    fun showAdIfAvailable() {
        mInterstitialAd?.show(this) ?: Toast.makeText(this, "Anúncio ainda não carregado", Toast.LENGTH_SHORT).show()
    }
}

