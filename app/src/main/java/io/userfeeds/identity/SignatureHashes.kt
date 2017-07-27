package io.userfeeds.identity

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.security.keystore.KeyProperties
import java.security.MessageDigest

fun Context.signatureHashes(applicationId: String): List<String> {
    val packageInfo = packageManager.getPackageInfo(applicationId, PackageManager.GET_SIGNATURES)
    return packageInfo.signatures.map(Signature::sha256)
}

private val Signature.sha256: String get() {
    return with(MessageDigest.getInstance(KeyProperties.DIGEST_SHA256)) {
        update(toByteArray())
        digest().toHexString()
    }
}
