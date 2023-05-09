package com.albertsons.acronyms.mapper

import com.albertsons.acronyms.domain.AcronymDefinition
import com.albertsons.acronyms.domain.Definition
import com.albertsons.acronyms.network.data.response.AcronymDefinitionDTO
import com.albertsons.acronyms.network.data.response.DefinitionDTO

/**
 * Map acronym DTO to domain model
 */
fun List<AcronymDefinitionDTO>.asDomainMode(): List<AcronymDefinition> {
    return map {
        AcronymDefinition(
            acronym = it.acronym,
            definitions = it.definitions?.asDefinitionDomainMode()
        )
    }
}

fun List<DefinitionDTO>.asDefinitionDomainMode(): List<Definition> {
    return map {
        Definition(
            text = it.text
        )
    }
}