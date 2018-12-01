package com.braindead.scoreboard.utilities;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class BindingAdapter {

    @android.databinding.BindingAdapter("android:textStyle")
    public static void setTypeface(TextView view, String style) {
        switch (style) {
            case "bold":
                view.setTypeface(null, Typeface.BOLD);
                break;
            default:
                view.setTypeface(null, Typeface.NORMAL);
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

    /*@android.databinding.BindingAdapter("android:backgroundTint")
    public static void setBackgroundTint(View view, int color) {
        view.setBackgroundTintList(ColorStateList.valueOf(color));
    }*/

    @android.databinding.BindingAdapter({"bind:textSize"})
    public static void setTextSize(TextView view, int value) {
        view.setTextSize(value);
    }

    @android.databinding.BindingAdapter({"bind:layout_width"})
    public static void setLayoutWidth(TextView view, int value) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = value + 10;
        view.setLayoutParams(layoutParams);
    }

    @android.databinding.BindingAdapter({"bind:layout_height"})
    public static void setLayoutHeight(TextView view, int value) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = value + 10;
        view.setLayoutParams(layoutParams);
    }
}