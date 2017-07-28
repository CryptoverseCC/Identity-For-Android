package io.userfeeds.identity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.export_identity_activity.*
import net.glxn.qrgen.android.QRCode


class ExportIdentityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.export_identity_activity)
        copyToClipboardView.setOnClickListener { copyToClipboard() }
        shareView.setOnClickListener { share() }
        qrCodeView.setImageBitmap(QRCode.from(privatePublicHex).withSize(512, 512).bitmap())
    }

    private fun copyToClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(null, privatePublicHex)
        clipboard.primaryClip = clip
    }

    private fun share() {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT, privatePublicHex)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    private val privatePublicHex by lazy {
        val repo = KeyRepository(this)
        val privateKey = decryptDataUsingKeyStore(repo.encryptedPrivateKey, repo.iv)
        val privateKeyHex = privateKey.toHexString()
        "$privateKeyHex:${repo.publicKeyHex}"
    }
}
