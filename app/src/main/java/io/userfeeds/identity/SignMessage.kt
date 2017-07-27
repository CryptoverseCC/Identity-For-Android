package io.userfeeds.identity

import android.content.Context
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec

fun signMessage(context: Context, message: String): Signature {
    val repo = KeyRepository(context)
    val privateKey = decryptDataUsingKeyStore(repo.encryptedPrivateKey, repo.iv)
    val key = KeyFactory.getInstance("EC").generatePrivate(PKCS8EncodedKeySpec(privateKey))
    val signature = sign(key, message)
    return Signature("ecdsa.secp256r1", signature.toHexString(), repo.publicKeyHex)
}

private fun sign(key: PrivateKey, message: String): ByteArray {
    return with(java.security.Signature.getInstance("SHA256WithECDSA")) {
        initSign(key)
        update(message.toByteArray())
        sign()
    }
}
