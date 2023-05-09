package com.albertsons.acronyms.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.albertsons.acronyms.exception.ExceptionType
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AcronymViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testException_Network() {

        // Given a fresh AcronymViewModel
        val acronymViewModel = AcronymViewModel(ApplicationProvider.getApplicationContext())

        val observer = Observer<ExceptionType> {}
        try {

            // Observe the LiveData forever
            acronymViewModel.eventError.observeForever(observer)

            // Simulate a network error
            acronymViewModel.setException(ExceptionType.NETWORK)

            // Then error event is triggered
            val value = acronymViewModel.eventError.value
            assertEquals(value, ExceptionType.NETWORK)

        } finally {
            acronymViewModel.eventError.removeObserver(observer)
        }
    }

    @Test
    fun testException_Data() {

        // Given a fresh AcronymViewModel
        val acronymViewModel = AcronymViewModel(ApplicationProvider.getApplicationContext())

        val observer = Observer<ExceptionType> {}
        try {

            // Observe the LiveData forever
            acronymViewModel.eventError.observeForever(observer)

            // Simulate a network error
            acronymViewModel.setException(ExceptionType.DATA)

            // Then the error event is triggered
            val value = acronymViewModel.eventError.value
            assertEquals(value, ExceptionType.DATA)

        } finally {
            acronymViewModel.eventError.removeObserver(observer)
        }
    }

    @Test
    fun testException_Unknown() {

        // Given a fresh AcronymViewModel
        val acronymViewModel = AcronymViewModel(ApplicationProvider.getApplicationContext())

        val observer = Observer<ExceptionType> {}
        try {

            // Observe the LiveData forever
            acronymViewModel.eventError.observeForever(observer)

            // Simulate a network error
            acronymViewModel.setException(ExceptionType.UNKNOWN)

            // Then the error event is triggered
            val value = acronymViewModel.eventError.value
            assertEquals(value, ExceptionType.UNKNOWN)

        } finally {
            acronymViewModel.eventError.removeObserver(observer)
        }
    }

    @Test
    fun testException_None() {

        // Given a fresh AcronymViewModel
        val acronymViewModel = AcronymViewModel(ApplicationProvider.getApplicationContext())

        val observer = Observer<ExceptionType> {}
        try {

            // Observe the LiveData forever
            acronymViewModel.eventError.observeForever(observer)

            // Simulate a network error
            acronymViewModel.setException(ExceptionType.NONE)

            // Then the error event is triggered
            val value = acronymViewModel.eventError.value
            assertEquals(value, ExceptionType.NONE)

        } finally {
            acronymViewModel.eventError.removeObserver(observer)
        }
    }
}