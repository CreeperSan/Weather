package creeper_san.weather.Event

import creeper_san.weather.Json.UpdateJson

class UpdateHistoryResultEvent(private val isSuccess:Boolean,private val json:UpdateJson?){

    public fun isSuccess():Boolean{
        return isSuccess
    }

    public fun getJson():UpdateJson?{
        return json
    }



}
