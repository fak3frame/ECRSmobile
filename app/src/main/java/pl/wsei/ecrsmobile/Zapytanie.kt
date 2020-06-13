package pl.wsei.ecrsmobile

data class Zapytanie(
    val idZapytania: Int = 0,
    val userId: String = "",
    val marka: String = "",
    val zasiegOd: Int = 0,
    val zasiegDo: Int = 99999,
    val mocOd: Int = 0,
    val mocDo: Int = 99999,
    val cenaOd: Int = 0,
    val cenaDo: Int = 999999,
    val listaPasujacychAut: ArrayList<Car> = arrayListOf()
)