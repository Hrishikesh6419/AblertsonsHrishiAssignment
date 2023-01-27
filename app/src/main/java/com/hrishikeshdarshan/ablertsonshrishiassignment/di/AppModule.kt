package com.hrishikeshdarshan.ablertsonshrishiassignment.di

import com.hrishikeshdarshan.ablertsonshrishiassignment.data.AcromineApi
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.AcromineRepository
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.NetworkAcromineRepository
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.AcromineResponse
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.WordDetail
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.mapping.Mapper
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.mapping.WordListMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://www.nactem.ac.uk/software/acromine/"

    @Singleton
    @Provides
    fun provideAcromineApi(): AcromineApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AcromineApi::class.java)
    }

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideWordMapper(): Mapper<AcromineResponse, List<WordDetail>> = WordListMapper()

    @Singleton
    @Provides
    fun provideAcromineRepository(
        api: AcromineApi,
        mapper: Mapper<AcromineResponse, List<WordDetail>>,
        dispatcher: CoroutineDispatcher,
    ): AcromineRepository = NetworkAcromineRepository(api, mapper, dispatcher)
}