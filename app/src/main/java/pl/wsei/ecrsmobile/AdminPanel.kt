package pl.wsei.ecrsmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_admin_panel.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.userTV

class AdminPanel : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)
        //hide app name bar
        supportActionBar!!.hide()


        admin_panel_add_car_BT.setOnClickListener {
            val dodanieAutaIntent = Intent(applicationContext, DodawanieSamochodow::class.java)
            startActivity(dodanieAutaIntent)
        }
        admin_panel_delete_all_cars_BT.setOnClickListener {
            removeCarsFromDB()
        }
        admin_panel_add_cars_BT.setOnClickListener {
            addCarsToDB()
        }
        admin_panel_add_delete_cars.setOnClickListener {
            removeAndAddCarsToDB()
        }

        admin_panel_back_BT.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

    }
}