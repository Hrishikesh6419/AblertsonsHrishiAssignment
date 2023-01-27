package com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.mapping

interface Mapper<I, O> {
    fun map(input: I): O
}