package io.userfeeds.identity

import android.content.Context
import android.preference.PreferenceManager
import kotlin.LazyThreadSafetyMode.NONE

class WhitelistedAppsRepository(private val context: Context) {

    private val prefs by lazy(NONE) { PreferenceManager.getDefaultSharedPreferences(context) }

    fun contains(applicationId: String): Boolean {
        return asWhitelistable(applicationId) in whitelistedApps
    }

    fun add(applicationId: String) {
        val newWhitelistedApps = whitelistedApps + asWhitelistable(applicationId)
        prefs.edit().putStringSet(WHITELISTED_APPS_KEY, newWhitelistedApps).apply()
    }

    private fun asWhitelistable(applicationId: String): String {
        val callingAppSignatures = context.signatureHashes(applicationId)
        return "$applicationId:${callingAppSignatures.joinToString(separator = ":")}"
    }

    private val whitelistedApps get() = prefs.getStringSet(WHITELISTED_APPS_KEY, emptySet())

    companion object {

        private const val WHITELISTED_APPS_KEY = "whitelistedApps"
    }
}
