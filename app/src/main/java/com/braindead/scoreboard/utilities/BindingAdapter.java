package com.braindead.scoreboard.utilities;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

public abstract class BindingAdapter {

    @android.databinding.BindingAdapter("android:textStyle")
    public static void setTypeface(TextView textView, String style) {
        switch (style) {
            case "bold":
                textView.setTypeface(null, Typeface.BOLD);
                break;
            default:
                textView.setTypeface(null, Typeface.NORMAL);
                break;
        }
    }

    @android.databinding.BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @android.databinding.BindingAdapter("android:background")
    public static void setBackground(TextView view, int color) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(color);
        view.setBackground(shape);
    }
}