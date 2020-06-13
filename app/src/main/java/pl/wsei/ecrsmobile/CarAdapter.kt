package pl.wsei.ecrsmobile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class CarAdapter(options: FirestoreRecyclerOptions<Car>):
    FirestoreRecyclerAdapter<Car, CarAdapter.CarHolder>(options) {

    //context to start activity
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarHolder {
        //setup layout - layout row of recycler view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_details, parent, false)
        //setup context
        context = parent.context
        return CarHolder(v)
    }

    //setup view elements with data for each recycler view element
    override fun onBindViewHolder(holder: CarHolder, position: Int, model: Car) {
        holder.displayCarsBrandTV.text = model.marka
        holder.displayCarsModelTV.text = model.model
        holder.displayCarsRangeTV.text = model.zasieg.toString()
        holder.displayCarsPowerTV.text = model.moc.toString()
        holder.displayCarsAccelerationTV.text = model.przyspieszenie.toString()
        holder.displayCarsTopSpeedTV.text = model.predkoscMaksymalna.toString()
        holder.displayCarsPriceTV.text = model.cena.toString()
        //iterate cars - text view on top RV element
        holder.displayCarsCarNumberTV.text = "Auto " + (position+1)

        //car image button
        holder.car_image_BT.setOnClickListener {
            val carImageIntent = Intent(context, CarImages::class.java)
            //sent car image url to car image activity
            carImageIntent.putExtra("carImageUrl", model.imageUrl)
            context.startActivity(carImageIntent)
        }
    }

    //holder class - setup view elements
    inner class CarHolder (view: View): RecyclerView.ViewHolder(view){
        val displayCarsBrandTV = view.findViewById(R.id.display_cars_brand_TV) as TextView
        val displayCarsModelTV = view.findViewById(R.id.display_cars_model_TV) as TextView
        val displayCarsRangeTV = view.findViewById(R.id.display_cars_range_TV) as TextView
        val displayCarsPowerTV = view.findViewById(R.id.display_cars_powerTV) as TextView
        val displayCarsAccelerationTV = view.findViewById(R.id.display_cars_acceleration_TV) as TextView
        val displayCarsTopSpeedTV = view.findViewById(R.id.display_cars_top_speed_TV) as TextView
        val displayCarsPriceTV = view.findViewById(R.id.display_cars_price_TV) as TextView
        val displayCarsCarNumberTV = view.findViewById(R.id.display_cars_car_number_TV) as TextView

        val car_image_BT = view.findViewById(R.id.car_image_BT) as Button
    }
}
