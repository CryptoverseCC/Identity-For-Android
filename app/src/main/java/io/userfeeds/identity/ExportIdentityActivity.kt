package io.userfeeds.identity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.export_identity_activity.*
import net.glxn.qrgen.android.QRCode


class ExportIdentityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.export_identity_activity)
        copyToClipboardView.setOnClickListener { copyToClipboard() }
        qrCodeView.setImageBitmap(QRCode.from(privatePublicHex).withSize(512, 512).bitmap())
    }

    private fun copyToClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(null, privatePublicHex)
        clipboard.primaryClip = clip
    }

    private val privatePublicHex: String get() {
        val repo = KeyRepository(this)
        val privateKey = decryptDataUsingKeyStore(repo.encryptedPrivateKey, repo.iv)
        val privateKeyHex = privateKey.toHexString()
        return "$privateKeyHex:${repo.publicKeyHex}"
    }
}
