package com.doubleclick.rovleapp.core.platform.local

import com.doubleclick.rovleapp.AndroidApplication
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.config.TinkConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager

object CryptoManager {
    private const val KEYSET_NAME = "rovle_keyset"
    private const val PREF_FILE_NAME = "rovle_keyset_prefs"

    private val aead: Aead

    init {
        TinkConfig.register()
        aead = AndroidKeysetManager.Builder()
            .withSharedPref(AndroidApplication.applicationContext(), KEYSET_NAME, PREF_FILE_NAME)
            .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
            .build().keysetHandle.getPrimitive(Aead::class.java)
    }

    fun encrypt(input: ByteArray): ByteArray {
        return aead.encrypt(input, null)
    }

    fun decrypt(output: ByteArray): ByteArray {
        return if (output.isNotEmpty()) {
            aead.decrypt(output, null)
        } else {
            output
        }
    }
}
