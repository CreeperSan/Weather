package creeper_san.weather.Interface

import android.graphics.Bitmap
import okhttp3.Call
import okhttp3.Response
import java.io.IOException


interface HttpBitmapCallback {

    fun onFail(call:Call,e:IOException)
    fun onResult(call:Call,response: Response,bitmap:Bitmap)

}