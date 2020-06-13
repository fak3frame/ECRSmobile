package pl.wsei.ecrsmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {

    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var registerBT: Button
    private lateinit var goToLoginPageBT: Button

    private lateinit var dbAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    private lateinit var dbData: FirebaseFirestore
    private lateinit var userIdAuth: String

    private lateinit var userReference: CollectionReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //hide app name bar
        supportActionBar!!.hide()

        emailET = findViewById(R.id.emailRegisterET)
        passwordET = findViewById(R.id.passRegisterET)
        registerBT = findViewById(R.id.registerBT)
        goToLoginPageBT = findViewById(R.id.goToLoginPageBT)

        progressBar = findViewById(R.id.progressBar)

        dbAuth = FirebaseAuth.getInstance()
        dbData = FirebaseFirestore.getInstance()

        //checking if user is logged in
        if(dbAuth.currentUser != null){
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        //button staring login activity
        goToLoginPageBT.setOnClickListener {
            startActivity(Intent(applicationContext, Login::class.java))
        }

        registerBT.setOnClickListener {

            //get values from edit text forms
            val email = emailET.text.toString().trim()
            val password = passwordET.text.toString().trim()

            //validate forms
            if(TextUtils.isEmpty(email)){
                emailET.setError("Email jest wymagany")
            }
            if(TextUtils.isEmpty(password)){
                passwordET.setError("Hasło jest wymagane")
            }

            if (password.length < 6){
                passwordET.setError("Hasło musi mieć ponad 6 znaków")
            }

            //if all ok
            else{
                //set progress bar animation visible
                progressBar.visibility = View.VISIBLE

                //create user with email and password in firebase auth module
                dbAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{
                        if(it.isSuccessful){

                            //toast info - user created
                            Toast.makeText(applicationContext, "Użytkownik stworzony", Toast.LENGTH_SHORT).show()

                            userIdAuth = dbAuth.currentUser!!.uid
                            val user = User(userIdAuth, email, false)
                            userReference = dbData.collection("users")
                            userReference.add(user).addOnSuccessListener {
                                //Log.d("TAG", "user profile is created in db")
                            }

                            //start main activity (main menu app)
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                        }
                        else{
                            //toast info with bug
                            Toast.makeText(applicationContext, "Error + ${it.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
