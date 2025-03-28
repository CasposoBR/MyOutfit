import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(authViewModel: AuthViewModel = viewModel()){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Bem vindo ao MyOutfit")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {/* Implementar login com Google */ }) {
            Text(text = "Login com Google")
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


