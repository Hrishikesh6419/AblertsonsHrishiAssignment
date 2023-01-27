package com.hrishikeshdarshan.ablertsonshrishiassignment

import com.hrishikeshdarshan.ablertsonshrishiassignment.data.AcromineRepository
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.WordDetail
import com.hrishikeshdarshan.ablertsonshrishiassignment.util.Resource

class FakeAcromineRepository : AcromineRepository {

    override suspend fun getLongForms(shortForm: String): Resource<List<WordDetail>> {
        return if (shortForm.isEmpty()) {
            Resource.Error("Response is Empty")
        } else {
            Resource.Success(listOfTestWords)
        }
    }

    companion object {
        val listOfTestWords = listOf(WordDetail(
            "Hi Hi",
            123,
            2019
        ))
    }
}