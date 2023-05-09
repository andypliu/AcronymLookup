package com.albertsons.acronyms.repository

import androidx.lifecycle.MutableLiveData
import com.albertsons.acronyms.domain.AcronymDefinition
import com.albertsons.acronyms.exception.DataException
import com.albertsons.acronyms.exception.ExceptionConstant
import com.albertsons.acronyms.mapper.asDomainMode
import com.albertsons.acronyms.network.service.AcronymService
import com.albertsons.acronyms.network.service.ServiceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AcronymsRepository {

    private var acronymService : AcronymService? = null

    init {
        acronymService = ServiceProvider.getAcronymService()
    }

    var acronymDefinitions = MutableLiveData<List<AcronymDefinition>>()

    suspend fun lookupDefinition(acronym : String) {
        withContext(Dispatchers.IO) {
            // Get acronym definitions from the network
            val response = acronymService?.getAcronymDefinition(acronym)
            val acronymDefinitionResult = response?.body()
                try {
                    acronymDefinitionResult?.asDomainMode().also { list ->
                        withContext(Dispatchers.Main) {
                            acronymDefinitions.value = list
                        }
                    }
                } catch (npe: java.lang.NullPointerException) {
                    throw DataException(ExceptionConstant.WRONG_DATA_FORMAT, npe)
                }
        }
    }
}