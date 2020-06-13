package pl.wsei.ecrsmobile

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var dbData: FirebaseFirestore
    private lateinit var dbAuth: FirebaseAuth
    private lateinit var userID: String
    private lateinit var userReference: CollectionReference

    private lateinit var queryLoggedUser: Query
    private lateinit var loggedUser: User




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //hide app name bar
        supportActionBar!!.hide()



        dbData = FirebaseFirestore.getInstance()
        dbAuth = FirebaseAuth.getInstance()

        //get user id (firebase auth module)
        userID = dbAuth.currentUser!!.uid

        //ref to collection users in db
        userReference = dbData.collection("users")
        //ref to document with logged user user id
        queryLoggedUser = userReference.whereEqualTo("id",userID)
        //create object of user
        loggedUser = User()

        //get data logged user from db
        queryLoggedUser.get().addOnSuccessListener { documents ->
            for (document in documents) {
                loggedUser.id = userID
                loggedUser.email = document.data.get("email").toString()
                loggedUser.isAdmin = document.data.get("admin") as Boolean
            }

            //setup email logged user in top main menu text view
            userTV.text = loggedUser.email

            //setup activity elements if current logged user is admin
            if(loggedUser.isAdmin){
                zapytaniaBT.text = "Wszystkie zapytania"
                isAdminInfoTV.visibility = View.VISIBLE
                goToAdminPanelBT.visibility = View.VISIBLE
                isAdminInfoTV.text = "ADMIN ACCOUNT"
            }
            else{
                isAdminInfoTV.text = "USER ACCOUNT" //invisible - for tests
            }
        }

        bazaSamochodowBT.setOnClickListener {
            val listaAutIntent = Intent(applicationContext, BazaSamochodow::class.java)
            startActivity(listaAutIntent)
        }
        zapytaniaBT.setOnClickListener {
            val zapytaniaIntent = Intent(applicationContext, Zapytania::class.java)
            //add extra to query list intent to send type of account is logged
            zapytaniaIntent.putExtra("isAdmin", isAdminInfoTV.text)
            startActivity(zapytaniaIntent)
        }
        goToAdminPanelBT.setOnClickListener {
            startActivity(Intent(applicationContext, AdminPanel::class.java))
        }

        logoutBT.setOnClickListener {
            //logout
            FirebaseAuth.getInstance().signOut()
            //go to login activity
            startActivity(Intent(applicationContext, Login::class.java))
        }
    }

}
