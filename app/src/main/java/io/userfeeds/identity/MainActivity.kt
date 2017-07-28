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
        copyToClipboardView.setOnClickListener { copyToClipboard(publicKeyHex) }
        shareView.setOnClickListener { sharePlainText(publicKeyHex) }
    }

    override fun onResume() {
        super.onResume()
        publicKeyView.text = publicKeyHex
    }

    private fun exportIdentity() {
        val intent = Intent(this, ExportIdentityActivity::class.java)
        startActivity(intent)
    }

    private fun importIdentity() {
        val intent = Intent(this, ImportIdentityActivity::class.java)
        startActivity(intent)
    }

    private val publicKeyHex get() = KeyRepository(this).publicKeyHex
}
