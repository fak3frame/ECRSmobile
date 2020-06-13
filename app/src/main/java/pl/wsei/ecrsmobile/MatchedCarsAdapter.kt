package pl.wsei.ecrsmobile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.matched_car_details.view.*


class MatchedCarsAdapter(private val carList: List<Car>) :
        RecyclerView.Adapter<MatchedCarsAdapter.MatchedCarsViewHolder>() {

    //context to start activity
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchedCarsViewHolder {
        //setup layout - layout row of recycler view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.matched_car_details, parent, false)
        //setup context
        context = parent.context
        return MatchedCarsViewHolder(itemView)
    }

    //amount of recycler view element
    override fun getItemCount(): Int {
        //size of send car list
        return carList.size
    }

    //setup view elements with data for each recycler view element
    override fun onBindViewHolder(holder: MatchedCarsViewHolder, position: Int) {
        val currentCar = carList[position]
        holder.display_cars_brand_TV.text = currentCar.marka
        holder.display_cars_model_TV.text = currentCar.model
        holder.display_cars_range_TV.text = currentCar.zasieg.toString()
        holder.display_cars_powerTV.text = currentCar.moc.toString()
        holder.display_cars_acceleration_TV.text = currentCar.przyspieszenie.toString()
        holder.display_cars_top_speed_TV.text = currentCar.predkoscMaksymalna.toString()
        holder.display_cars_price_TV.text = currentCar.cena.toString()

        //iterate cars - text view on top RV element
        holder.display_cars_car_number_TV.text = "Auto ${position+1}"

        //car image button
        holder.car_photo_BT.setOnClickListener {
            val carImageIntent = Intent(context, CarImages::class.java)
            //sent car image url to car image activity
            carImageIntent.putExtra("carImageUrl", currentCar.imageUrl)
            context.startActivity(carImageIntent)
        }
    }

    //holder class - setup view elements
    class MatchedCarsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val display_cars_brand_TV = itemView.display_cars_brand_TV
        val display_cars_model_TV = itemView.display_cars_model_TV
        val display_cars_range_TV = itemView.display_cars_range_TV
        val display_cars_powerTV = itemView.display_cars_powerTV
        val display_cars_acceleration_TV = itemView.display_cars_acceleration_TV
        val display_cars_top_speed_TV = itemView.display_cars_top_speed_TV
        val display_cars_price_TV = itemView.display_cars_price_TV

        val display_cars_car_number_TV = itemView.display_cars_car_number_TV

        val car_photo_BT = itemView.car_photo_BT
    }

}