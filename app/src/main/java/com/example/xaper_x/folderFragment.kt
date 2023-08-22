package com.example.xaper_x

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xaper_x.databinding.FragmentFolderBinding


class folderFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_folder, container, false)
        val binding= FragmentFolderBinding.bind(view)



        binding.folderRV.setHasFixedSize(true)
        binding.folderRV.setItemViewCacheSize(10)
        binding.folderRV.layoutManager= LinearLayoutManager(requireContext())
        binding.folderRV.adapter= FolderAdapter(requireContext(),MainActivity.folderList)
        binding.totalFolders.text="Total Folders:  ${MainActivity.folderList.size}"
        return view


        // Inflate the layout for this fragment

    }


}