package creeper_san.weather.Item

class VersionItem(private val version:Int,private val versionName:String,private val updateTime:String,private val description:String){

    public fun getVersion():Int{
        return version
    }

    public fun getVersionName():String{
        return versionName
    }

    public fun getUpdateTime():String{
        return updateTime
    }

    public fun getDescription():String{
        return description
    }

}
