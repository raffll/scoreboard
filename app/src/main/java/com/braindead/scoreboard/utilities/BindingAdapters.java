package com.braindead.scoreboard.utilities;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.braindead.scoreboard.ui.main.MainActivity;
import com.braindead.scoreboard.ui.main.MainSectionsAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

public abstract class BindingAdapters {

    @BindingAdapter({"textStyle"})
    public static void setTypeface(TextView view, Boolean value) {
        view.setTypeface(null, value ? Typeface.BOLD : Typeface.NORMAL);
        view.setPaintFlags(value ? Paint.UNDERLINE_TEXT_FLAG : 0);
    }

    @BindingAdapter({"visibility"})
    public static void setVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"background"})
    public static void setBackground(TextView view, int color) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(color);
        view.setBackground(shape);
    }

    @BindingAdapter({"textSize"})
    public static void setTextSize(TextView view, int value) {
        view.setTextSize(value);
    }

    @BindingAdapter({"layout_width"})
    public static void setLayoutWidth(TextView view, int value) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = value + 10;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter({"layout_height"})
    public static void setLayoutHeight(TextView view, int value) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = value + 10;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter({"handler"})
    public static void bindViewPagerAdapter(final ViewPager view, final MainActivity activity) {
        final MainSectionsAdapter adapter = new MainSectionsAdapter(activity.getSupportFragmentManager());
        view.setAdapter(adapter);
    }

    @BindingAdapter({"pager"})
    public static void bindViewPagerTabs(final TabLayout view, final ViewPager pagerView) {
        view.setupWithViewPager(pagerView, true);
    }
}