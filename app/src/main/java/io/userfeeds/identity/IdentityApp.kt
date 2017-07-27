package io.userfeeds.identity

import android.app.Application

class IdentityApp : Application() {

    override fun onCreate() {
        super.onCreate()
        generateIdentity(this)
    }
}
