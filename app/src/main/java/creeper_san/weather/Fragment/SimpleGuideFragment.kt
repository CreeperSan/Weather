package creeper_san.weather.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import creeper_san.weather.Base.BaseFragment
import creeper_san.weather.R


class SimpleGuideFragment(private val text:String) :BaseFragment() {
    @BindView(R.id.fragmentSimpleGuideText)lateinit var textView:TextView

    fun setTextGravity(gravity:Int):SimpleGuideFragment{
        textView.gravity = gravity
        return this
    }

    override fun getLayout(): Int {
        return R.layout.fragment_simple_guide
    }

    override fun onCreateViewFinish() {
        textView.text = text
    }

}