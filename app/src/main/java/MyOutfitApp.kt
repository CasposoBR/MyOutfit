//classe principal que inicia antes de tudo no app
import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // inicia o Dagger-hilt , que gerencia a injeção de dependencias do app.
class MyOutfitApp : Application(){
    override fun onCreate(){
        super.onCreate()
        FirebaseApp.initializeApp(this)

    }

}
