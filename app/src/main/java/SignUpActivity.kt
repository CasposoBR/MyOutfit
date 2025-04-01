import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


//lógica do cadastro
class SignUpActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            val cpf = binding.editCpf.text.toString()
            val birthDate = binding.editBirthDate.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && cpf.isNotEmpty() && birthDate.isNotEmpty()) {
                registerWithEmail(email,password,cpf,birthDate)

            }else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()

            }
        }

        binding.btnSignUpGoogle.setOnClickListener {
            signUpWithGoogle()

        }
    }

    private fun  registerWithEmail(email: String, password: String, cpf: String, birthDate: String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful){
                    //salvar dados adicionais, como Cpf e data de nascimento ao firebase firestore
                    val user = auth.currentUser
                    //navegar para a proxima tela

                }else{
                    Toast.makeText(this, "Erro ao cadastrar, tente novamente",Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun signUpWithGoogle(){
        //implementar a lógica do cadastro com o google

    }





}