package creeper_san.weather

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import butterknife.OnPageChange
import com.airbnb.lottie.LottieAnimationView
import creeper_san.weather.Base.BaseActivity
import creeper_san.weather.Base.BaseFragment
import creeper_san.weather.Event.PartEditEvent
import creeper_san.weather.Event.WeatherRequestEvent
import creeper_san.weather.Fragment.ButtonGuideFragment
import creeper_san.weather.Fragment.MultiChooseGuideFragment
import creeper_san.weather.Fragment.SimpleGuideFragment
import creeper_san.weather.Fragment.SingleChooseGuideFragment
import creeper_san.weather.Helper.ConfigHelper
import creeper_san.weather.Helper.DatabaseHelper
import creeper_san.weather.Helper.ResHelper
import creeper_san.weather.Item.PartItem
import org.greenrobot.eventbus.EventBus

class GuideActivity: BaseActivity() {
    val viewPager:ViewPager by lazy { findViewById(R.id.guideViewPager) as ViewPager }
    val lottieView:LottieAnimationView by lazy { findViewById(R.id.guideLottieAnimationView) as LottieAnimationView }

    var isFirstBoot = false
    val fragmentList:MutableList<BaseFragment> = ArrayList<BaseFragment>()
    val guidePageAdapter by lazy { GuidePageAdapter() }

    lateinit var partFragment:MultiChooseGuideFragment
    lateinit var themeFragment:SingleChooseGuideFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFirstBoot = intent.getBooleanExtra("isFirstBoot",false)
        initFragment()
        viewPager.adapter = guidePageAdapter
        viewPager.addOnPageChangeListener(GuideOnPageChangeListener())
    }

    inner class GuideOnPageChangeListener:ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            lottieView.progress = position.toFloat()*0.2f + 0.2f*positionOffset
        }
        override fun onPageSelected(position: Int) {}
    }

    private fun initFragment() {
        fragmentList.add(SimpleGuideFragment("嗨"))
        fragmentList.add(SimpleGuideFragment("这是一个普通天气的App"))
        fragmentList.add(SimpleGuideFragment("但是使用前我们想知道点事情"))
        val list = ArrayList<String>()
        list.add("纯文字")
        list.add("卡片")
        themeFragment = SingleChooseGuideFragment("你喜欢怎么样的风格呢？",list,1)
        fragmentList.add(themeFragment)
        //添加天气部件界面
        val stringList = ArrayList<String>()
        (0..6).mapTo(stringList) { ResHelper.getPartNameFromCode(this, it) }
        partFragment = MultiChooseGuideFragment("您想了解那些数据呢？",stringList,true)
        fragmentList.add(partFragment)
        //结束界面
        fragmentList.add(ButtonGuideFragment("让我们开始吧", Runnable {
            val resultList = partFragment.getResultList()

            //天气部分
            for ((index,result) in resultList.withIndex()){
                if(result){
                    DatabaseHelper.insertPartItem(this, PartItem(this,index,ResHelper.getPartNameFromCode(this,index)))
                }
            }

            //主题选择部分
            if(themeFragment.getSelection() == 0){
                AlertDialog.Builder(this).setTitle("我有个小小的建议").setMessage("您当前选择的主题是我们进行Beta开发时用的测试主题，使用起来体验并不是很好，需要我们帮你换成卡片式主题吗？")
                        .setPositiveButton("好的",{dialog, which ->
                            ConfigHelper.settingSetAllPartTheme(context,"1")
                            postEvent(PartEditEvent(PartEditEvent.EDIT_REORDER))
                            finish()
                        })
                        .setNegativeButton("不用了",{dialog, which -> finish() })
                        .show()
            }else{
                ConfigHelper.settingSetAllPartTheme(context,"1")
                postEvent(PartEditEvent(PartEditEvent.EDIT_REORDER))
                finish()
            }
        }))
    }

    override fun onBackPressed() {}

    override fun getLayout(): Int {
        return R.layout.acivity_guide
    }

    inner class GuidePageAdapter:FragmentStatePagerAdapter(supportFragmentManager){
        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

    }
}