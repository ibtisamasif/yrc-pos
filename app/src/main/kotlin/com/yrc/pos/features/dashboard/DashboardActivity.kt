package com.yrc.pos.features.dashboard

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yrc.pos.R
import com.yrc.pos.core.TicketData
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.enums.Enclosure
import com.yrc.pos.core.views.YrcTextView
import kotlinx.android.synthetic.main.content_main.*

class DashboardActivity : YrcBaseActivity() {

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
        textViewHeaderTitle = supportActionBar?.customView?.findViewById(R.id.textViewTitle)

        bottom_nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))
    }

    private fun setEnclosure() {
        when (TicketData.enclosure) {
            Enclosure.grandstand -> {
                TicketData.enclosure = Enclosure.grandstand
                showGrandStand()
            }
            Enclosure.clocktower -> {
                TicketData.enclosure = Enclosure.clocktower
                showClockTower()
            }
            else -> {
            }
        }
    }

    private fun showGrandStand() {
        bottom_nav_view?.menu?.clear()
        bottom_nav_view?.inflateMenu(R.menu.bottom_nav_menu_grand_stand)
        setNavigationStartDestination(R.id.navigation_enclosure_grand_stand)
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
}