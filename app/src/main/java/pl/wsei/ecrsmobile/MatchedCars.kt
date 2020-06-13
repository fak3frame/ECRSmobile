package pl.wsei.ecrsmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_baza_samochodow.*
import kotlinx.android.synthetic.main.activity_matched_cars.*
import kotlinx.android.synthetic.main.activity_matched_cars.car_list_back_BT

class MatchedCars : AppCompatActivity() {

    private lateinit var dbData: FirebaseFirestore
    private lateinit var dbAuth: FirebaseAuth

    private lateinit var zapytanieReference: DocumentReference

    private lateinit var queryRef: String
    private lateinit var query: Zapytanie



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matched_cars)
        //hide app name bar
        supportActionBar!!.hide()

        dbData = FirebaseFirestore.getInstance()
        dbAuth = FirebaseAuth.getInstance()

        //declare matched car list
        var matchedCarList = arrayListOf<Car>()

        //get value from intent (send by adapter) contains id of document of query
        queryRef = intent.getStringExtra("matchedQuery")

        //ref to selected query
        zapytanieReference = dbData.document("zapytania/$queryRef")

        //get document from db
        zapytanieReference.get().addOnSuccessListener { document ->
            if(document != null){
                query = document.toObject(Zapytanie::class.java)!!

                //setup list of matched cars - query got array of matched cars
                matchedCarList = query.listaPasujacychAut

                //setup recycler view as linear listing of elements
                matched_cars_RV.layoutManager = LinearLayoutManager(this)
                //setup recycler view adapter with matched cars adapter constructor with
                // matched list of cars
                matched_cars_RV.adapter = MatchedCarsAdapter(matchedCarList)
            }
        }

        //check admin to set intent activity of query list
        var loggedUser: User = User()
        val userID = dbAuth.currentUser!!.uid
        var isAdmin = "USER ACCOUNT"
        val userReference = dbData.collection("users")
        val queryLoggedUser = userReference.whereEqualTo("id",userID)
        queryLoggedUser.get().addOnSuccessListener { documents ->
            for (document in documents) {
                loggedUser.isAdmin = document.data.get("admin") as Boolean
            }
            if(loggedUser.isAdmin){
                isAdmin = "ADMIN ACCOUNT"
            }
        }

        car_list_back_BT.setOnClickListener {
            val zapytaniaIntent = Intent(applicationContext, Zapytania::class.java)
            zapytaniaIntent.putExtra("isAdmin", isAdmin)
            startActivity(zapytaniaIntent)
        }
    }
}