package pl.wsei.ecrsmobile

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ZapytaniaAdapter(options: FirestoreRecyclerOptions<Zapytanie>):
            FirestoreRecyclerAdapter<Zapytanie,
            ZapytaniaAdapter.ZapytanieHolder>(options){

    //context to start activity
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZapytanieHolder {
        //setup layout - layout row of recycler view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.zapytanie_details, parent, false)
        //setup context
        context = parent.context
        return ZapytanieHolder(v)
    }

    //setup view elements with data for each recycler view element
    override fun onBindViewHolder(holder: ZapytanieHolder, position: Int, model: Zapytanie) {
        holder.markaSetTV.text = model.marka

        //condition check if values of queries is default
        // if default - text view are set to "....."
        if (model.zasiegOd != 0){
            holder.queryListRangeFromET.text = model.zasiegOd.toString()
        }
        if (model.zasiegDo != 99999){
            holder.queryListRangeToET.text = model.zasiegDo.toString()
        }
        if (model.mocOd != 0){
            holder.queryListPowerFromET.text = model.mocOd.toString()
        }
        if (model.mocDo != 99999){
            holder.queryListPowerToET.text = model.mocDo.toString()
        }
        if (model.cenaOd != 0){
            holder.queryListPriceFromET.text = model.cenaOd.toString()
        }
        if (model.cenaDo != 999999){
            holder.queryListPriceToET.text = model.cenaDo.toString()
        }

        //default is hidden
        holder.idUseraZapytania.text = model.userId

        //if car query has mach
        if(model.listaPasujacychAut.size>0){
            //set text value info of mach query
            holder.queryDetailFoundInfoTV.text = "Znaleziono auta do zapytania"
            //set color of text value
            holder.queryDetailFoundInfoTV.setTextColor(Color.GREEN)
            //set visible go to matched car list of query button
            holder.query_list_show_cars.visibility = View.VISIBLE

        }else{
            //set text value info of mach query
            holder.queryDetailFoundInfoTV.text = "Nieznaleziono aut do zapytania"
            //set color of text value
            holder.queryDetailFoundInfoTV.setTextColor(Color.RED)
        }

        //iterate queries - text view on top RV element
        holder.query_detail_counter_TV.text = "Zapytanie " + (position+1)

        //delete adapter position (real-time)
        holder.query_detail_delete_BT.setOnClickListener {
            deleteItem(position)
        }

        //edit query
        holder.query_detail_edit_BT.setOnClickListener {
            //create intent of add query
            val editIntent = Intent(context, DodawanieZapytania::class.java)
            //put all values as extra
            editIntent.putExtra("documentId", snapshots.getSnapshot(position).id)
            editIntent.putExtra("brand", model.marka)
            editIntent.putExtra("rangeFrom", model.zasiegOd.toString())
            editIntent.putExtra("rangeTo", model.zasiegDo.toString())
            editIntent.putExtra("powerFrom", model.mocOd.toString())
            editIntent.putExtra("powerTo", model.mocDo.toString())
            editIntent.putExtra("priceFrom", model.cenaOd.toString())
            editIntent.putExtra("priceTo", model.cenaDo.toString())
            editIntent.putExtra("topQueryNumber", model.idZapytania.toString())
            context.startActivity(editIntent)
        }

        //show all matched cars
        holder.query_list_show_cars.setOnClickListener {
            val matchedCarsIntent = Intent(context, MatchedCars::class.java)
            //put object of query into intent as extra
            matchedCarsIntent.putExtra("matchedQuery", snapshots.getSnapshot(position).id)
            context.startActivity(matchedCarsIntent)
        }
    }

    //fun deleting adapter element with delete in database using snapshots with position
    fun deleteItem(position: Int ){
        snapshots.getSnapshot(position).reference.delete()
    }

    //holder class - setup view elements (with init)
    inner class ZapytanieHolder(view: View) : RecyclerView.ViewHolder(view) {

        val markaSetTV :TextView
        val queryListRangeFromET: TextView
        val queryListRangeToET: TextView
        val queryListPowerFromET: TextView
        val queryListPowerToET: TextView
        val queryListPriceFromET: TextView
        val queryListPriceToET: TextView

        val queryDetailFoundInfoTV: TextView

        val idUseraZapytania: TextView

        val query_detail_counter_TV: TextView

        val query_detail_delete_BT: Button
        val query_detail_edit_BT: Button
        val query_list_show_cars: Button

        init {
            markaSetTV = view.findViewById(R.id.markaSetTV)
            queryListRangeFromET = view.findViewById(R.id.query_list_range_from_TV)
            queryListRangeToET = view.findViewById(R.id.query_list_range_to_TV)
            queryListPowerFromET = view.findViewById(R.id.query_list_power_from_TV)
            queryListPowerToET = view.findViewById(R.id.query_list_power_to_TV)
            queryListPriceFromET = view.findViewById(R.id.query_list_price_from_TV)
            queryListPriceToET = view.findViewById(R.id.query_list_price_to_TV)

            queryDetailFoundInfoTV = view.findViewById(R.id.query_detail_found_info_TV)

            idUseraZapytania = view.findViewById(R.id.idUzytkownikaZapytania)

            query_detail_counter_TV = view.findViewById(R.id.query_detail_counter_TV)

            query_detail_delete_BT = view.findViewById(R.id.query_detail_delete_BT)
            query_detail_edit_BT = view.findViewById(R.id.query_detail_edit_BT)
            query_list_show_cars = view.findViewById(R.id.query_list_show_cars)

        }
    }
}

