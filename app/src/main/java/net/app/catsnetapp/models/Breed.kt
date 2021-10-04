package net.app.catsnetapp.models

data class Breed(
    val id: String? = null,
    val name: String? = null,
    val temperament: String? = null,
    val life_span: String? = null,
    val alt_names: String? = null,
    val wikipedia_url: String? = null,
    val origin: String? = null,
    val weight_imperial: String? = null,
    val experimental: Int? = null,
    val hairless: Int? = null,
    val natural: Int? = null,
    val rare: Int? = null,
    val rex: Int? = null,
    val suppress_tail: Int? = null,
    val short_legs: Int? = null,
    val hypoallergenic: Int? = null,
    val adaptability: Int? = null,
    val affection_level: Int? = null,
    val country_code: String? = null,
    val child_friendly: Int? = null,
    val dog_friendly: Int? = null,
    val energy_level: Int? = null,
    val grooming: Int? = null,
    val health_issues: Int? = null,
    val intelligence: Int? = null,
    val shedding_level: Int? = null,
    val social_needs: Int? = null,
    val stranger_friendly: Int? = null,
    val vocalisation: Int? = null
) {

    fun getShowName(timeStamp: String?): String? {
        return name ?: alt_names?: timeStamp
    }
}