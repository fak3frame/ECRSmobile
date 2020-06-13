package pl.wsei.ecrsmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_dodawanie_samochodow.*

class DodawanieSamochodow : AppCompatActivity() {


    private lateinit var dbData: FirebaseFirestore

    private lateinit var carReference: CollectionReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodawanie_samochodow)
        //hide app name bar
        supportActionBar!!.hide()

        dbData = FirebaseFirestore.getInstance()

        zapiszAutoBT.setOnClickListener {
            //get values from edit text
            val brand = add_car_brand_ET.text.toString()
            val model = add_car_model_ET.text.toString()
            val range = add_car_range_ET.text.toString()
            val power = add_car_power_ET.text.toString()
            val acceleration = add_car_acceleration_ET.text.toString()
            val topSpeed = add_car_top_speed_ET.text.toString()
            val price = add_car_price_ET.text.toString()

            //create object of car
            val car = Car(
                brand,
                model,
                range.toInt(),
                power.toInt(),
                acceleration.toDouble(),
                topSpeed.toInt(),
                price.toInt()
            )

            //ref to cars collection
            carReference = dbData.collection("cars")
            //put object of car as new document
            carReference.add(car)

            //back to admin panel
            startActivity(Intent(applicationContext, AdminPanel::class.java))
        }


    }

}
