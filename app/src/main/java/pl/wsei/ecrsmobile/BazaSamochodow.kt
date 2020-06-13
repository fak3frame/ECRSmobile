package pl.wsei.ecrsmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_baza_samochodow.*

class BazaSamochodow : AppCompatActivity() {

    private lateinit var dbData: FirebaseFirestore
    private lateinit var carReference: CollectionReference

    private lateinit var myAdapter:CarAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baza_samochodow)
        //hide app name bar
        supportActionBar!!.hide()

        //setup recycler view
        setupRecycerView()


        car_list_back_BT.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

    private fun setupRecycerView(){
        dbData = FirebaseFirestore.getInstance()

        //reference to cars collection
        carReference = dbData.collection("cars")

        //query sorts documents with brand field
        val query: Query = carReference.orderBy("marka", Query.Direction.ASCENDING)

        //set option value with query and Car class
        val options = FirestoreRecyclerOptions.Builder<Car>()
            .setQuery(query, Car::class.java)
            .build()

        //create adapter value with CarAdapter constructor with created option vale
        myAdapter = CarAdapter(options)

        listaAutRV.setHasFixedSize(true)
        //setup recycler view as linear listing of elements
        listaAutRV.layoutManager = LinearLayoutManager(this)
        //setup recycler view with adapter
        listaAutRV.adapter = myAdapter

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
