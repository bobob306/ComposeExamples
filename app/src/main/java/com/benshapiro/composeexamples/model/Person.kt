package com.benshapiro.composeexamples.model

import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

data class Person(
    @PrimaryKey (autoGenerate = true) @DocumentId val id: String = "",
    val firstName: String? = null,
    val lastName: String? = null,
    val age: Int? = null,
)
