package com.albertsons.acronyms.domain

data class AcronymDefinition(
    val acronym: String? = null,
    val definitions: List<Definition>? = null
)