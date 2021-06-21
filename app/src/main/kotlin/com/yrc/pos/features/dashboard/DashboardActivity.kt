package com.yrc.pos.features.dashboard

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.session.Session
import com.yrc.pos.core.views.YrcTextView
import com.yrc.pos.features.login.login_service.Enclosure
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_main.*

class DashboardActivity : YrcBaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawerLayout: DrawerLayout? = null
    private var textViewHeaderTitle: YrcTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setEnclosure()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_layout)
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.header_background))
        textViewHeaderTitle = supportActionBar?.customView?.findViewById<YrcTextView>(R.id.textViewTitle)

        bottom_nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))
        navigationDrawerView.setupWithNavController(findNavController(R.id.nav_host_fragment))

        drawerLayout = findViewById(R.id.drawer_layout)
        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment), drawerLayout)
        navigationDrawerView.setNavigationItemSelectedListener(this)

        setNavigationDrawerHeaderData()
    }

    private fun setEnclosure() {
        intent.extras?.let {
            when (it.getSerializable(ENCLOSURE) as Enclosure?) {
                Enclosure.grandstand -> {
                    showGrandStand()
                }
                Enclosure.clocktower -> {
                    showClockTower()
                }
            }
        }
    }

    private fun showGrandStand() {
        bottom_nav_view?.menu?.clear()
        bottom_nav_view?.inflateMenu(R.menu.bottom_nav_menu_g_p)
        setNavigationStartDestination(R.id.navigation_enclosure_g_and_p)
    }

    private fun showClockTower() {
        bottom_nav_view?.menu?.clear()
        bottom_nav_view?.inflateMenu(R.menu.bottom_nav_menu_clock_tower)
        setNavigationStartDestination(R.id.navigation_enclosure_clock_tower)
    }

    private fun setNavigationStartDestination(destination: Int) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val graphInflater = navHostFragment?.navController?.navInflater
        val navGraph = graphInflater?.inflate(R.navigation.dashboard_nav_graph)
        val navController = navHostFragment?.navController
        navGraph?.startDestination = destination
        if (navGraph != null) {
            navController?.graph = navGraph
        }
    }

    private fun setNavigationDrawerHeaderData() {

        val headerView = navigationDrawerView.getHeaderView(0)
        val textViewUserName = headerView.findViewById(R.id.textView_userName) as YrcTextView
        val imageViewUserPhoto = headerView.findViewById(R.id.imageView_userPhoto) as ImageView
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(drawerLayout) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        menuItem.isChecked = true
        drawerLayout?.closeDrawers()

        when (menuItem.itemId) {
            R.id.item_sign_out -> {
                Session.clearSession()
            }
        }
        return true
    }

    companion object {
        const val ENCLOSURE = "enclosure"
    }
}