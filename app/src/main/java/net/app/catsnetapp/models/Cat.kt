package net.app.catsnetapp.models

data class Cat(
    val id: String?,
    val url: String?,
    val breeds: List<Breed?>,
    val width: Int?,
    val height: Int?
) {

    fun getBreed(): Breed? {
        return if (!breeds.isNullOrEmpty() && breeds.size == 1) {
            breeds[0]
        } else if (breeds.size > 1) {
            breeds.random()
        } else null
    }
}