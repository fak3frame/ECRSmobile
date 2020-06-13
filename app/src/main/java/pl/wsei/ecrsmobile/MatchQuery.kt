package pl.wsei.ecrsmobile

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


fun matchQuery() {
    val dbData = FirebaseFirestore.getInstance()
    //ref to query collection
    val zapytannieReference = dbData.collection("zapytania")
    //ref to cars collection
    val carReference = dbData.collection("cars")

    //create empty mutable list of Cars
    var carList = mutableListOf<Car>()
    //create empty mutable list of Cars (fond)
    var carListFound = mutableListOf<Car>()


    var query: Zapytanie
    var car: Car

    //get all cars from db
    carReference.get().addOnSuccessListener { documents ->
        for (document in documents) {
            car = document.toObject(Car::class.java)
            //put each car into car list
            carList.add(car)
        }

        //get all query from db
        zapytannieReference.get().addOnSuccessListener { documents ->
            for (document in documents) {
                //get one query from db
                query = document.toObject(Zapytanie::class.java)

                //get id of single document
                var eachDocumentId = document.id

                //get cars from list
                for (car in carList){
                    //if car brand is selected
                    if (query.marka != "Dowolna"){
                        //IF VALUE IS NOT SET IN ADD CAR QUERY ACTIVITY ITS AUTOMATIC SET TO
                        // MAX VALUE that condition check ALL VALUES
                        if (query.marka == car.marka && query.zasiegOd <= car.zasieg &&
                            query.zasiegDo >= car.zasieg && query.mocOd <= car.moc &&
                            query.mocDo >= car.moc && query.cenaOd <= car.cena &&
                            query.cenaDo >= car.cena){
                            //add found car into found car list
                            carListFound.add(car)
                        }
                    } else {
                        if (query.zasiegOd <= car.zasieg && query.zasiegDo >= car.zasieg &&
                            query.mocOd <= car.moc && query.mocDo >= car.moc &&
                            query.cenaOd <= car.cena && query.cenaDo >= car.cena){
                            carListFound.add(car)
                        }
                    }
                }
                var referenceToUpdateQuery: DocumentReference
                //ref to iterate query
                referenceToUpdateQuery = dbData.document("zapytania/$eachDocumentId")
                //insert car object into cars array field of query document
                referenceToUpdateQuery.update("listaPasujacychAut", carListFound)

                //clear found car list to iterate next query
                carListFound.clear()
            }
        }
    }
}

