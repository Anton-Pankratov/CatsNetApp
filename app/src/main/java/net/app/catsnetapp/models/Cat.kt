package net.app.catsnetapp.models

data class Cat(
    val id: String?,
    val url: String?,
    var ext: String?,
    val breeds: List<Breed?>,
    val width: Int?,
    val height: Int?
) {

    init {
        ext = checkExtension()
    }

    fun getName(): String? {
        return getBreed()?.let {
            it.name ?: it.alt_names ?: "Cat#${it.id}"
        }
    }

    private fun getBreed(): Breed? {
        return if (!breeds.isNullOrEmpty() && breeds.size == 1) {
            breeds[0]
        } else if (breeds.size > 1) {
            breeds.random()
        } else null
    }

    private fun checkExtension(): String {
        return url?.substringAfterLast(".").let {
            when (it) {
                "jpg", "jpeg", "gif", "png" -> ".$it"
                else -> ".jpg"
            }
        }
    }
}