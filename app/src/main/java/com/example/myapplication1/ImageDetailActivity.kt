package com.example.myapplication1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.myapplication1.databinding.ActivityImageDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File


//class ImageDetailActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityImageDetailBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityImageDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // 加载图片
//        val imageUriString = intent.getStringExtra("imageUri")
//        val imageUri = Uri.parse(imageUriString)
//        Glide.with(this).load(imageUri).into(binding.detailImageView)
//
//        // 设置分享按钮点击事件
//        val fabShare: FloatingActionButton = binding.fabShare
//        fabShare.setOnClickListener {
//            shareImage(imageUri)
//        }
//    }
//
//    private fun shareImage(imageUri: Uri) {
//        val shareIntent = Intent(Intent.ACTION_SEND)
//        shareIntent.type = "image/*"
//        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
//        startActivity(Intent.createChooser(shareIntent, "Share com.example.myapplication1.Image"))
//    }
//}


class ImageDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 加载图片
        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = Uri.parse(imageUriString)
        Glide.with(this).load(imageUri).into(binding.detailImageView)

        // 设置分享按钮点击事件
        val fabShare: FloatingActionButton = binding.fabShare
        fabShare.setOnClickListener {
            shareImage(imageUri)
        }
    }

    private fun shareImage(imageUri: Uri) {
        // 将 file:// URI 转换为 content:// URI
        val file = File(imageUri.path!!)
        val contentUri = FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.fileprovider",
            file
        )

        // 创建分享 Intent
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, contentUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // 授予临时读取权限
        }

        // 启动分享
        startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }
}