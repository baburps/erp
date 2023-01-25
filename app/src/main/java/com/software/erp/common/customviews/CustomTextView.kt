package com.software.erp.common.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomTextView(
    context: Context?,
    attrs: AttributeSet?
) //    private void getFontFromAttribute(Context context, AttributeSet attrs) {
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.customView, 0, 0);
//        String font = a.getString(R.styleable.customView_fontType);
//        a.recycle();
//        Typeface typeface;
//        if (!TextUtils.isEmpty(font)) {
//            typeface = FontHelper.getFont(context, font + ".ttf");
//            if (typeface != null) {
//                setFont(typeface);
//            } else {
//                setFont(FontHelper.getDefaultFont(context, this.getTypeface()));
//            }
//        } else {
//            setFont(FontHelper.getDefaultFont(context, this.getTypeface()));
//        }
//    }
//
//    private void setFont(Typeface typeface) {
//        if (typeface != null) {
//            this.setTypeface(typeface);
//        }
//    }
    : AppCompatTextView(context!!, attrs)