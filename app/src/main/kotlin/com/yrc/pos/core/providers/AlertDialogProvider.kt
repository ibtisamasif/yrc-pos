package com.yrc.pos.core.providers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.yrc.pos.R
import com.yrc.pos.core.enums.DialogTheme
import com.yrc.pos.core.listeners.DialogButtonClickListener
import com.yrc.pos.core.views.YrcButton
import com.yrc.pos.core.views.YrcTextView

@SuppressLint("StaticFieldLeak")
object AlertDialogProvider {

    private var negativeButton: TextView? = null
    private var positiveButton: YrcButton? = null
    private var textViewTitle: YrcTextView? = null
    private var textViewMessage: YrcTextView? = null

    fun showFailureDialog(context: Context, theme: DialogTheme){
        val dialogView = getView(context, theme)
        initializeViews(dialogView)
        textViewTitle!!.text = context.getString(R.string.error)
        textViewMessage!!.text = context.getString(R.string.sorry_something_went_wrong)
        val alertDialog = createAlertDialog(context, dialogView)
        positiveButton!!.setOnClickListener { alertDialog.dismiss() }
    }

    fun showAlertDialog(context: Context, theme: DialogTheme, message: String?) {
        val dialogView = getView(context, theme)
        initializeViews(dialogView)
        textViewMessage!!.text = message
        val alertDialog = createAlertDialog(context, dialogView)
        positiveButton!!.setOnClickListener { alertDialog.dismiss() }
    }

    fun showAlertDialog(context: Context, theme: DialogTheme, message: String?, positiveBtnText: String?, dialogButtonClickListener: DialogButtonClickListener) {
        val dialogView = getView(context, theme)
        initializeViews(dialogView)
        textViewMessage!!.text = message
        positiveButton!!.text = positiveBtnText
        val alertDialog = createAlertDialog(context, dialogView)
        positiveButton!!.setOnClickListener { dialogButtonClickListener.onClick(alertDialog) }
    }

    fun showAlertDialog(context: Context, theme: DialogTheme, message: String?, positiveBtnText: String?, positiveClickListener: DialogButtonClickListener,
                        negativeClickListener: DialogButtonClickListener) {
        val dialogView = getView(context, theme)
        initializeViews(dialogView)
        textViewMessage!!.text = message
        positiveButton!!.text = positiveBtnText
        negativeButton!!.visibility = View.VISIBLE
        val alertDialog = createAlertDialog(context, dialogView)
        positiveButton!!.setOnClickListener { positiveClickListener.onClick(alertDialog) }
        negativeButton!!.setOnClickListener { negativeClickListener.onClick(alertDialog) }
    }

    private fun getView(context: Context, theme: DialogTheme) : View {
        var layoutView: View? = null
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        when (theme) {
            DialogTheme.ThemeGreen -> layoutView = inflater.inflate(R.layout.layout_alert_dialog_green, null)
            DialogTheme.ThemeWhite -> layoutView = inflater.inflate(R.layout.layout_alert_dialog_white, null)
        }

        return layoutView
    }

    private fun initializeViews(dialogView: View) {
        negativeButton = dialogView.findViewById<TextView>(R.id.negativeBtn)
        positiveButton = dialogView.findViewById<YrcButton>(R.id.positiveBtn)
        textViewTitle = dialogView.findViewById<YrcTextView>(R.id.textView_title)
        textViewMessage = dialogView.findViewById<YrcTextView>(R.id.textView_message)
    }

    private fun createAlertDialog(context: Context, dialogView: View) : AlertDialog {
        val alertDialog = AlertDialog.Builder(context).setView(dialogView).create()
        alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        alertDialog.setCancelable(false)
        alertDialog.show()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return alertDialog
    }
}