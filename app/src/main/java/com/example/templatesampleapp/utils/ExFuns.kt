package com.example.templatesampleapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import java.text.DecimalFormat

/**
 * @author Umer Bilal
 * Created 11/10/2023 at 10:31 AM
 */


// Generic fun to show logs
inline fun <reified T> T.showLog(msg: String, tag: String = this!!::class.java.simpleName) {
    Log.e(tag, msg)
}

fun getChuckerInterCeptor(context: Context): ChuckerInterceptor {

// Create the Collector
    val chuckerCollector = ChuckerCollector(
        context = context,
        // Toggles visibility of the notification
        showNotification = true,
        // Allows to customize the retention period of collected data
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

// Create the Interceptor
    val chuckerInterceptor = ChuckerInterceptor.Builder(context)
        // The previously created Collector
        .collector(chuckerCollector)
        // The max body content length in bytes, after this responses will be truncated.
        .maxContentLength(250_000L)
        // Read the whole response body even when the client does not consume the response completely.
        // This is useful in case of parsing errors or when the response body
        // is closed before being read like in Retrofit with Void and Unit types.
        .alwaysReadResponseBody(true)
        // Use decoder when processing request and response bodies. When multiple decoders are installed they
        // are applied in an order they were added.
        //  .addBodyDecoder(decoder)
        // Controls Android shortcut creation.
        // .createShortcut(true)
        .build()
    return chuckerInterceptor
}


fun Context.showToast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

fun Double.roundToOneDecimalPlace(): String {
    val decimalFormat = DecimalFormat("#.###")
    return decimalFormat.format(this)
}
