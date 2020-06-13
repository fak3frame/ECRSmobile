package pl.wsei.ecrsmobile

data class Car(
    val marka: String = "",
    val model: String = "",
    val zasieg: Int = 0,
    val moc: Int = 0,
    val przyspieszenie: Double = 0.0,
    val predkoscMaksymalna: Int = 0,
    val cena: Int = 0,
    val imageUrl: String = ""
)