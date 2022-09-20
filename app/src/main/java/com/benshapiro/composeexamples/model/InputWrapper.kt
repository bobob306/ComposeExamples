package com.benshapiro.composeexamples.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Parcelable so it can be saved inside savedStateHandle, helping with process death
@Parcelize
data class InputWrapper(
    val value: String = "",
    val errorId: String? = null
) : Parcelable
