package net.app.catsnetapp.models

data class Cat(
    val id: String?,
    val url: String?,
    val breeds: List<Breed?>,
    val width: Int?,
    val height: Int?
) {

    fun getBreedIfSingle(): Breed? {
        return if (!breeds.isNullOrEmpty() && breeds.size == 1)
            breeds[1] else null
    }
}