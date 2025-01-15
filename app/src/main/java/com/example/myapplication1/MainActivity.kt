package com.example.myapplication1

import ImageDbHelper
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication1.ui.theme.MyApplication1Theme

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.databinding.ActivityMainBinding
import android.provider.MediaStore
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import androidx.lifecycle.Observer

//class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
//    private val imageList = mutableListOf<Uri>()
//    private lateinit var adapter: ImageAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Setup RecyclerView
//        adapter = ImageAdapter(imageList, this)
//        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
//        binding.recyclerView.adapter = adapter
//
//        // Add com.example.myapplication1.Image Button
//        binding.addButton.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            startActivityForResult(intent, 1)
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            val imageUri = data.data
//            if (imageUri != null) {
//                imageList.add(imageUri)
//                adapter.notifyDataSetChanged()
//            }
//        }
//    }
//}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ImageAdapter
    private lateinit var dbHelper: ImageDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize database helper
        dbHelper = ImageDbHelper(this)

        // Setup RecyclerView
        adapter = ImageAdapter(getImageUris(), this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = adapter

        // Add Image Button
        binding.addButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            if (imageUri != null) {
                val imagePath = saveImageToFileSystem(imageUri)
                if (imagePath != null) {
                    dbHelper.insertImagePath(imagePath)
                    adapter.updateImages(getImageUris())
                }
            }
        }
    }

    private fun saveImageToFileSystem(uri: Uri): String? {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val file = File(getExternalFilesDir(null), "image_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file.absolutePath
    }

    private fun getImageUris(): List<Uri> {
        val cursor = dbHelper.getAllImages()
        val uris = mutableListOf<Uri>()
        if (cursor.moveToFirst()) {
            do {
                val path = cursor.getString(cursor.getColumnIndexOrThrow(ImageDbHelper.COLUMN_PATH))
                uris.add(Uri.fromFile(File(path)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return uris
    }
}




