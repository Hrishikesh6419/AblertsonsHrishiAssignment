package com.hrishikeshdarshan.ablertsonshrishiassignment.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.AcromineResponse
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.AcromineResponseItem
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.Lf
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.WordDetail
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.mapping.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DefaultAcromineRepositoryTest {

    private lateinit var repository: NetworkAcromineRepository

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var api: AcromineApi

    @Mock
    private lateinit var wordMapper: Mapper<AcromineResponse, List<WordDetail>>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = NetworkAcromineRepository(api, wordMapper, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given a getLongForms call, when response body is empty, then emit Error state`() {
        val shortForm = "randomWord"

        runTest {
            whenever(api.getLongForms(shortForm)).thenReturn(mockedAcrimoneEmptyResponse())
            assertEquals(EMPTY_RESPONSE, repository.getLongForms(shortForm).message)
        }
    }

    @Test
    fun `Given a getLongForms call, when response body is valid, then emit Success state`() {
        val shortForm = "hh"
        val mockedResponse = mockedAcrimoneNonEmptyResponse()
        val acromineResponse = ACRIMONE_VALID_RESPONSE
        val expectedResult = listOf(
            WordDetail(
                LONG_FORM_HH,
                LONG_FORM_HH_FREQUENCY,
                LONG_FORM_HH_SINCE
            )
        )

        runTest {
            whenever(api.getLongForms(shortForm)).thenReturn(mockedResponse)
            whenever(wordMapper.map(acromineResponse)).thenReturn(expectedResult)
            assertEquals(expectedResult, repository.getLongForms(shortForm).data)
        }
    }

    @Test
    fun `Given a getLongForms call, when an unknown exception occurs, then emit Success state`() {
        val shortForm = "hh"

        runTest {
            whenever(api.getLongForms(shortForm)).thenReturn(mockedAcrimoneErrorResponse())
            assertEquals(AN_ERROR_OCCURRED_MESSAGE, repository.getLongForms(shortForm).message)
        }
    }

    private fun mockedAcrimoneEmptyResponse(): Response<AcromineResponse> =
        Response.success(AcromineResponse())

    private fun mockedAcrimoneNonEmptyResponse(): Response<AcromineResponse> =
        Response.success(ACRIMONE_VALID_RESPONSE)

    private fun mockedAcrimoneErrorResponse(): Response<AcromineResponse> =
        Response.error(
            NETWORK_ERROR_CODE,
            SERVICE_DOWN_ERROR_BODY
        )

    companion object {
        private const val LONG_FORM_HH = "Hi Hi"
        private const val LONG_FORM_HH_FREQUENCY = 1
        private const val LONG_FORM_HH_SINCE = 1996
        private const val SHORT_FORM_HH = "HH"
        private const val NETWORK_ERROR_CODE = 404
        private const val EMPTY_RESPONSE = "Response is Empty"
        private const val AN_ERROR_OCCURRED_MESSAGE = "An Error Occurred"


        private val ACRIMONE_VALID_RESPONSE = AcromineResponse().also {
            it.add(AcromineResponseItem(
                listOf(LONG_FORM_HH_DETAIL),
                SHORT_FORM_HH
            ))
        }

        private val SERVICE_DOWN_ERROR_BODY = ResponseBody.create(
            null,
            AN_ERROR_OCCURRED_MESSAGE
        )

        private val LONG_FORM_HH_DETAIL = Lf(
            frequency = LONG_FORM_HH_FREQUENCY,
            longForm = LONG_FORM_HH,
            since = LONG_FORM_HH_SINCE,
            emptyList()
        )
    }
}