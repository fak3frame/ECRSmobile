package pl.wsei.ecrsmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_dodawanie_zapytania.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_zapytania.*
import java.util.*

class DodawanieZapytania : AppCompatActivity() {

    private lateinit var dbData: FirebaseFirestore
    private lateinit var dbAuth: FirebaseAuth

    private lateinit var carReference: CollectionReference
    private lateinit var zapytanieReference: CollectionReference

    private lateinit var specyficQueryReference: DocumentReference
    private lateinit var documentId: String


    private lateinit var userID: String
    private lateinit var userReference: CollectionReference

    private lateinit var queryLoggedUser: Query
    private lateinit var loggedUser: User

    private lateinit var isAdmin: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodawanie_zapytania)
        //hide app name bar
        supportActionBar!!.hide()


        dbData = FirebaseFirestore.getInstance()
        dbAuth = FirebaseAuth.getInstance()


        zapytanieReference = dbData.collection("zapytania")
        carReference = dbData.collection("cars")


        //set user id (query field)
        userID = dbAuth.currentUser!!.uid

        //check admin to set intent activity of query list
        isAdmin = "USER ACCOUNT"
        userReference = dbData.collection("users")
        queryLoggedUser = userReference.whereEqualTo("id",userID)
        loggedUser = User()
        queryLoggedUser.get().addOnSuccessListener { documents ->
            for (document in documents) {
                loggedUser.isAdmin = document.data.get("admin") as Boolean
            }
            if(loggedUser.isAdmin){
                isAdmin = "ADMIN ACCOUNT"
            }
        }


        //declare variable of query
        var selectetBrand: String
        var rangeFrom: Int
        var rangeTo: Int
        var powerFrom: Int
        var powerTo: Int
        var priceFrom: Int
        var priceTo: Int


        //check activity as edit query
        if (intent.hasExtra("documentId")){
            //get values from intent (set in adapter of queries)
            rangeFrom = intent.getStringExtra("rangeFrom").toInt()
            rangeTo = intent.getStringExtra("rangeTo").toInt()
            powerFrom = intent.getStringExtra("powerFrom").toInt()
            powerTo = intent.getStringExtra("powerTo").toInt()
            priceFrom = intent.getStringExtra("priceFrom").toInt()
            priceTo = intent.getStringExtra("priceTo").toInt()

            documentId = intent.getStringExtra("documentId")
            //set ref to edited query
            specyficQueryReference = dbData.document("zapytania/$documentId")

            //conditions check default values of edited query
            //if value is not default, edit text forms is filled by send values from adapter
            if(rangeFrom != 0){
                add_query_range_from_ET.setText(rangeFrom.toString())
            }
            if(rangeTo != 99999){
                add_query_range_to_ET.setText(rangeTo.toString())
            }
            if(powerFrom != 0){
                add_query_power_from_ET.setText(powerFrom.toString())
            }
            if(powerTo != 99999){
                add_query_power_to_ET.setText(powerTo.toString())
            }
            if(priceFrom != 0){
                add_query_price_from_ET.setText(priceFrom.toString())
            }
            if(priceTo != 999999){
                add_query_price_to_ET.setText(priceTo.toString())
            }
        }


        //create car brand list to select list in spinner
        val brandList = mutableListOf<String>()
        //add element of default
        brandList.add("Dowolna")
        //value of selected brand - if creating car query
        selectetBrand = brandList.get(0)
        //if edit query - set selected brand
        if (intent.hasExtra("brand")){
            selectetBrand = intent.getStringExtra("brand")
        }


        //get all car list to create list of brand cars from added cars to db
        carReference.orderBy("marka").get().addOnSuccessListener { documents ->
            //loop reads all car brands
            for(document in documents){
                var newBrand = document.data.get("marka").toString()
                //check multiple brads
                if(!brandList.contains(newBrand)){
                    brandList.add(newBrand)
                }
            }

            //set spinner adapter contains layout (default android) and my brand list
            val brandListSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, brandList)

            //attach adapter to my spinner
            add_query_brand_select_SP.adapter = brandListSpinnerAdapter

            //create start position variable (number)
            //default 0 if activity creating new query
            var spinnerStartPosition = 0

            //if edit query - value of brand is set
            //condition check if selected brand is in brand list
            if (brandList.contains(selectetBrand)){
                //set position of loaded brand - position in brand list
                spinnerStartPosition = brandList.indexOf(selectetBrand)
                //else spinner stat position is default - "Dowolna"
            }

            //set start position of spinner
            add_query_brand_select_SP.setSelection(spinnerStartPosition)

            //setup spinner
            add_query_brand_select_SP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //selected item attach to brand variable
                    selectetBrand = parent!!.getItemAtPosition(position).toString()
                }
            }//end of setup spinner
        }//end of car list query

        //setup id of query (number)
        //get max number of query
        var topQueryNumber: Int = 0
        //get all queries
        zapytanieReference.get().addOnSuccessListener { documents ->
            for (docuent in documents){
                topQueryNumber++
            }
        }
        //if query is edited, value is rewrite
        if(intent.hasExtra("topQueryNumber")){
            topQueryNumber = intent.getStringExtra("topQueryNumber").toString().toInt()
        }


        //save query button
        add_query_save_BT.setOnClickListener {

            //check set value of variables by user
            //if edit text of query is empty, it set default value
            if (add_query_range_from_ET.text.toString()!=""){
                rangeFrom = add_query_range_from_ET.text.toString().toInt()
            } else{
                rangeFrom = 0
            }
            if (add_query_range_to_ET.text.toString()!=""){
                rangeTo = add_query_range_to_ET.text.toString().toInt()
            } else{
                rangeTo = 99999
            }
            if (add_query_power_from_ET.text.toString()!=""){
                powerFrom = add_query_power_from_ET.text.toString().toInt()
            }else{
                powerFrom = 0
            }
            if (add_query_power_to_ET.text.toString()!=""){
                powerTo = add_query_power_to_ET.text.toString().toInt()
            }else{
                powerTo = 99999
            }
            if (add_query_price_from_ET.text.toString()!=""){
                priceFrom = add_query_price_from_ET.text.toString().toInt()
            }else{
                priceFrom = 0
            }
            if (add_query_price_to_ET.text.toString()!=""){
                priceTo = add_query_price_to_ET.text.toString().toInt()
            }else{
                priceTo = 999999
            }

            //create object of car witch set fields - to set/update query in db
            val zapytanie =
                Zapytanie(topQueryNumber,
                    userID,
                    selectetBrand,
                    rangeFrom,
                    rangeTo,
                    powerFrom,
                    powerTo,
                    priceFrom,
                    priceTo)

            //if intent has extra - activity edit query
            if (intent.hasExtra("documentId")){
                //update query
                specyficQueryReference.set(zapytanie)
            }
            else {
                //insert query (id auto generated)
                zapytanieReference.add(zapytanie)
            }

            val zapytaniaIntent = Intent(applicationContext, Zapytania::class.java)
            zapytaniaIntent.putExtra("isAdmin", isAdmin)
            startActivity(zapytaniaIntent)
        }
    }
}
