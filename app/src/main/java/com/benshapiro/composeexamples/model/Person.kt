package com.benshapiro.composeexamples.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    @PrimaryKey (autoGenerate = true) @DocumentId val id: String = "",
    val firstName: String? = null,
    val lastName: String? = null,
    val age: Int? = null,
) : Parcelable
