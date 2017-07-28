package io.userfeeds.identity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager.LayoutParams.FLAG_SECURE
import kotlinx.android.synthetic.main.export_identity_activity.*
import net.glxn.qrgen.android.QRCode


class ExportIdentityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(FLAG_SECURE, FLAG_SECURE)
        setContentView(R.layout.export_identity_activity)
        copyToClipboardView.setOnClickListener { copyToClipboard(privatePublicHex) }
        shareView.setOnClickListener { sharePlainText(privatePublicHex) }
        qrCodeView.setImageBitmap(QRCode.from(privatePublicHex).withSize(512, 512).bitmap())
    }

    private val privatePublicHex by lazy {
        val repo = KeyRepository(this)
        val privateKey = decryptDataUsingKeyStore(repo.encryptedPrivateKey, repo.iv)
        val privateKeyHex = privateKey.toHexString()
        "$privateKeyHex:${repo.publicKeyHex}"
    }
}
