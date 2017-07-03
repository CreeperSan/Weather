package creeper_san.weather.Fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import butterknife.BindView
import creeper_san.weather.Base.BaseFragment
import creeper_san.weather.R

class MultiChooseGuideFragment(private val title:String,private val valueList:ArrayList<String>) :BaseFragment(){
    @BindView(R.id.fragmentMultiChooseRecyclerView)lateinit var recyclerView:RecyclerView
    @BindView(R.id.fragmentMultiChooseTitle)lateinit var textView:TextView
    private var valueDataList:MutableList<Boolean>
    private lateinit var recyclerAdapter:MultiItemAdapter

    init {
        valueDataList = ArrayList<Boolean>()
        for (str in valueList){
            valueDataList.add(false)
        }
    }

    constructor(title: String, valueList: ArrayList<String>, booleanList:MutableList<Boolean>):this(title, valueList){
        valueDataList = booleanList
    }

    constructor(title: String, valueList: ArrayList<String>, default:Boolean):this(title, valueList){
        valueDataList.forEachIndexed { index, b -> valueDataList[index] = default  }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_multi_choose_guide
    }

    override fun onCreateViewFinish() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerAdapter = MultiItemAdapter()
        recyclerView.adapter = MultiItemAdapter()
        textView.text = title
    }

    fun getResultList():MutableList<Boolean>{
        return valueDataList
    }

    inner class MultiItemAdapter:RecyclerView.Adapter<MultiItemViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MultiItemViewHolder {
            return MultiItemViewHolder(activity.layoutInflater.inflate(R.layout.item_multichoose_guide_fragment,parent,false))
        }

        override fun getItemCount(): Int {
            return valueList.size
        }

        override fun onBindViewHolder(holder: MultiItemViewHolder, position: Int) {
            holder.checkBox.isChecked = valueDataList[position]
            holder.textView.text = valueList[position]
            holder.itemView.setOnClickListener {
                val pos = holder.adapterPosition
                valueDataList[pos] = !valueDataList[pos]
                holder.checkBox.isChecked = valueDataList[pos]
            }
        }

    }
    inner class MultiItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var checkBox:CheckBox
        var textView:TextView

        init {
            checkBox = itemView.findViewById(R.id.itemMultiChooseCheckBox) as CheckBox
            textView = itemView.findViewById(R.id.itemMultiChooseTextView) as TextView
        }

    }

}