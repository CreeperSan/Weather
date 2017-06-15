package creeper_san.weather

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import creeper_san.weather.Base.BaseActivity

class AboutActivity : BaseActivity() {
    private var toolbar: Toolbar? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = findViewById(R.id.infoToolbar) as Toolbar;
        setSupportActionBar(toolbar);
        val actionBar: ActionBar? = supportActionBar;
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("关于")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayout(): Int {
        return R.layout.activity_about
    }

}
