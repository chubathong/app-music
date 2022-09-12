package com.example.myapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> arrayFragment=new ArrayList<>();
    private ArrayList<String> arraytitle=new ArrayList<>();
    public MainViewPagerAdapter(@NonNull FragmentManager fm)
    {
        super(fm);
    }


    /* click tới fragment nào trả về vị trí fragment  đó*/
    /* khi trả về ViewPager thì trả bao nhiêu Fragment*/

    @Override
    public Fragment getItem(int position)
    {
        return arrayFragment.get(position);
    }

    /* bao nhieu Fragment muốn hiển thị*/
    @Override
    public int getCount()
    {
        return arrayFragment.size();
    }
    public void addFragment(Fragment fragment,String title)/* title hiển thị tiêu đề fragment*/
    {
        arrayFragment.add(fragment);
        arraytitle.add(title);
    }
    @Nullable
    @Override
    /* add dồn Viewpager vào tab và tab này  hiển thị tên mỗi fragment trong Viewpager*/
    public CharSequence getPageTitle(int position)
    {
        return arraytitle.get(position);
    }
}
