package io.userfeeds.identity

import android.content.Context
import java.security.KeyPairGenerator

fun generateIdentity(context: Context) {
    val repo = KeyRepository(context)
    if (repo.hasKey) {
        return
    }
    val secretKey = generateAesSecretKey()
    val (privateKey, publicKey) = generateEllipticCurveKeyPair()
    val (encryptedPrivateKey, iv) = encryptData(secretKey, privateKey)
    repo.store(encryptedPrivateKey, publicKey, iv)
}

private fun generateEllipticCurveKeyPair(): Pair<ByteArray, ByteArray> {
    return with(KeyPairGenerator.getInstance("EC")) {
        initialize(256)
        val kp = generateKeyPair()
        Pair(kp.private.encoded, kp.public.encoded)
    }
}
