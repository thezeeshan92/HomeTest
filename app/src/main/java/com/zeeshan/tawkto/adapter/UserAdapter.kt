package com.zeeshan.tawkto.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zeeshan.tawkto.musicplayer.R
import com.zeeshan.tawkto.musicplayer.databinding.LayoutUserItemBinding
import com.zeeshan.tawkto.room.entity.User
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter(private val listener: UserItemListener) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(), Filterable {
    private val users = ArrayList<User>()
    private var usersFilterList = ArrayList<User>()

    @SuppressLint("NotifyDataSetChanged")
    fun addUserItem(users: ArrayList<User>) {
        this.users.clear()
        this.usersFilterList.clear()
        this.users.addAll(users)
        this.usersFilterList.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = UserViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_user_item,
            parent, false
        ), listener
    )

    override fun getItemCount() = usersFilterList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(usersFilterList[position])
    }

    class UserViewHolder(
        layoutUserItemBinding: LayoutUserItemBinding,
        private val listener: UserItemListener
    ) :
        RecyclerView.ViewHolder(layoutUserItemBinding.root), View.OnClickListener {

        private val layoutArtistMusicItem = layoutUserItemBinding
        private lateinit var userItem: User

        init {
            layoutUserItemBinding.root.setOnClickListener(this)
        }

        fun bind(userItem: User) {
            layoutArtistMusicItem.userItem = userItem
            this.userItem = userItem
        }

        override fun onClick(v: View?) {
            listener.onUserClicked(userItem.login)
        }
    }


    interface UserItemListener {
        fun onUserClicked(username: String)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                usersFilterList = if (charSearch.isEmpty()) {
                    users
                } else {
                    val resultList = ArrayList<User>()
                    for (row in users) {
                        if (row.login.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = usersFilterList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                usersFilterList = results?.values as  ArrayList<User>
                notifyDataSetChanged()
            }

        }
    }
}