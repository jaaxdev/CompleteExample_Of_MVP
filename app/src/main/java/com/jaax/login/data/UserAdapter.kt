package com.jaax.login.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaax.login.R
import com.jaax.login.data.model.User
import com.jaax.login.databinding.UserCardviewBinding
import kotlin.collections.ArrayList

class UserAdapter(
    private val onUserClickListener: (Int) -> Unit
): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private val listUsers = ArrayList<User>()

    companion object {
        private const val ID = "Número de ID: "
        private const val NAME = "Nombre: "
        private const val EMAIL = "Email: "
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = UserCardviewBinding.bind(view)

        fun bind(user: User, onUserClickListener: (Int) -> Unit) {
            binding.tvID.text = ID.plus(user.id)
            binding.tvFullName.text = NAME.plus(user.first_name).plus(" ").plus(user.last_name)
            binding.tvEmail.text = EMAIL.plus(user.email)
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
    fun addMoreUsers(list: List<User>) {
        listUsers.addAll(list)
        notifyDataSetChanged()
    }

    /*override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val entry = constraint.toString()
                if(entry.isEmpty()) {
                    listUsers = loadListUsers as ArrayList<User>
                } else {
                    val results = kotlin.collections.ArrayList<User>(0)
                    for(user in loadListUsers) {
                        if(
                            user.id.toString().contains(entry)
                            || user.email.lowercase().contains(entry.lowercase())
                            || user.first_name.lowercase().contains(entry.lowercase())
                            || user.last_name.lowercase().contains(entry.lowercase())
                        ) {
                            results.add(user)
                        }
                    }
                    listUsers = results
                }
                val filteredResults = FilterResults()
                filteredResults.values = listUsers
                return filteredResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listUsers = results?.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }*/
}