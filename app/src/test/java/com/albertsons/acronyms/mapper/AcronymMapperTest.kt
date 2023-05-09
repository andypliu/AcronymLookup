package com.albertsons.acronyms.mapper

import com.albertsons.acronyms.network.data.response.AcronymDefinitionDTO
import com.albertsons.acronyms.network.data.response.DefinitionDTO
import org.junit.Assert.*

import org.junit.Test

class AcronymMapperTest {

    @Test
    fun asDefinitionDomainMode_TwoDefinitions() {
        val definitions = listOf(
            DefinitionDTO("Object Oriented Programming"),
            DefinitionDTO("Oh Oh Paul")
        )

        val result = definitions.asDefinitionDomainMode()

        //check the result
        assertEquals(result.size, 2)
        assertEquals(result[0].text, "Object Oriented Programming")
    }

    @Test
    fun asDomainMode_OneAcronym() {
        val definitions = listOf(
            DefinitionDTO("Object Oriented Programming"),
            DefinitionDTO("Oh Oh Paul")
        )

        val acronym = listOf(
            AcronymDefinitionDTO("OOP", definitions)
        )

        val result = acronym.asDomainMode()

        //check the result
        assertEquals(result.size, 1)
        assertEquals(result[0].acronym, "OOP")

        val definitionResult = result[0].definitions
        assertEquals(definitionResult?.size, 2)
        assertEquals(definitionResult?.get(1)?.text, "Oh Oh Paul")
    }
}