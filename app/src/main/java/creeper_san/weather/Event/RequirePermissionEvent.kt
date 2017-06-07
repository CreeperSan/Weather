package creeper_san.weather.Event

class RequirePermissionEvent(private val permission:String,private val typeID:Int){

    public fun getPermission():String{
        return permission
    }

    public fun getTypeID():Int{
        return typeID
    }

}
