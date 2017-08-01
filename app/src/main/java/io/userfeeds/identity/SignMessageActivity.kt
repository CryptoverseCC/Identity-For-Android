package io.userfeeds.identity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.sign_message_activity.*
import kotlin.LazyThreadSafetyMode.NONE

class SignMessageActivity : AppCompatActivity() {

    private val message by lazy(NONE) { intent.getStringExtra("io.userfeeds.identity.message") }
    private val requestId by lazy(NONE) { intent.getStringExtra("io.userfeeds.identity.requestId") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_message_activity)
        if (WhitelistedAppsRepository(this).contains(callingPackage)) {
            finishWithSignature()
            return
        }
        val callingAppSignatures = signatureHashes(callingPackage)
        fromView.text = "Message from: $callingPackage (${callingAppSignatures.joinToString()})"
        messageView.text = message
        confirmView.setOnClickListener { confirm() }
    }

    private fun confirm() {
        if (doNotAskAgainView.isChecked) {
            WhitelistedAppsRepository(this).add(callingPackage)
        }
        finishWithSignature()
    }

    private fun finishWithSignature() {
        val signature = signMessage(this, message)
        val data = Intent()
                .putExtra("io.userfeeds.identity.message", message)
                .putExtra("io.userfeeds.identity.requestId", requestId)
                .putExtra("io.userfeeds.identity.signature.type", signature.type)
                .putExtra("io.userfeeds.identity.signature.value", signature.value)
                .putExtra("io.userfeeds.identity.signature.creator", signature.creator)
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
