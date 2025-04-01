import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

//logica do login
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.ToString()

            if (email.isNotEmpty() && password.isNotEmpty() ){
                loginWithEmail(email,password)

            }else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()

            }
        }

    binding.btnLoginGoogle.SetOnClickListener{
        signInWithGoogle()

        }

    }

    private fun loginWithEmail (email: String, password: String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener((this)) {task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    //navegar para a proxima tela

                }else {
                    Toast.makeText(this, "erro ao logar. Tente novamente.", Toast.LENGTH_SHORT).show()

                }
            }

        private fun signInWithGoogle(){ //implementar lógica do login aqui

        }

    }

}