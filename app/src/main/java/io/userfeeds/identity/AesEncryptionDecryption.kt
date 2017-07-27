package io.userfeeds.identity

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.*
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

internal fun generateAesSecretKey(): SecretKey {
    return with(KeyGenerator.getInstance(KEY_ALGORITHM_AES, "AndroidKeyStore")) {
        init(KeyGenParameterSpec.Builder("key1", PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                .setKeySize(256)
                .setBlockModes(BLOCK_MODE_CBC)
                .setEncryptionPaddings(ENCRYPTION_PADDING_PKCS7)
                .build())
        generateKey()
    }
}

internal fun encryptData(secretKey: SecretKey, data: ByteArray): Pair<ByteArray, ByteArray> {
    return with(Cipher.getInstance(TRANSFORMATION)) {
        init(Cipher.ENCRYPT_MODE, secretKey)
        Pair(doFinal(data), iv)
    }
}

internal fun decryptDataUsingKeyStore(data: ByteArray, iv: ByteArray): ByteArray {
    return with(KeyStore.getInstance("AndroidKeyStore")) {
        load(null)
        val secretKey = getKey("key1", null) as SecretKey
        decryptData(secretKey, data, iv)
    }
}

private fun decryptData(secretKey: SecretKey, data: ByteArray, iv: ByteArray): ByteArray {
    return with(Cipher.getInstance(TRANSFORMATION)) {
        init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
        doFinal(data)
    }
}

private const val TRANSFORMATION = "AES/CBC/PKCS7Padding"