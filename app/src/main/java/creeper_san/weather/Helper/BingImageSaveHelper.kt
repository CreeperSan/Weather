package creeper_san.weather.Helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import creeper_san.weather.Event.BingImageEvent
import org.greenrobot.eventbus.EventBus
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*


object BingImageSaveHelper {
    private val bingPicPath = "bingPic"

    fun isNewestPicture(context: Context):Boolean{
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        val updateTimeValue = "$year$month$day"
        println("记录的时间 : ${ConfigHelper.settingGetBingImageUpdateDate(context)}")
        println("当前理论的时间 : $updateTimeValue")
        return ConfigHelper.settingGetBingImageUpdateDate(context) != updateTimeValue
    }

    fun getNewestPicture(url:String){
        EventBus.getDefault().postSticky(BingImageEvent(BingImageEvent.Type.TYPE_GET,url))
    }

    fun clearCache(context: Context){
        ConfigHelper.settingSetBingImageUpdateDate(context,"")
    }

    fun savePictureAsNewest(picData:Bitmap,context: Context){
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        val updateTimeValue = "$year$month$day"
        ConfigHelper.settingSetBingImageUpdateDate(context,updateTimeValue)
        //保存文件
        val fos = context.openFileOutput(bingPicPath,Context.MODE_PRIVATE)
        val bos = ByteArrayOutputStream()
        picData.compress(Bitmap.CompressFormat.JPEG,100,bos)
        fos.write(bos.toByteArray())
        fos.flush()
        fos.close()
        println("来自Kotlin，图片保存成功")
    }

    fun getCacheBingPicData(context: Context):Bitmap?{
        return BitmapFactory.decodeStream(context.openFileInput(bingPicPath))
    }

    private fun writeFile(picData: String,file: File){
        val fileWriter = FileWriter(file)
        fileWriter.write(picData)
        fileWriter.flush()
        fileWriter.close()
    }

}