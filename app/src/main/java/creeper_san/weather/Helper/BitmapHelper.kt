package creeper_san.weather.Helper

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

object BitmapHelper {

    fun blur(bitmap: Bitmap,radius:Float,context: Context):Bitmap{
        val longLength = if (bitmap.width>bitmap.height) bitmap.width else bitmap.height
        val times = longLength.toFloat()/240f
        val output:Bitmap
        if (longLength>240){
            output = Bitmap.createBitmap((bitmap.width.toFloat()/times).toInt(), (bitmap.height.toFloat()/times).toInt(),Bitmap.Config.RGB_565)
        }else{
            output = Bitmap.createBitmap(bitmap.width,bitmap.height,Bitmap.Config.RGB_565)
        }

        val script = RenderScript.create(context)
        val gaussianBlur = ScriptIntrinsicBlur.create(script, Element.U8_4(script))
        val allIn = Allocation.createFromBitmap(script,bitmap)
        val allOut = Allocation.createFromBitmap(script,output)
        gaussianBlur.setRadius(radius)
        gaussianBlur.setInput(allIn)
        gaussianBlur.forEach(allOut)
        allOut.copyTo(output)
        bitmap.recycle()
        script.destroy()
        return output
    }

}