package io.userfeeds.identity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exportView.setOnClickListener { exportIdentity() }
        importView.setOnClickListener { importIdentity() }
        copyToClipboardView.setOnClickListener { copyToClipboard() }
        shareView.setOnClickListener { share() }
    }

    override fun onResume() {
        super.onResume()
        publicKeyView.text = KeyRepository(this).publicKeyHex
    }

    private fun exportIdentity() {
        val intent = Intent(this, ExportIdentityActivity::class.java)
        startActivity(intent)
    }

    private fun importIdentity() {
        val intent = Intent(this, ImportIdentityActivity::class.java)
        startActivity(intent)
    }

    private fun copyToClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(null, KeyRepository(this).publicKeyHex)
        clipboard.primaryClip = clip
    }

    private fun share() {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT, KeyRepository(this).publicKeyHex)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }
}
