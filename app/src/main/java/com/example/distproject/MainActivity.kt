package com.example.distproject

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.socketio.client.IO
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket


class MainActivity : AppCompatActivity() {

    // 소켓통신에 필요한것
    private val html = ""
    private var mHandler: Handler? = null

    //private var socket: Socket? = null
    private var socket: com.github.nkzawa.socketio.client.Socket? = IO.socket("http://36cd89957a89.ngrok.io")

    private var dos: DataOutputStream? = null
    private var dis: DataInputStream? = null

    private val ip = "121.127.180.189" // IP 번호

    private val port = 8080 // port 번호


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connect_btn.setOnClickListener{
            connect()
        }
    }

    // 로그인 정보 db에 넣어주고 연결시켜야 함.
    fun connect() {
        mHandler = Handler()
        Log.w("connect", "연결 하는중")
        // 받아오는거
        val checkUpdate: Thread = object : Thread() {
            override fun run() {
// ip받기
                val newip: String = java.lang.String.valueOf(ip_edit.getText())

// 서버 접속
                try {
                    socket = IO.socket("https://e02a8d3788dc.ngrok.io")
                    Log.w("서버 접속됨", "서버 접속됨")
                } catch (e1: IOException) {
                    Log.w("서버접속못함", "서버접속못함")
                    e1.printStackTrace()
                }
                Log.w("edit 넘어가야 할 값 : ", "안드로이드에서 서버로 연결요청")
                try {
                    dos = DataOutputStream(socket!!.getOutputStream()) // output에 보낼꺼 넣음
                    dis = DataInputStream(socket!!.getInputStream()) // input에 받을꺼 넣어짐
                    dos!!.writeUTF("안드로이드에서 서버로 연결요청")
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.w("버퍼", "버퍼생성 잘못됨")
                }
                Log.w("버퍼", "버퍼생성 잘됨")

                // 서버에서 계속 받아옴 - 한번은 문자, 한번은 숫자를 읽음. 순서 맞춰줘야 함.
                try {
                    var line = ""
                    var line2: Int
                    while (true) {
                        line = dis!!.readUTF()
                        line2 = dis!!.read()
                        Log.w("서버에서 받아온 값 ", "" + line)
                        Log.w("서버에서 받아온 값 ", "" + line2)
                    }
                } catch (e: Exception) {
                }
            }
        }
        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start()
    }
}