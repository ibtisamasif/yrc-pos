package com.yrc.pos.core

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.yrc.pos.R

class YrcFrameActivity : YrcBaseActivity() {

    private var inflateOptionsMenu = true
    private var fragmentName: String? = null
    private var activityTitle: String? = null

    companion object {
        const val FRAGMENT_NAME_STRING = "fragment_name"
        const val INFLATE_OPTIONS_MENU = "inflate_options_menu"
        const val ACTIVITY_TITLE = "activity_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
        setupActionBar()

        if (intent.extras != null) {
            activityTitle = intent?.extras?.getString(ACTIVITY_TITLE)
            fragmentName = intent?.extras?.getString(FRAGMENT_NAME_STRING)
            inflateOptionsMenu = intent?.extras?.getBoolean(INFLATE_OPTIONS_MENU) == true
        }

        if (fragmentName != null) {
            val fragment = Fragment.instantiate(applicationContext, fragmentName!!)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.containerFrameActivity, fragment)
            transaction.commit()
        }

        setActionBarTitle(activityTitle!!)
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar!!.title = title
    }

    private fun setupActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}