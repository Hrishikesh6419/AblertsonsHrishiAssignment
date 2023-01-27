package com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.mapping

import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.AcromineResponse
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.WordDetail

class WordListMapper : Mapper<AcromineResponse, List<WordDetail>> {

    override fun map(input: AcromineResponse): List<WordDetail> {
        return input[0].longForms.map {
            WordDetail(
                it.longForm,
                it.frequency,
                it.since
            )
        }
    }
}