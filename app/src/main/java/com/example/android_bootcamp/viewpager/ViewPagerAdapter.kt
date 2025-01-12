package com.example.android_bootcamp.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2//number of pages
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Completed()//first page
            1 -> Active()  //second page
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
