package com.almaz.mukatukha_drinks.di.module

import com.almaz.mukatukha_drinks.core.interfaces.CafeRepository
import com.almaz.mukatukha_drinks.core.interfaces.UserRepository
import com.almaz.mukatukha_drinks.data.repository.CafeRepositoryImpl
import com.almaz.mukatukha_drinks.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepoModule {
    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindCafeRepository(cafeRepositoryImpl: CafeRepositoryImpl): CafeRepository
}

