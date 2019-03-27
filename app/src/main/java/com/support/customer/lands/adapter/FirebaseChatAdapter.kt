package com.support.customer.lands.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import com.support.customer.lands.R
import com.support.customer.lands.model.FirebaseChatResponse
import kotlinx.android.synthetic.main.item_message_receive.view.*
import kotlinx.android.synthetic.main.item_message_send.view.*


class FirebaseChatAdapter(private val messageList: ArrayList<FirebaseChatResponse>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == TYPE_MESSAGE_RECEIVE){
            val itemMessageReceiveBinding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), R.layout.item_message_receive, parent, false)
            ViewHolderReceive(itemMessageReceiveBinding)
        }else{
            val itemMessageSendBinding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), R.layout.item_message_send, parent, false)
            ViewHolderSend(itemMessageSendBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            TYPE_MESSAGE_RECEIVE -> (holder as ViewHolderReceive).bindItem(messageList?.get(position))

            TYPE_MESSAGE_SEND -> (holder as ViewHolderSend).bindItem(messageList?.get(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messageList?.get(position)?.isMyMessage == true) {
            TYPE_MESSAGE_SEND
        } else {
            TYPE_MESSAGE_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList?.size ?: 0
    }


    inner class ViewHolderReceive(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root)  {

        fun bindItem(response: FirebaseChatResponse?) {
            itemView.txtMessageReceive.text = response?.message
        }
    }

    inner class ViewHolderSend(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {

        fun bindItem(response: FirebaseChatResponse?) {
            itemView.txtMessageSend.text = response?.message
        }
    }



    companion object {
        private val TYPE_MESSAGE_RECEIVE = 1
        private val TYPE_MESSAGE_SEND = 2
    }
}