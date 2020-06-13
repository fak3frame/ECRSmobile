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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {
    //

    private lateinit var emailLoginET: EditText
    private lateinit var passLoginET: EditText
    private lateinit var loginBT: Button
    private lateinit var dbData: FirebaseFirestore
    private lateinit var dbAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var userReference: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //hide app name bar
        supportActionBar!!.hide()

        emailLoginET = findViewById(R.id.emailLoginET)
        passLoginET = findViewById(R.id.passLoginET)
        loginBT = findViewById(R.id.loginBT)
        dbAuth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar2)

        //checking if user is logged in
        if(dbAuth.currentUser != null){
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        loginBT.setOnClickListener {

            //get values from forms
            val email = emailLoginET.text.toString().trim()
            val password = passLoginET.text.toString().trim()

            //validate text in forms
            if(TextUtils.isEmpty(email)){
                emailLoginET.setError("Email jest wymagany")
            }
            if(TextUtils.isEmpty(password)){
                passLoginET.setError("Hasło jest wymagane")
            }
            if (password.length < 6){
                passLoginET.setError("Hasło musi mieć ponad 6 znaków")
            }

            //if all ok
            else{
                //set progress bar animation visible
                progressBar.visibility = View.VISIBLE

                //send to firebase login with email/password module
                dbAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        //if response successful
                        if(it.isSuccessful){
                            //toast info - logged
                            Toast.makeText(applicationContext, "Zalogowano pomyślnie", Toast.LENGTH_SHORT).show()

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
