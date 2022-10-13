package com.benshapiro.composeexamples.repository

import com.benshapiro.composeexamples.data.DataOrException
import com.benshapiro.composeexamples.model.Person
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonsRepository @Inject constructor(
    private val queryPersonsByName: Query,
) {
    suspend fun getPersonsFromFirestore(): DataOrException<List<Person>, Exception> {
        val dataOrException = DataOrException<List<Person>, Exception>()
        try {
            dataOrException.data = queryPersonsByName.get().await().map { document ->
                document.toObject(Person::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }

    suspend fun deletePerson(person: Person) {
        Firebase.firestore.collection("people").document(person.id).delete()
        getPersonsFromFirestore()
    }

    suspend fun getPersonFromFirestore(id: String) : DataOrException<Person, Exception> {
        val dbPerson = Firebase.firestore.collection("people").document(id)
        val personData = DataOrException<Person, Exception>()
        try {
            personData.data = dbPerson.get().await().toObject(Person::class.java)
            } catch (e: FirebaseFirestoreException) {
                personData.e = e
        }
        return personData
    }

}