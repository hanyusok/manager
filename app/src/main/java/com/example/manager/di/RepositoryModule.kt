package com.example.manager.di

import com.example.manager.repo.ProductRepository
import com.example.manager.repo.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

//    @Binds
//    abstract fun bindAuthenticateRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository
}