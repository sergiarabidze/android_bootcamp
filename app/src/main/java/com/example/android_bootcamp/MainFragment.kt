package com.example.android_bootcamp

import android.os.Bundle
import android.view.View
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding


class MainFragment : FragmentBase<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}