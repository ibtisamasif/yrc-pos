package com.yrc.pos.features.support

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.yrc.pos.R
import kotlinx.android.synthetic.main.layout_support_dialog.*

class SupportDialog : DialogFragment() {

    private var PERMISSION_REQUEST_CALL_PHONE = 1111

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        isCancelable = false
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return inflater.inflate(R.layout.layout_support_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeBtn.setOnClickListener { dialog!!.dismiss() }
        textView_email.setOnClickListener { sendEmailToSupport() }
        textView_call.setOnClickListener { checkCallPermission() }
    }

    private fun sendEmailToSupport() {
        dialog!!.dismiss()
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse(getString(R.string.mail_to) + getString(R.string.support_email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.customer_support_required))
        startActivity(emailIntent)
    }

    private fun checkCallPermission() {
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), PERMISSION_REQUEST_CALL_PHONE);
        } else {
            callAtSupport()
        }
    }

    private fun callAtSupport() {
        dialog!!.dismiss()
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse(getString(R.string.tel) + getString(R.string.support_number))
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            PERMISSION_REQUEST_CALL_PHONE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callAtSupport()
                }
            }
        }
    }
}