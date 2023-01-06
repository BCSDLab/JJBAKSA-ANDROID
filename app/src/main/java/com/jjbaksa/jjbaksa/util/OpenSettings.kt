package com.jjbaksa.jjbaksa.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract

class OpenSettings : ActivityResultContract<Unit?, Int>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        return resultCode
    }
}
