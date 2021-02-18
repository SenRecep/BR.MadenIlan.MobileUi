package com.example.brmadenilanmobileui.ui.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSlidePagerAdapter(fa:FragmentActivity):FragmentStateAdapter(fa) {
    var fragments=ArrayList<Fragment>();
    override fun getItemCount(): Int {
        return  fragments.size;
    }

    override fun createFragment(position: Int): Fragment {
        return  fragments[position]
    }

    fun addFragment(f:Fragment){
        fragments.add(f);
    }
}