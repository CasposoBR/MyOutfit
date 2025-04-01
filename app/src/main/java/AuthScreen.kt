import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun GoogleSignInButton(authViewModel: AuthViewModel) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        authViewModel.configureGoogleSignIn(context)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        authViewModel.handleGoogleSignInResult(result.data) { success, error ->
            if (success) {
                Toast.makeText(context, "Login com Google bem-sucedido!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Erro: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun launchGoogleSignIn() {
        val signInIntent = authViewModel.getGoogleSignInIntent()
        launcher.launch(signInIntent)
    }
}