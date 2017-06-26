package creeper_san.weather.Event

class BingImageEvent(private val type:Int,private val url:String){

    fun getType():Int = type

    fun getUrl():String = url

    object Type{
        val TYPE_GET = 0
    }
}

