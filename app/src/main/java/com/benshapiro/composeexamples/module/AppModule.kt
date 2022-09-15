package com.benshapiro.composeexamples.module

import com.benshapiro.composeexamples.data.DataOrException
import com.benshapiro.composeexamples.model.Person
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideQueryProductsByName() = FirebaseFirestore.getInstance()
        .collection("people")
        .orderBy("firstName", Query.Direction.DESCENDING)

    @Provides
    fun provideDataOrException(): DataOrException<List<Person>, Exception> = DataOrException()
}