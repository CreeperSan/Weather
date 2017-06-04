package creeper_san.weather

import agency.tango.materialintroscreen.MaterialIntroActivity
import agency.tango.materialintroscreen.SlideFragment
import agency.tango.materialintroscreen.SlideFragmentBuilder
import agency.tango.materialintroscreen.widgets.SwipeableViewPager
import android.os.Bundle
import android.support.v4.view.CustomViewPager
import android.util.Log
import android.view.View
import android.widget.ImageButton

class IntroActivity : MaterialIntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ic_calm)
                .title("嗨！")
                .description("欢迎来带来到Weather")
                .build())
        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ic_haze)
                .title("在这里")
                .description("你可以定义你的天气主界面")
                .build())
        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ic_part_wind)
                .title("在这里")
                .description("你可以只专注于你的信息")
                .build())
        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ic_snow_flurry)
                .title("在这里")
                .description("没有扰人的广告")
                .build())
        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ic_haze)
                .title("欢迎使用")
                .description("打造专属于自己的天气")
                .build())
        init()
    }

    private fun init(){
        hideBackButton()
        enableLastSlideAlphaExitTransition(true)
        findViewById(R.id.button_next).alpha = 0f
    }

}


