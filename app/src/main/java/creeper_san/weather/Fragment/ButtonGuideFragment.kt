package creeper_san.weather.Fragment

import android.widget.Button
import butterknife.BindView
import creeper_san.weather.Base.BaseFragment
import creeper_san.weather.R


class ButtonGuideFragment(private val buttonTitle:String,private val runnable: Runnable):BaseFragment(){
    @BindView(R.id.fragmentButtonGuideButton)lateinit var button:Button

    override fun getLayout(): Int {
        return R.layout.fragment_button_guide
    }

    override fun onCreateViewFinish() {
        button.text = buttonTitle
        button.setOnClickListener { runnable.run() }
    }

}

