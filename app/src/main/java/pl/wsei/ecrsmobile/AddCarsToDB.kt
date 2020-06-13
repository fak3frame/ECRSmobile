package pl.wsei.ecrsmobile

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

fun addCarsToDB(){
    val dbData: FirebaseFirestore = FirebaseFirestore.getInstance()
    val carsReference = dbData.collection("cars")
    var carReference: DocumentReference

    val carList = mutableListOf<Car>()

    carList.add(Car("Audi","e-tron",328,408,5.5,200,293964,"https://www.autocentrum.pl/ac-file/car-version/5cd97ca757502a8019465cf0/audi-e-tron-suv.jpg"))
    carList.add(Car("Audi","e-tron Sportback",351,408,5.5,200,303789, "https://i.wpimg.pl/730x0/m.autokult.pl/a1915300-medium-cropped-e97c0986.jpg"))
    carList.add(Car("BMW","i3",246,170,7.2,150,174689, "https://ocdn.eu/pulscms-transforms/1/A3vktkpTURBXy9iZmFiZTcwZjU4NjQzMWRiYzUzM2UxNjc0ZTM2ZDQzYi5qcGeRlQMAzQEezQeAzQQ5"))
    carList.add(Car("BMW","i3s",246,184,6.8,161,187265, "https://icdn2.digitaltrends.com/image/digitaltrends/2019-bmw-i3s-review-04501.jpg"))
    carList.add(Car("Chevrolet","Bolt EV",417,204,6.5,145,143917, "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/2020-chevrolet-mmp-1-1566585206.jpg"))
    carList.add(Car("Fiat","500e",135,113,8.9,137,131498,"https://i.wpimg.pl/730x0/m.autokult.pl/nuova-fiat-500-quella-el-8de6655.jpg"))
    carList.add(Car("Hyundai","IONIQ Electric",274,136,10.1,164,129867, "https://i.wpimg.pl/730x0/m.autokult.pl/hyundai-ioniq-electric-z-cf57fd6.jpg"))
    carList.add(Car("Hyundai","Kona Electric",415,204,7.6,167,146157, "https://elektrowoz.pl/wp-content/uploads/2018/10/Hyundai-Kona-Electric-2019-CENA.jpg"))
    carList.add(Car("Jaguar","I-PEACE",377,400,4.5,200,274511, "https://rootblog.pl/wp-content/uploads/2019/03/Jaguar-I-Pace.jpg"))
    carList.add(Car("Kia","Niro EV",385,204,7.5,167,153624, "http://projectautomotive.pl/wp-content/uploads/2018/05/Kia-Niro-EV-01-820x510.jpg"))
    carList.add(Car("MINI","Cooper SE",177,184,6.9,150,117507,"https://static.antyweb.pl/wp-content/uploads/2019/07/09200150/mini_electric_100-1420x670.jpg"))
    carList.add(Car("Nissan","Leaf",240,150,7.4,145,124188, "https://www-europe.nissan-cdn.net/content/dam/Nissan/nissan_europe/Configurator/Leaf/B12P/grades/18TDIEULHD_LEAF_B12P_2D_GRADE_002.jpg.ximg.l_12_m.smart.jpg"))
    carList.add(Car("Nissan","Leaf e+ S",364,218,6.5,161,150126, "https://ev-database.org/img/auto/Nissan_Leaf_2018/Nissan_Leaf_2018-01.jpg"))
    carList.add(Car("Nissan","Leaf e+ S SV SL",346,218,6.5,161,156218, "https://cdn.motor1.com/images/mgl/WPp3j/s3/nissan-reveals-leaf-e-plus-62-kwh-battery-226-mile-range.jpg"))
    carList.add(Car("Porshe","Taycan 4S PBP",327,571,3.8,249,444051, "https://cnet3.cbsistatic.com/img/y2Yogq6Vkdz70gxaiEsIyh6ayo4=/1200x675/2019/12/10/a9f901c2-8f5e-4f2c-9d39-5eb3bbe68979/taycan-4s-ogi.jpg"))
    carList.add(Car("Porshe","Taycan Turbo",323,680,3.5,259,603294, "https://wrc.net.pl/app/uploads/2019/12/2020-porsche-taycan.jpg"))
    carList.add(Car("Porshe","Teycan Turbo S",309,761,2.6,259,737307, "https://cdn.galleries.smcloud.net/t/galleries/gf-U1WT-HBsm-z9vW_porsche-taycan-turbo-s-1920x1080-nocrop.jpg"))
    carList.add(Car("Tesla","Model 3 Standard Range",402,218,5.3,225,149301, "https://ev-database.org/img/auto/Tesla_Model_3/Tesla_Model_3-01.jpg"))
    carList.add(Car("Tesla","Model 3 Long Range",518,231,4.4,233,184671, "https://superauto24.com/files/temp/cars-offbig-71384/1582902155_642_441_dsc_0002.jpg"))
    carList.add(Car("Tesla","Model 3 Performance",481,262,3.2,261,216111, "https://d3lp4xedbqa8a5.cloudfront.net/imagegen/max/ccr/1023/-/s3/digital-cougar-assets/whichcar/2019/09/02/-1/2019-Tesla-Model-3-Performance-review.jpg"))
    carList.add(Car("Tesla","Model S Long Range",629,245,3.7,249,294711, "https://bi.im-g.pl/im/65/04/14/z20989541IH,Tesla-Model-S.jpg"))
    carList.add(Car("Tesla","Model S Performance",560,262,2.3,262,373311, "https://wrc.net.pl/app/uploads/2019/11/model-s-vs-aventador-s-drag-race.jpg"))
    carList.add(Car("Tesla","Model X Long Range",565,245,4.4,249,314361, "http://elektromobilni.pl/assets/uploads/2019/09/TESLA_MODEL_X-1-768x514.png"))
    carList.add(Car("Tesla","Model X Performancne",491,262,2.6,262,392961, "https://d3lp4xedbqa8a5.cloudfront.net/imagegen/max/ccr/1023/-/s3/digital-cougar-assets/whichcar/2018/09/11/-1/2018-Tesla-Model-X-P100D-performance-review-feature.jpg"))
    carList.add(Car("Tesla","Model Y Long Range",509,245,4.8,217,208251, "https://www.auto-motor-i-sport.pl/media/lib/2636/tesla-model-y-0b53730b4bebb6b347120ca686e07dc3.jpg"))
    carList.add(Car("Tesla","Model Y Perfromance",507,258,3.5,233,239691, "https://www.autoefl.pl/files/2020-01/1579614962_teslay1.jpg"))

    carsReference.get().addOnSuccessListener { documents ->
        for(car in carList){
            carsReference.add(car)
        }
    }
}