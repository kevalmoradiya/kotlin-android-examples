package com.project.randomuser.util

import android.util.Log
import androidx.paging.PagedList
import com.project.randomuser.networking.UserApiResponse
import com.project.randomuser.networking.UserDetail
import com.project.randomuser.networking.UserService
import com.project.randomuser.userdatabase.UserDb
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.concurrent.Executors


class UserBoundaryCallback(private val db: UserDb) :
    PagedList.BoundaryCallback<UserDetail>() {

  private val api = UserService.createService()
  private val executor = Executors.newSingleThreadExecutor()
  private val helper = PagingRequestHelper(executor)

  override fun onZeroItemsLoaded() {
    super.onZeroItemsLoaded()
    //1
    helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helperCallback ->
      api.getUser(loadSize = 30,before = "abc",after=PageIndex().index.toString())
          //2
          .enqueue(object : Callback<UserApiResponse> {

            override fun onFailure(call: Call<UserApiResponse>?, t: Throwable) {
              //3
              Log.e("UserBoundaryCallback", "Failed to load data!"+t.toString())
              helperCallback.recordFailure(t)
            }

            override fun onResponse(
                call: Call<UserApiResponse>?,
                response: Response<UserApiResponse>
            ) {
              //4
              val posts = response.body()?.results
              executor.execute {
                try{
                  db.userdetailDao().insert(posts ?: listOf())
                  helperCallback.recordSuccess()
                }
                catch (e:Exception)
                {
                  Log.d("DBINSERTERROR",e.toString())
                }

              }

            }
          })
    }
  }

  override fun onItemAtEndLoaded(itemAtEnd: UserDetail) {
    super.onItemAtEndLoaded(itemAtEnd)
    helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { helperCallback ->
      api.getUser(loadSize = 30,before = "abc",after = PageIndex().increment().toString())
          .enqueue(object : Callback<UserApiResponse> {

            override fun onFailure(call: Call<UserApiResponse>?, t: Throwable) {
              Log.e("UserBoundaryCallback", "Failed to load data!")
              helperCallback.recordFailure(t)
            }

            override fun onResponse(
                call: Call<UserApiResponse>?,
                response: Response<UserApiResponse>) {

              val posts = response.body()?.results
              executor.execute {
                db.userdetailDao().insert(posts ?: listOf())
                helperCallback.recordSuccess()
              }

            }
          })
    }
  }
}
