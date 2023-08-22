package com.example.xaper_x

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.xaper_x.databinding.FolderViewBinding

class FolderAdapter(private val context: Context, private var folderList: ArrayList<Folder>): RecyclerView.Adapter<FolderAdapter.MyHolder>()  {
    class MyHolder (binding: FolderViewBinding) : RecyclerView.ViewHolder(binding.root) {
       val folderName=binding.folderNameFV
        val root=binding.root

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(FolderViewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val folderName = folderList[position].folderName
        holder.folderName.text = folderName

        // Inside the item click listener in your FolderAdapter
        holder.root.setOnClickListener {
            val intent = Intent(context, FoldersActivity::class.java)
            intent.putExtra("folderId", folderList[position].id) // Pass folder ID
            intent.putExtra("folderName", folderList[position].folderName) // Pass folder name
            ContextCompat.startActivity(context, intent, null)
        }

    }


    override fun getItemCount(): Int {
        return folderList.size
    }


}