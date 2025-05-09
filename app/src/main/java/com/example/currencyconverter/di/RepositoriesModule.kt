package com.example.currencyconverter.di

import com.example.currencyconverter.dao.CurrencyDao
import com.example.currencyconverter.network.CurrencyApiService
import com.example.currencyconverter.repositories.ConversionRepository
import com.example.currencyconverter.repositories.ConversionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

     @Provides
     @Singleton
     fun provideConversionRepository(
          currencyDao: CurrencyDao,
          currencyApiService: CurrencyApiService
     ): ConversionRepository {
          return ConversionRepositoryImpl(currencyDao, currencyApiService)
     }
}