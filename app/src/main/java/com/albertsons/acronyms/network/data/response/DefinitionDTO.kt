package com.albertsons.acronyms.network.data.response

import com.google.gson.annotations.SerializedName

data class DefinitionDTO (
    @SerializedName("lf")
    val text : String? = null
)