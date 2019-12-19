package com.project.randomuser.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.randomuser.networking.UserDetail

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{

    fun bind(user: UserDetail?, clickListener: OnItemClickListener)
    {


        itemView.setOnClickListener {
            clickListener.onItemClicked(user)
        }
    }
}
