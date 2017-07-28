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
        exportView.setOnClickListener { startActivity<ExportIdentityActivity>() }
        importView.setOnClickListener { startActivity<ImportIdentityActivity>() }
        copyToClipboardView.setOnClickListener { copyToClipboard(publicKeyHex) }
        shareView.setOnClickListener { sharePlainText(publicKeyHex) }
    }

    override fun onResume() {
        super.onResume()
        publicKeyView.text = publicKeyHex
    }

    private val publicKeyHex get() = KeyRepository(this).publicKeyHex
}
