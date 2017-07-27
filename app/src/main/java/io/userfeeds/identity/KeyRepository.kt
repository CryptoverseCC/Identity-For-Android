package io.userfeeds.identity

import android.content.Context
import android.preference.PreferenceManager
import kotlin.LazyThreadSafetyMode.NONE

class KeyRepository(context: Context) {

    private val prefs by lazy(NONE) { PreferenceManager.getDefaultSharedPreferences(context) }

    val hasKey get() = prefs.contains(ENCRYPTED_PRIVATE_KEY_HEX)

    fun store(encryptedPrivateKey: ByteArray, publicKey: ByteArray, iv: ByteArray) {
        prefs.edit()
                .putString(ENCRYPTED_PRIVATE_KEY_HEX, encryptedPrivateKey.toHexString())
                .putString(PUBLIC_KEY_HEX, publicKey.toHexString())
                .putString(IV_HEX, iv.toHexString())
                .apply()
    }

    val encryptedPrivateKey get() = byteArrayFromHexString(prefs.getString(ENCRYPTED_PRIVATE_KEY_HEX, null))
    val publicKeyHex get() = prefs.getString(PUBLIC_KEY_HEX, null)
    val iv get() = byteArrayFromHexString(prefs.getString(IV_HEX, null))

    companion object {

        private const val ENCRYPTED_PRIVATE_KEY_HEX = "encryptedPrivateKeyHex"
        private const val PUBLIC_KEY_HEX = "publicKeyHex"
        private const val IV_HEX = "ivHex"
    }
}
