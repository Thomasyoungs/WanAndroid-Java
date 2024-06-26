package com.pigeon.cloud.utils;

import android.content.Context;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.pigeon.basic.core.adapter.MultiFragmentPagerAdapter;
import com.pigeon.basic.core.utils.ResUtils;
import com.pigeon.basic.core.utils.display.DisplayInfoUtils;
import com.pigeon.basic.core.utils.listener.SimpleCallback;
import com.pigeon.cloud.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class MagicIndicatorUtils {

    public static <E, F extends Fragment> CommonNavigator commonNavigator(
            MagicIndicator mi, ViewPager vp,
            MultiFragmentPagerAdapter<E, F> adapter,
            SimpleCallback<Integer> onClickCallback) {
        CommonNavigator navigator = new CommonNavigator(vp.getContext());
        final int padding16 = (int) DisplayInfoUtils.getInstance().dp2px(8);
        navigator.setLeftPadding(padding16);
        navigator.setRightPadding(padding16);
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return adapter.getCount();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(Html.fromHtml(adapter.getPageTitle(index).toString()));
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResUtils.getDimens(R.dimen.actionbarex_common_title_bar_text_size_def));
                simplePagerTitleView.setSelectedColor(ResUtils.getThemeColor(context, R.attr.colorOnMainOrSurface));
                simplePagerTitleView.setNormalColor(ResUtils.getThemeColor(context, R.attr.colorOnMainOrSurfaceAlpha));
                int paddingH = (int) DisplayInfoUtils.getInstance().dp2px(8);
                int paddingV = (int) DisplayInfoUtils.getInstance().dp2px(5);
                simplePagerTitleView.setPadding(paddingH, paddingV, paddingH, paddingV);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vp.setCurrentItem(index);
                        if (onClickCallback != null) {
                            onClickCallback.onResult(index);
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mi.setNavigator(navigator);
        ViewPagerHelper.bind(mi, vp);
        return navigator;
    }

    public static CommonNavigator commonNavigator(
            MagicIndicator mi, ViewPager vp,
            FragmentPagerAdapter adapter,
            SimpleCallback<Integer> onClickCallback) {
        CommonNavigator navigator = new CommonNavigator(vp.getContext());
        final int padding16 = (int) DisplayInfoUtils.getInstance().dp2px(8);
        navigator.setLeftPadding(padding16);
        navigator.setRightPadding(padding16);
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return adapter.getCount();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(Html.fromHtml(adapter.getPageTitle(index).toString()));
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResUtils.getDimens(R.dimen.actionbarex_common_title_bar_title_text_size_def));
                simplePagerTitleView.setSelectedColor(ResUtils.getThemeColor(context, R.attr.colorOnMainOrSurface));
                simplePagerTitleView.setNormalColor(ResUtils.getThemeColor(context, R.attr.colorOnMainOrSurfaceAlpha));
                int paddingH = (int) DisplayInfoUtils.getInstance().dp2px(8);
                int paddingV = (int) DisplayInfoUtils.getInstance().dp2px(5);
                simplePagerTitleView.setPadding(paddingH, paddingV, paddingH, paddingV);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vp.setCurrentItem(index);
                        if (onClickCallback != null) {
                            onClickCallback.onResult(index);
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mi.setNavigator(navigator);
        ViewPagerHelper.bind(mi, vp);
        return navigator;
    }
}
