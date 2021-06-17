package com.yrc.pos.features.signup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.session.User
import com.yrc.pos.features.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.activity_hello_user.*

class HelloUserActivity : YrcBaseActivity() {

    private var mDelayHandler: Handler? = null
    private val ANIMATIONS_DELAY: Long = 500

    private lateinit var robotAnimation: Animation
    private lateinit var userNameAnimation: Animation
    private lateinit var helloBubbleAnimation: Animation
    private lateinit var whatsYourNameAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_user)

        loadAnimations()
        setUserNameTextChangedListener()
        startAnimationsWithDelay()
    }

    private fun startAnimationsWithDelay() {
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, ANIMATIONS_DELAY)
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            startRobotAnimations()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
    }

    private fun loadAnimations() {
        robotAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_from_left)
        helloBubbleAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_from_top)
        userNameAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom)
        whatsYourNameAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom)

        robotAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                startHelloBubbleAnimation()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        helloBubbleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                startWhatsYourNameAnimation()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        whatsYourNameAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                startUserNameAnimation()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    private fun startRobotAnimations() {
        imageView_robot.visibility = View.VISIBLE
        imageView_robot.startAnimation(robotAnimation)
    }

    private fun startHelloBubbleAnimation() {
        imageView_helloBubble.visibility = View.VISIBLE
        imageView_helloBubble.startAnimation(helloBubbleAnimation)
    }

    private fun startWhatsYourNameAnimation() {
        textView_what.visibility = View.VISIBLE
        textView_IsYourName.visibility = View.VISIBLE
        textView_what.startAnimation(whatsYourNameAnimation)
        textView_IsYourName.startAnimation(whatsYourNameAnimation)
    }

    private fun startUserNameAnimation() {
        editText_userName.visibility = View.VISIBLE
        editText_userName.startAnimation(userNameAnimation)
    }

    private fun setUserNameTextChangedListener() {

        editText_userName.setTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(content: Editable) {}

            override fun beforeTextChanged(content: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(content: CharSequence, start: Int, before: Int, count: Int) {
                if (content.isNotEmpty()) {
                    button_enter.visibility = View.VISIBLE
                } else {
                    button_enter.visibility = View.INVISIBLE
                }
            }
        })
    }

    fun onEnterButtonClicked(enterButton: View) {
        val dashboardIntent = Intent(this, DashboardActivity::class.java)
        dashboardIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(dashboardIntent)
        finish()
    }
}