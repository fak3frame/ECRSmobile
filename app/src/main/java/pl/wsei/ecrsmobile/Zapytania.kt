package pl.wsei.ecrsmobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_zapytania.*

class Zapytania : AppCompatActivity(){

    private lateinit var dbData: FirebaseFirestore
    private lateinit var dbAuth: FirebaseAuth
    private lateinit var zapytannieReference: CollectionReference

    private lateinit var userID: String
    private lateinit var isAdmin: String

    private lateinit var myAdapter:ZapytaniaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zapytania)
        //hide app name bar
        supportActionBar!!.hide()

        //mach queries
        matchQuery()

        //get extra from intent - main menu send value
        if(intent.hasExtra("isAdmin")){
            query_list_is_admin_info.text = intent.getStringExtra("isAdmin")
            //if admin is logged set queries button text to all queries
            if(query_list_is_admin_info.text == "ADMIN ACCOUNT"){
                query_list_type_of_list_query_TV.text = "Wszystkie zapytania"
            }
        } else{
            isAdmin = "ERROR"
        }

        isAdmin = query_list_is_admin_info.text.toString()


        setupRecycerView(isAdmin)

        query_list_back_BT.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        odswiezBT.setOnClickListener {
            val zapytaniaIntent = Intent(applicationContext, Zapytania::class.java)
            zapytaniaIntent.putExtra("isAdmin", query_list_is_admin_info.text)
            startActivity(zapytaniaIntent)
        }

        dodajZapytanieBT.setOnClickListener {
            val dodajZapytanieIntent = Intent(applicationContext, DodawanieZapytania::class.java)
            startActivity(dodajZapytanieIntent)
        }

    }
    private fun setupRecycerView(isAdmin: String){
        dbData = FirebaseFirestore.getInstance()
        dbAuth = FirebaseAuth.getInstance()

        //get logged user id
        userID = dbAuth.currentUser!!.uid

        //ref to queries collection
        zapytannieReference = dbData.collection("zapytania")

        //create query value for adapter
        var queryLoggedUser:Query
        if (isAdmin == "ADMIN ACCOUNT"){
            //if admin is logged, query is sorted by query id (number)
            queryLoggedUser = zapytannieReference.orderBy("idZapytania", Query.Direction.ASCENDING)

        } else{
            //if user is logged, query limited car query to show only logged user documents
            queryLoggedUser = zapytannieReference.whereEqualTo("userId",userID)
        }

        //set option value with query and query class
        val options = FirestoreRecyclerOptions.Builder<Zapytanie>()
            .setQuery(queryLoggedUser, Zapytanie::class.java)
            .build()

        //create adapter value with CarAdapter constructor with created option vale
        myAdapter = ZapytaniaAdapter(options)

        listaZapytanRV.setHasFixedSize(true)
        //setup recycler view as linear listing of elements
        listaZapytanRV.layoutManager = LinearLayoutManager(this)
        //setup recycler view with adapter
        listaZapytanRV.adapter = myAdapter

    }

    //methods ensuring working recycler view with realtime data in db
    override fun onStart() {
        super.onStart()
        myAdapter.startListening()
    }
    override fun onStop() {
        super.onStop()
        myAdapter.stopListening()
    }
}
