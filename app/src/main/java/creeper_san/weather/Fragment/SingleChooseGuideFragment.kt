package creeper_san.weather.Fragment

import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import butterknife.BindView
import creeper_san.weather.Base.BaseFragment
import creeper_san.weather.R

class SingleChooseGuideFragment(private val title:String,private val dataList:List<String>):BaseFragment(){
    @BindView(R.id.fragmentSingleRecyclerView)lateinit var recyclerView:RecyclerView
    @BindView(R.id.fragmentSingleChooseTitle)lateinit var textView:TextView

    constructor(title: String, dataList: List<String>, default:Int) : this(title,dataList) {
        selected = default
    }

    private var selected = 0
    private lateinit var adapter:SingleChooseAdapter

    override fun onCreateViewFinish() {
        textView.text = title
        adapter = SingleChooseAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
    fun getSelection():Int{
        return selected
    }

    override fun getLayout(): Int {
        return R.layout.fragment_single_choose_guide
    }

    inner class SingleChooseAdapter():RecyclerView.Adapter<SingleChooseViewHolder>(){
        override fun onBindViewHolder(holder: SingleChooseViewHolder, position: Int) {
            holder.radioButton.isChecked = position == selected
            holder.radioButton.text = dataList[position]
            holder.itemView.setOnClickListener {
                val pos = holder.adapterPosition
                val prePos = selected
                selected = pos
                adapter.notifyItemChanged(pos)
                adapter.notifyItemChanged(prePos)
            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SingleChooseViewHolder {
            return SingleChooseViewHolder(activity.layoutInflater.inflate(R.layout.item_singlechoose_guide_fragment,parent,false))
        }

    }
    inner class SingleChooseViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        lateinit var radioButton:RadioButton

        init {
            radioButton = itemView.findViewById(R.id.itemSingleChooseRadioButton) as RadioButton
        }

    }

}