package com.project.randomuser.ui

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.Config
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.randomuser.R
import com.project.randomuser.networking.UserDetail
import com.project.randomuser.userdatabase.UserDao
import com.project.randomuser.userdatabase.UserDb
import com.project.randomuser.util.OnItemClickListener
import com.project.randomuser.util.UserAdapter
import com.project.randomuser.util.UserBoundaryCallback
import com.project.randomuser.util.UserDetailSource
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnItemClickListener {

    //livedata for paged list
    lateinit var livedata: LiveData<PagedList<UserDetail?>>
    //config for paged list
    private val config= Config.Builder()
    .setPageSize(30)
    .setEnablePlaceholders(false)
    .build()
    //initialise adapter
    private val adapter = UserAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeList()
        val database = UserDb.create(this)

        //search using user name
        val search=findViewById<SearchView>(R.id.search)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {




                //pass search string
                livedata = initializedPagedListBuilder(config,database,"%" + query.toString() + "%").build()
                livedata.observe(this@MainActivity, Observer<PagedList<UserDetail?>> { pagedList ->
                    adapter.submitList(pagedList)
                })

                return false
            }
        })

    }



    //initialise list
    private fun initializeList() {

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        val database = UserDb.create(this)
        //livedata
        livedata = initializedPagedListBuilder(config,database,"").build()
        //set observer for livedata
        livedata.observe(this, Observer<PagedList<UserDetail?>> { pagedList ->
            adapter.submitList(pagedList)
        })

    }

    //pagedlist builder
    private fun initializedPagedListBuilder(config: Config,database:UserDb,input:String):
            LivePagedListBuilder<Int?, UserDetail?> {


        if (input.equals("%%") || input.equals("")) {

            val livePageListBuilder = LivePagedListBuilder(
                database.userdetailDao().users(),
                config)
            livePageListBuilder.setBoundaryCallback(UserBoundaryCallback(database))
            return livePageListBuilder
        } else {
            val livePageListBuilder = LivePagedListBuilder(
                database.userdetailDao().userByName(input),
                config)

            return livePageListBuilder
        }



    }


    //override user click interface method
    override fun onItemClicked(user: UserDetail?) {


        val i = Intent(this, DetailUserActivity::class.java)

        i.putExtra("userDetail",user )
        startActivity(i)
    }
}
