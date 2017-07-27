package io.userfeeds.identity

internal fun byteArrayFromHexString(hex: String): ByteArray {
    val ret = ByteArray(hex.length / 2)
    for (i in 0 until ret.size) {
        val byteInHex = hex.substring(2 * i, 2 * i + 2).toUpperCase()
        ret[i] = hexToByteMap.getValue(byteInHex)
    }
    return ret
}

private val hexToByteMap by lazy {
    (0..255).associate { "%02X".format(it) to it.toByte() }
}

internal fun ByteArray.toHexString() = joinToString("") { byteToHexMap[it.toInt() and 0xFF] }

private val byteToHexMap by lazy {
    Array<String>(256) { "%02X".format(it) }
}
