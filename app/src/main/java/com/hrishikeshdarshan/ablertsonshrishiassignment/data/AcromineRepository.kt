package com.hrishikeshdarshan.ablertsonshrishiassignment.data

import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.AcromineResponse
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.WordDetail
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.mapping.Mapper
import com.hrishikeshdarshan.ablertsonshrishiassignment.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AcromineRepository {
    suspend fun getLongForms(shortForm: String): Resource<List<WordDetail>>
}

class NetworkAcromineRepository @Inject constructor(
    private val api: AcromineApi,
    private val wordMapper: Mapper<AcromineResponse, List<WordDetail>>,
    private val dispatcher: CoroutineDispatcher,
) : AcromineRepository {

    override suspend fun getLongForms(shortForm: String): Resource<List<WordDetail>> {
        return try {
            withContext(dispatcher) {
                val response = api.getLongForms(shortForm)
                val result =
                    response.body() ?: return@withContext Resource.Error(AN_ERROR_OCCURRED_MESSAGE)

                when {
                    result.isEmpty() -> Resource.Error(EMPTY_RESPONSE_MESSAGE)
                    response.isSuccessful -> Resource.Success(mapWords(result))
                    else -> Resource.Error(response.message())
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: AN_ERROR_OCCURRED_MESSAGE)
        }
    }

    private fun mapWords(result: AcromineResponse): List<WordDetail> {
        return wordMapper.map(result)
    }

    companion object {
        const val AN_ERROR_OCCURRED_MESSAGE = "An Error Occurred"
        const val EMPTY_RESPONSE_MESSAGE = "Response is Empty"
    }
}