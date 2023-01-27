package com.hrishikeshdarshan.ablertsonshrishiassignment.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hrishikeshdarshan.ablertsonshrishiassignment.FakeAcromineRepository
import com.hrishikeshdarshan.ablertsonshrishiassignment.FakeAcromineRepository.Companion.listOfTestWords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val repository = FakeAcromineRepository()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(repository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given fetchLongForms action, when response is successful, then post Success event`() {
        val shortForm = "HH"

        runTest {
            viewModel.fetchLongForms(shortForm)
            advanceUntilIdle()
            val actualResult = viewModel.acromineEvent.value as HomeViewModel.AcromineEvent.Success
            assertEquals(listOfTestWords, actualResult.response)
        }
    }

    @Test
    fun `Given fetchLongForms action, when response is not successful, then post Error event`() {
        val shortForm = ""
        val expectedErrorMessage = "Response is Empty"

        runTest {
            viewModel.fetchLongForms(shortForm)
            advanceUntilIdle()
            val actualResult = viewModel.acromineEvent.value as HomeViewModel.AcromineEvent.Failure
            assertEquals(expectedErrorMessage, actualResult.errorMessage)
        }
    }
}