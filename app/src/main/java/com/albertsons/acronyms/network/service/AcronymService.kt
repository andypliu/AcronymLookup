package com.albertsons.acronyms.network.service

import com.albertsons.acronyms.network.data.response.AcronymDefinitionDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AcronymService {
    @GET(ServiceConstant.ACRONYM_DICTINARY_PATH)
    suspend fun getAcronymDefinition(@Query(value="sf") acronym : String): Response<List<AcronymDefinitionDTO>>
}