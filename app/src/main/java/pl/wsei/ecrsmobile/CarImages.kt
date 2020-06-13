package pl.wsei.ecrsmobile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_car_images.*
import java.lang.Exception

class CarImages : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_images)
        //hide app name bar
        supportActionBar!!.hide()

        //get image url from intent - send by selected image car button in recycler view
        // car list
        val carImageUrl = intent.getStringExtra("carImageUrl")

        //check if car object has imager url
        if (carImageUrl != ""){
            Picasso.get()
                .load(carImageUrl)
                //setup max resolution of image
                .resize(10000,10000)
                //center image
                .centerInside()
                //send image to image view
                .into(car_image_view)
        }

        back_BT.setOnClickListener {
            finish()
        }
    }

}