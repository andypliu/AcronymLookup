package com.albertsons.acronyms.network.data.response

import com.google.gson.annotations.SerializedName

/*
  AcronymDefinition Data Transfer Object.  It is use to represent the object from JSON object from network
 */
data class AcronymDefinitionDTO(

    @SerializedName("sf")
    val acronym: String? = null,

    @SerializedName("lfs")
    val definitions: List<DefinitionDTO>? = null
)
