package com.galoppingtech.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.galoppingtech.retrofit.R
import com.galoppingtech.retrofit.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException
const val TAG = "ManiAnctivity"


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var todoAdapter: TodoAdapter


    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecycler()

        lifecycleScope.launchWhenCreated {
            binding.progress.isVisible = true
            val  response = try {
                RetrofitInstance.api.getTodos()
            }catch (e: IOException){

            Log.e(TAG,"IOException error occurred, Check your internet")
                binding.progress.isVisible =false
                return@launchWhenCreated

            }catch (e:HttpException){

                Log.e(TAG,"HTTPException error occurred")
                binding.progress.isVisible =false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null){
                todoAdapter.todos = response.body()!!
            }else{
                Log.e(TAG,"Failured")
            }
            binding.progress.isVisible =false
        }


       
    }
    private fun setRecycler() = binding.recyclerview.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }



}

