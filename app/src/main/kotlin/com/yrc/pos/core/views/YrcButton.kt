package com.yrc.pos.core.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import androidx.annotation.AttrRes
import com.yrc.pos.R
import com.yrc.pos.core.providers.TypefaceProvider

class YrcButton : Button , TypefaceProvider {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, R.attr.borderlessButtonStyle) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        this.typeface = getTypefaceFromXml(context, attrs)
    }
}