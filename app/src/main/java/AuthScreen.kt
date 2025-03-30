import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable//todas as funções que desenham elementos na tela no Compose precisam dessa anotação
fun AuthScreen(authViewModel: AuthViewModel = viewModel()){
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val googleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("SEU_WEB_CLIENT_ID")
                .requestEmail()
                .build()
        )
    }

    //lançador de atividade para capturar o resultado do login
    val launcher  = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try{
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { token ->
                authViewModel.signInWithGoogle(token) { success, error ->
                    if (success) {
                        Log.d("Auth", "Login com Google bem-sucedido!")
                        Toast.makeText(context, "Login com Google bem-sucedido!", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("Auth", "Erro: $error")
                        Toast.makeText(context, "Erro: $error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: ApiException) {
            Log.e("Auth", "Falha no login do Google", e)
        }
    }
fun launchGoogleSignIn(){
    val signInIntent = googleSignInClient.signInIntent
    launcher.launch(signInIntent)

}



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Bem vindo ao MyOutfit")
        Spacer(modifier = Modifier.height(16.dp))


       OutlinedTextField(
           value = email,
           onValueChange = { email = it },
           label = {Text("E-mail") }
       )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {Text("Senha") }
        )
        Spacer (modifier = Modifier.height(16.dp))

        Button(onClick = {
            authViewModel.loginWithEmail(email, password) { sucess ->
                if (sucess) {
                    Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Erro no login!", Toast.LENGTH_SHORT).show()
                }
            }
        }) {

            Text(text = "Login com E-mail")

        }


        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {/*Implementar login com e-mail*/}){
            Text(text = "Login com E-mail")
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { /* Navegar para cadastro */ }) {
            Text(text = "Criar conta")
        }
    }
}

@Preview
@Composable
fun PreviewAuthScreen(){
    AuthScreen()
}


