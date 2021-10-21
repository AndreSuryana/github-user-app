package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ItemRowUserBinding
import com.example.githubuserapp.data.model.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val listUsers = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUsers[position]
        holder.apply {
            bind(user)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(listUsers[holder.adapterPosition]) }
        }
    }

    override fun getItemCount() = listUsers.size

    fun setListUsers(users: ArrayList<User>) {
        listUsers.clear()
        listUsers.addAll(users)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                Glide.with(ivAvatar.context)
                    .load(user.avatarUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(ivAvatar)
                tvUsername.text = user.login
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}