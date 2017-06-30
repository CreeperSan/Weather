package creeper_san.weather

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import creeper_san.weather.Base.BaseActivity
import creeper_san.weather.Event.UpdateRequestEvent
import creeper_san.weather.Event.UpdateResultEvent
import creeper_san.weather.Item.VersionItem
import creeper_san.weather.Json.UpdateJson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

open class VersionHistoryActivity : BaseActivity(),View.OnClickListener {

    private var toolbar: Toolbar? = null
    private var recyclerView:RecyclerView? = null
    private var progressBar:ProgressBar? = null
    private var loadingLayout:LinearLayout? = null
    private var loadingText:TextView? = null
    private var versionList:MutableList<VersionItem> = mutableListOf<VersionItem>()
    private var adapter:VersionAdapter = VersionAdapter();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = findViewById(R.id.versionHistoryToolbar) as Toolbar
        recyclerView = findViewById(R.id.versionHistoryRecyclerView) as RecyclerView
        progressBar = findViewById(R.id.versionHistoryLoadingProgressBar) as ProgressBar
        loadingLayout = findViewById(R.id.versionHistoryLoadingLayout) as LinearLayout
        loadingText = findViewById(R.id.versionHistoryLoadingText) as TextView
        setSupportActionBar(toolbar)
        title = "版本更新历史"
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        loadingText?.setOnClickListener(this)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
        //发送请求
        postEvent(UpdateRequestEvent(UpdateRequestEvent.TYPE_CHECK_UPDATE_HISTORY))
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onUpdateResultEvent(event:UpdateResultEvent){
        versionList.clear()
        if(event.isSuccess){
            val json = event.updateJson
            if(event.updateJson.isHistory){
                for (i in 0..json.versionCount()-1){
                    versionList.add(VersionItem(json.getVersion(i),json.getVersionName(i),json.getUpdateTime(i),json.getDescription(i)))
                }
            }
            adapter.notifyDataSetChanged()
            loadingLayout?.visibility = View.GONE
        }else{
            progressBar?.visibility = View.GONE
            loadingText?.isClickable = true
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_version_history
    }
    override fun onClick(v: View?) {
        loadingText?.isClickable = false
        postEvent(UpdateRequestEvent(UpdateRequestEvent.TYPE_CHECK_UPDATE_HISTORY))
    }


    inner class VersionAdapter : RecyclerView.Adapter<VersionHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VersionHolder {
            return VersionHolder(layoutInflater.inflate(R.layout.item_version_history,parent,false))
        }

        override fun onBindViewHolder(holder: VersionHolder, position: Int) {
            val item = versionList.get(position)
            val descriptionBuilder = StringBuilder("")
            val descriptionItemArray = item.getDescription().split("+")
            for (descriptItem in descriptionItemArray){
                if (descriptItem.isNotEmpty() && descriptItem.isNotBlank()){
                    descriptionBuilder.append("· $descriptItem \n")
                }
            }
            holder.getDescription()?.text = descriptionBuilder.toString()
            holder.getTime()?.text = item.getUpdateTime()
            holder.getTitle()?.text = item.getVersionName()
        }

        override fun getItemCount(): Int {
            return versionList.size
        }

    }
    inner class VersionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title:TextView? = null
        private var time:TextView? = null
        private var description:TextView? = null

        init {
            title = itemView.findViewById(R.id.itemVersionHistoryTitle) as TextView
            time = itemView.findViewById(R.id.itemVersionHistoryTime) as TextView
            description = itemView.findViewById(R.id.itemVersionHistoryDescription) as TextView
        }

        public fun getTitle():TextView?{
            return title
        }

        public fun getTime():TextView?{
            return time
        }

        public fun getDescription():TextView?{
            return description
        }
    }
}
