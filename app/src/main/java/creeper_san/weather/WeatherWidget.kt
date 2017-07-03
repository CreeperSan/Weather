package creeper_san.weather

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import creeper_san.weather.Event.WidgetEvent
import creeper_san.weather.Helper.DatabaseHelper
import creeper_san.weather.Helper.OfflineCacheHelper
import creeper_san.weather.Helper.ResHelper
import org.greenrobot.eventbus.EventBus

class WeatherWidget : AppWidgetProvider(){

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {//刷新时执行
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        postEvent(WidgetEvent(WidgetEvent.WidgetType.TYPE_BASE,WidgetEvent.EventType.TYPE_UPDATE))

    }
    companion object{
        fun setRemoteViewHintEmpty(remoteView:RemoteViews){
            remoteView.setImageViewResource(R.id.widgetWeatherImage,R.drawable.ic_unknown)
            remoteView.setTextViewText(R.id.widgetCityName,"尚未设置城市")
            remoteView.setTextViewText(R.id.widgetWeatherTextAndTemperature,"添加城市，快捷获取天气")
        }
        fun generateBaseWidgetRemoteView(context: Context):RemoteViews{
            val remoteView = RemoteViews(context.packageName,R.layout.widget_weather)
            val cityList = DatabaseHelper.getCityList(context)
            if(cityList.isEmpty()){
                setRemoteViewHintEmpty(remoteView)
            }else{
                val cityName = cityList[0].id
                if (cityName.isEmpty() || cityName.isBlank()){
                    setRemoteViewHintEmpty(remoteView)
                }else{
                    val weatherJson = OfflineCacheHelper.getWeatherItemFromCache(context,cityName)
                    remoteView.setImageViewResource(R.id.widgetWeatherImage,ResHelper.getWeatherImageWeatherCode(weatherJson?.getCode(0)))
                    remoteView.setTextViewText(R.id.widgetCityName,cityList[0].city)
                    remoteView.setTextViewText(R.id.widgetWeatherTextAndTemperature,
                            "${context.getString(ResHelper.getStringIDFromWeatherCode(weatherJson?.getCode(0)))} ${weatherJson?.getTmp(0)}℃")
                }
            }
            return remoteView
        }
    }

    override fun onAppWidgetOptionsChanged(context: Context, appWidgetManager: AppWidgetManager?, appWidgetId: Int, newOptions: Bundle?) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        context.startService(Intent(context,WeatherService::class.java))
        postEvent(WidgetEvent(WidgetEvent.WidgetType.TYPE_BASE,WidgetEvent.EventType.TYPE_DELETE))
    }

    override fun onRestored(context: Context, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
        context.startService(Intent(context,WeatherService::class.java))
        postEvent(WidgetEvent(WidgetEvent.WidgetType.TYPE_BASE,WidgetEvent.EventType.TYPE_DELETE))
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray?) {//小部件被删除
        super.onDeleted(context, appWidgetIds)
//        log("onDelete()")
        context.startService(Intent(context,WeatherService::class.java))
        postEvent(WidgetEvent(WidgetEvent.WidgetType.TYPE_BASE,WidgetEvent.EventType.TYPE_DELETE))
    }

    override fun onDisabled(context: Context) {//最后一个widget被删除
        super.onDisabled(context)
//        log("onDisable()")
        context.startService(Intent(context,WeatherService::class.java))
        postEvent(WidgetEvent(WidgetEvent.WidgetType.TYPE_BASE,WidgetEvent.EventType.TYPE_DELETE))
    }

    override fun onEnabled(context: Context) {//widget添加到屏幕上执行
        super.onEnabled(context)
//        log("onEnable()")
        context.startService(Intent(context,WeatherService::class.java))
        postEvent(WidgetEvent(WidgetEvent.WidgetType.TYPE_BASE,WidgetEvent.EventType.TYPE_DISABLE))
    }

    private fun postEvent(event:WidgetEvent){
        EventBus.getDefault().postSticky(event)
    }

    private fun log(content:String){
        Log.i("小部件",content)
    }
}