package creeper_san.weather.Event

import android.graphics.Bitmap

class BingImageResultEvent(private val type:Int,private val imageData:Bitmap?){

    fun getType() = type
    fun getImageData() = imageData


    object ResultType{
        val TYPE_SUCCESS = 0
        val TYPE_FAIL = -1
    }

}