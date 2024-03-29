package com.pigeon.cloud.module.knowledge.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public class KnowledgeArticleFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList = null;

    public KnowledgeArticleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        mFragmentList = fragmentList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }
}
