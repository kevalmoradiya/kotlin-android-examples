package com.project.randomuser.util

import androidx.recyclerview.widget.DiffUtil
import com.project.randomuser.networking.UserDetail


class UserDiffUtilCallback : DiffUtil.ItemCallback<UserDetail>() {

  override fun areItemsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
    return oldItem.email == newItem.email
  }

  override fun areContentsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
    return oldItem.email == newItem.email
            && oldItem.cell == newItem.cell
  }
}
