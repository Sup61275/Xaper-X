package com.example.xaper_x

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xaper_x.databinding.FragmentVideosBinding


class VideosFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_videos, container, false)
        val binding=FragmentVideosBinding.bind(view)
        val tempList= ArrayList<String>()

        binding.videoRV.setHasFixedSize(true)
        binding.videoRV.setItemViewCacheSize(10)
        binding.videoRV.layoutManager= LinearLayoutManager(requireContext())
        binding.videoRV.adapter= VideoAdapter(requireContext(),MainActivity.videoList)
        binding.totalVideos.text="Total Videos:  ${MainActivity.folderList.size}"
        return view


        // Inflate the layout for this fragment

    }
}

