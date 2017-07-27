package io.userfeeds.identity

data class Signature(
        val type: String,
        val value: String,
        val creator: String)
