//Lógica do firebase
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth

) : ViewModel() {

fun loginWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
    firebaseAuth.signInWithEmailAndPassword(email,password)
        .addOnCompleteListener { task ->
            onResult(task.isSuccessful)

        }
}

    fun registerWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }

fun loginWithGoogle(idToken: String, onResult: (Boolean,String?) -> Unit){
  val credential = GoogleAuthProvider.getCredential(idToken, null)
    FirebaseAuth.getInstance().signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true, null)
            } else {
                onResult(false, task.exception?.message)
            }
        }
}
        fun signInWithGoogle(token: String, callback: (success: Boolean, error: String?) -> Unit) {
            val credential = GoogleAuthProvider.getCredential(token, null)

            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Se o login foi bem-sucedido
                        callback(true, null)
                    } else {
                        // Se ocorreu algum erro
                        callback(false, task.exception?.localizedMessage)
                    }
                }
        }

    }


}