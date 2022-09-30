package com.benshapiro.composeexamples

import android.content.Context
import android.widget.Toast

fun Context.toast(messageId: String) {
    Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
}