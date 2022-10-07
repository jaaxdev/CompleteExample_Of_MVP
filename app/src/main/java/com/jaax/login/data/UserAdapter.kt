package com.jaax.login.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaax.login.R
import com.jaax.login.data.model.User
import com.jaax.login.databinding.UserCardviewBinding

class UserAdapter(
    private val onUserClickListener: (Int) -> Unit
): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var listUsers = ArrayList<User>(0)

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = UserCardviewBinding.bind(view)

        fun bind(user: User, onUserClickListener: (Int) -> Unit) {
            binding.tvID.text = binding.tvID.text.toString()
                .plus(" ").plus(user.id)
            binding.tvFullName.text =
                binding.tvFullName.text.toString()
                    .plus(" ")
                    .plus(user.first_name)
                    .plus(" ")
                    .plus(user.last_name)
            binding.tvEmail.text = binding.tvEmail.text.toString()
                .plus(" ")
                .plus(user.email)
            Glide
                .with(itemView.context)
                .load(user.avatar)
                .circleCrop()
                .thumbnail(Glide.with(itemView.context).load(R.drawable.ic_baseline_person_24))
                .into(binding.ivAvatar)

            itemView.setOnClickListener { onUserClickListener(user.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.user_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUsers[position]
        holder.bind(user, onUserClickListener)
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllPokemon(list: List<User>) {
        listUsers.addAll(list)
        notifyDataSetChanged()
    }
}