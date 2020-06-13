package pl.wsei.ecrsmobile

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

fun removeCarsFromDB(){
    val dbData: FirebaseFirestore = FirebaseFirestore.getInstance()
    val carsReference = dbData.collection("cars")
    var carReference: DocumentReference

    carsReference.get().addOnSuccessListener { documents ->
        for (document in documents) {
            carReference = dbData.document("cars/${document.id}")
            carReference.delete()
        }
    }
}