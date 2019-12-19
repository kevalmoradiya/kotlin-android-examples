package com.project.randomuser.util

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagedListAdapter
import com.project.randomuser.R
import com.project.randomuser.networking.UserDetail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_row.view.*


class UserAdapter(val itemClickListener: OnItemClickListener) : PagedListAdapter<UserDetail, UserViewHolder>(UserDiffUtilCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_row, parent, false)
    return UserViewHolder(view)
  }



  override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
    val item = getItem(position)
    val resources = holder.itemView.context.resources
    val dob = resources.getString(R.string.dob, item?.dob?.date)
    val gender = resources.getString(R.string.gender, item?.gender)
    val name=resources.getString(R.string.full_name,item?.name?.title+" "+item?.name?.first+" "+item?.name?.last)
    holder.itemView.textviewName.text =name
    holder.itemView.textviewDOB.text =dob
    holder.itemView.textviewGender.text=gender
    Picasso.get()
      .load(Uri.parse(item?.picture?.medium))
      .placeholder(R.drawable.progressbar)
      .fit()
      .centerInside()
      .into(holder.itemView.imageThumbnail)
    holder.bind(item,itemClickListener)
  }




}
