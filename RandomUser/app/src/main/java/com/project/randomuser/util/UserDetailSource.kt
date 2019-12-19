package com.project.randomuser.util

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.project.randomuser.networking.UserApiResponse
import com.project.randomuser.networking.UserDetail
import com.project.randomuser.networking.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailSource : PageKeyedDataSource<String, UserDetail>() {

  private val api = UserService.createService()

  override fun loadInitial(
      params: LoadInitialParams<String>,
      callback: LoadInitialCallback<String, UserDetail>) {

    api.getUser(loadSize = params.requestedLoadSize,before = "abc",after = PageIndex().index.toString())
        .enqueue(object : Callback<UserApiResponse> {

          override fun onFailure(call: Call<UserApiResponse>?, t: Throwable?) {
              Log.e("UserDetailSource", "Failed to fetch user!")
          }

          override fun onResponse(
              call: Call<UserApiResponse>?,
              response: Response<UserApiResponse>
          ) {

            val listing = response.body()?.results
            val userDetails = listing
            callback.onResult(userDetails ?: listOf(), "abc", PageIndex().increment().toString())
          }
        })
  }

  override fun loadAfter(
      params: LoadParams<String>,
      callback: LoadCallback<String, UserDetail>) {

    api.getUser(loadSize = params.requestedLoadSize,before = "abc",after = PageIndex().increment().toString())
        .enqueue(object : Callback<UserApiResponse> {

          override fun onFailure(call: Call<UserApiResponse>?, t: Throwable?) {
              Log.e("UserDetailSource", "Failed to fetch user!")
          }

          override fun onResponse(
              call: Call<UserApiResponse>?,
              response: Response<UserApiResponse>) {

            val listing = response.body()?.results
            val items = listing
            callback.onResult(items ?: listOf(),  PageIndex().increment().toString())
          }
        })
  }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, UserDetail>) {

        api.getUser(loadSize = params.requestedLoadSize,before = "abc",after = PageIndex().decrement().toString())
            .enqueue(object : Callback<UserApiResponse> {

                override fun onFailure(call: Call<UserApiResponse>?, t: Throwable?) {
                    Log.e("UserDataSource", "Failed to fetch data!")
                }

                override fun onResponse(
                    call: Call<UserApiResponse>?,
                    response: Response<UserApiResponse>) {

                    val listing = response.body()?.results
                    val items = listing
                    callback.onResult(items ?: listOf(), PageIndex().decrement().toString())
                }
            })
    }


}
