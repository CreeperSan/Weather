package creeper_san.weather.Event


class WidgetEvent(val widgetType:Int,val eventType:Int) {




    object EventType{
        val TYPE_ADD = 0
        val TYPE_UPDATE = 1
        val TYPE_DELETE = 2
        val TYPE_DISABLE = 3
        val TYPE_ENABLE = 4
    }

    object WidgetType{
        val TYPE_BASE = 0
    }
}