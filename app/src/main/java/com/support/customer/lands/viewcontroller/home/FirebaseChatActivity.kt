package com.support.customer.lands.viewcontroller.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.firebase.database.*
import com.google.gson.Gson
import com.support.customer.lands.R
import com.support.customer.lands.adapter.FirebaseChatAdapter
import com.support.customer.lands.databinding.ActivityFirebaseChatBinding
import com.support.customer.lands.model.ChatResponse
import com.support.customer.lands.model.FirebaseChatResponse
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.utills.extensions.hideSoftKeyboard
import haiphat.com.bds.nghetuvan.view.BaseActivity
import java.util.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.support.customer.lands.model.CountChatResponse


class FirebaseChatActivity : BaseActivity() {

    lateinit var firebaseChatBinding: ActivityFirebaseChatBinding
    val database = FirebaseDatabase.getInstance()

    var item = ArrayList<FirebaseChatResponse>()

    val dbGetMessage = UserServices.userInfo?.phone?.let { database.getReference("messages").child(it) }
        ?: database.getReference("messages")

    var db = UserServices.userInfo?.id?.let { database.getReference("accounts").child(it) }
    var count : Int? = 0
    override fun getContentView(): View {
        firebaseChatBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_firebase_chat, null, false)

        firebaseChatBinding.imgSend.setOnClickListener {
            //send content message chat
            val formatter = SimpleDateFormat("dd-MM-yyyy kk:mm:ss")
            val timeStamp = formatter.format(Calendar.getInstance().time)

            val date = formatter.parse(timeStamp) as Date
            getCountNotification()

            dbGetMessage?.push()?.setValue(
                ChatResponse(
                    text = firebaseChatBinding.edContent.text.toString(),
                    timestamp = (date.time / 1000),
                    notviewed = false,
                    fullname = UserServices?.userInfo?.fullname,
                    type = UserServices?.userInfo?.phone,
                    phone = UserServices?.userInfo?.phone
                )
            ).addOnSuccessListener {
                db?.child("count_notification")?.setValue(count)
                db?.child("last_send_time")?.setValue(-(date.time / 1000))
            }
            firebaseChatBinding.edContent.text.clear()
            firebaseChatBinding.edContent.hideSoftKeyboard(this@FirebaseChatActivity)

        }

        return firebaseChatBinding.root
    }


    private fun getCountNotification(){
        db?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var response = dataSnapshot.getValue(CountChatResponse::class.java)
                count = response?.count_notification?.plus(1)

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Tag", "Failed to read value.", error.toException())
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHeaderTitle(getString(R.string.txt_live_chat))



        dbGetMessage?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                readDataChat(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Tag", "Failed to read value.", error.toException())
            }
        })
    }

    private fun updateDataSnapshort() {
        dbGetMessage.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(tasksSnapshot: DataSnapshot) {

                for (snapshot in tasksSnapshot.children) {
                    var chat = snapshot.getValue(ChatResponse::class.java)
                    if (chat?.type != UserServices?.userInfo?.phone) {
                        snapshot.ref.child("notviewed").setValue(true)
                    }

                }
            }
        })
    }

    private fun readDataChat(dataSnapshot: DataSnapshot) {
        item.clear()
        for (singleSnapshot in dataSnapshot.children) {
            var chat = singleSnapshot.getValue(ChatResponse::class.java)
            if (chat?.type == UserServices?.userInfo?.phone) {
                item.add(FirebaseChatResponse(message = chat?.text, isMyMessage = true))
            } else {
                item.add(FirebaseChatResponse(message = chat?.text, isMyMessage = false))
                updateDataSnapshort()
            }
        }
        initAdapter()

    }


    private fun initAdapter() {
        val recyclerView = firebaseChatBinding.rvChat
        var adapter = FirebaseChatAdapter(item)
        val llm = LinearLayoutManager(this@FirebaseChatActivity)
        llm.stackFromEnd = true
        llm.reverseLayout = false
        recyclerView.layoutManager = llm
        recyclerView.adapter = adapter
    }


}
