package net.app.catsnetapp.models.requests

data class ImagesRequest(
    val size: String? = null,
    val mime_types: String? = null,
    val order: String? = null,
    val limit: Int? = null,
    val page: Int? = null,
    val category_ids: List<Int>? = null,
    val format: String? = null,
    val breed_id: String? = null
)
