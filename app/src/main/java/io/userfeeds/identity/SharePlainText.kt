package io.userfeeds.identity

import android.content.Context
import android.content.Intent

fun Context.sharePlainText(text: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_TEXT, KeyRepository(this).publicKeyHex)
    intent.type = "text/plain"
    startActivity(intent)
}
