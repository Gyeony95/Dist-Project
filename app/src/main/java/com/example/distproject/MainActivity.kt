package com.example.distproject

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.avon.remindfeedback.Network.RetrofitFactory
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_response.setBackgroundResource(R.color.GREEN)
        dataTest()


        refresh_layout.setOnRefreshListener {
            // 새로고침 코드를 작성
            dataTest()
            // 새로고침 완료시,
            // 새로고침 아이콘이 사라질 수 있게 isRefreshing = false
            refresh_layout.isRefreshing = false
        }

    }




    fun dataTest() {
        val client: OkHttpClient = RetrofitFactory.getClient()
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<Object> = apiService.getData()
        register_request.enqueue(object : Callback<Object> {
            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                if (response.isSuccessful) {

                    var jArray: JSONArray = JSONArray(Gson().toJson(response.body()))
                    Log.e("asd", jArray.toString())
                    var jObject:JSONObject = jArray.getJSONObject(jArray.length()-1)
                    Log.e("asd", jObject.getString("mst").toString())

                    tv_date.text = jObject.getString("createdAt")
                    if(jObject.getString("mst").toString().equals("high")){
                        tv_response.setBackgroundResource(R.color.RED)

                    }else{
                        tv_response.setBackgroundResource(R.color.GREEN)
                    }


                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())

            }

            override fun onFailure(call: Call<Object>, t: Throwable) {
                Log.e("실패", t.message.toString())

            }
        })
    }

}