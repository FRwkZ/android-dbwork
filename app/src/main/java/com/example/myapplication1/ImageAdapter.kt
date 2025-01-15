package com.example.myapplication1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//class ImageAdapter(
//    private val imageList: List<Uri>,
//    private val context: Context
//) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val imageUri = imageList[position]
//        Glide.with(context).load(imageUri).into(holder.imageView)
//
//        holder.imageView.setOnClickListener {
//            val intent = Intent(context, ImageDetailActivity::class.java)
//            intent.putExtra("imageUri", imageUri.toString())
//            context.startActivity(intent)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return imageList.size
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageView)
//    }
//}

class ImageAdapter(
    private var imageList: List<Uri>,
    private val context: Context
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUri = imageList[position]
        Glide.with(context).load(imageUri).into(holder.imageView)

        holder.imageView.setOnClickListener {
            val intent = Intent(context, ImageDetailActivity::class.java)
            intent.putExtra("imageUri", imageUri.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun updateImages(newImageList: List<Uri>) {
        imageList = newImageList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}




