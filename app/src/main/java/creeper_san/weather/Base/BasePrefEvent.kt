package creeper_san.weather.Base

open class BasePrefEvent(private val current:String, private val origin:String, private val key:String){
    public fun getCurrent():String{
        return current
    }

    public fun getOrigin():String{
        return origin
    }

    public fun getKey():String{
        return key
    }
}
