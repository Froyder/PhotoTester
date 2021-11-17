package com.example.phototester.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phototester.R
import java.io.File

class GalleryAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var list: Array<out File>
) : RecyclerView.Adapter<GalleryAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photoImage: ImageView? = null
        var photoName: TextView? = null

        init {
            photoImage = itemView.findViewById(R.id.photo_imageView)
            photoName = itemView.findViewById(R.id.photo_textView)
        }
    }

    fun setData(listOfFiles: Array<out File>) {
        this.list = listOfFiles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val uriParse = Uri.parse(list[position].absolutePath)
        holder.photoImage?.setImageURI(uriParse)
        holder.photoName?.text = list[position].name
        holder.photoImage?.setOnClickListener { openInNewWindow(list[position]) }
    }

    override fun getItemCount() = list.size

    private fun openInNewWindow(file: File) {
        onListItemClickListener.onItemClick(file)
    }

    interface OnListItemClickListener {
        fun onItemClick(file: File)
    }
}