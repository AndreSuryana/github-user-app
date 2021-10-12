package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.data.User
import com.example.githubuserapp.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemBinding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user: User = listUser[position]
        holder.apply {
            bind(user)
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(private val itemBinding: ItemRowUserBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: User) {
            itemBinding.apply {
                ivAvatar.setImageResource(user.avatar)
                tvName.text = user.name.toString()
                tvUsername.text = user.username.toString()
                tvCompany.text = user.company.toString()
                tvLocation.text = user.location.toString()
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}