package io.userfeeds.identity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.import_identity_activity.*
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

class ImportIdentityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.import_identity_activity)
        submitView.setOnClickListener { submit() }
        scanQrCodeView.setOnClickListener { scanQrCode() }
    }

    private fun submit() {
        verifyAndSave(privatePublicHexView.text.toString().trim())
    }

    private fun scanQrCode() {
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (scanResult != null) {
            verifyAndSave(scanResult.contents)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun verifyAndSave(privatePublicHex: String) {
        val keys = privatePublicHex.split(':')
        if (keys.size != 2) {
            return
        }
        val privateKeyByteArray = byteArrayFromHexString(keys[0])
        val publicKeyByteArray = byteArrayFromHexString(keys[1])
        val keyFactory = KeyFactory.getInstance("EC")
        val privateKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(privateKeyByteArray))
        val signature = sign(privateKey, "Test message")
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(publicKeyByteArray))
        with (Signature.getInstance("SHA256WithECDSA")) {
            initVerify(publicKey)
            update("Test message".toByteArray())
            if (!verify(signature)) {
                return
            }
        }
        val (encryptedPrivateKey, iv) = encryptDataUsingKeyStore(privateKeyByteArray)
        KeyRepository(this).store(encryptedPrivateKey, publicKeyByteArray, iv)
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
    }
}
