package com.project.randomuser.util

import com.project.randomuser.networking.UserDetail

interface OnItemClickListener{
    fun onItemClicked(user: UserDetail?)
}