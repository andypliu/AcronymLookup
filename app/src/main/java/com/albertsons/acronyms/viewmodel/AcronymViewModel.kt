package com.albertsons.acronyms.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.albertsons.acronyms.domain.AcronymDefinition

import com.albertsons.acronyms.exception.DataException
import com.albertsons.acronyms.exception.ExceptionType
import com.albertsons.acronyms.repository.AcronymsRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class AcronymViewModel(application: Application) : AndroidViewModel(application) {

    // Get the repository that store the data
    private val acronymsRepository = AcronymsRepository()

    // Acronyms definitions
    private var _acronymsDefinitions = acronymsRepository.acronymDefinitions
    val acronymsDefinitions: LiveData<List<AcronymDefinition>>
        get() = _acronymsDefinitions

    // Error Event
    private var _eventError = MutableLiveData(ExceptionType.NONE)
    val eventError: LiveData<ExceptionType>
        get() = _eventError

    private var _isErrorShown = MutableLiveData(false)

    /**
     * Look up acronym definition data from the repository. Use a coroutine launch to run in background thread.
     */
    fun lookupDefinition(acronym : String) {
        viewModelScope.launch {
            try {
                resetErrorState()
                acronymsRepository.lookupDefinition(acronym)
            } catch (networkError: IOException) {
                Timber.e(networkError)
                setException(ExceptionType.NETWORK)
            } catch (dataException : DataException) {
                Timber.e(dataException)
                setException(ExceptionType.DATA)
            } catch (e : Exception) {
                Timber.e(e)
                setException(ExceptionType.UNKNOWN)
            }
        }
    }

    fun setException(exceptionType : ExceptionType) {
        _eventError.value = exceptionType
    }

    private fun resetErrorState() {
        _eventError.value = ExceptionType.NONE
        _isErrorShown.value = false
    }

    fun onErrorShown() {
        _isErrorShown.value = true
    }

    /**
     * Factory for constructing AcronymViewModel with parameter
     */
    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AcronymViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AcronymViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct acronym view model")
        }
    }
}