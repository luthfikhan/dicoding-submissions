package id.filab.storyapp.dto

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class StoryResponse (
    @SerializedName("error"     ) var error     : Boolean?             = null,
    @SerializedName("message"   ) var message   : String?              = null,
    @SerializedName("listStory" ) var listStory : ArrayList<ListStory> = arrayListOf()
)


@kotlinx.serialization.Serializable
data class ListStory (
    @SerializedName("id"          ) var id          : String? = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("photoUrl"    ) var photoUrl    : String? = null,
    @SerializedName("createdAt"   ) var createdAt   : String? = null,
    @SerializedName("lat"         ) var lat         : Double? = null,
    @SerializedName("lon"         ) var lon         : Double? = null
)
