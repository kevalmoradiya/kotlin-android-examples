package com.project.randomuser.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query




interface UserService {
  @GET("/api/?")
  fun getUser(@Query("results") loadSize: Int = 30,
               @Query("page") after: String? = "abc",@Query("seeds") before: String? = null): Call<UserApiResponse>

  companion object {
    private const val BASE_URL = "https://randomuser.me"
      val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
          this.level = HttpLoggingInterceptor.Level.BODY
      }

      val client : OkHttpClient = OkHttpClient.Builder().apply {
          this.addInterceptor(interceptor)
      }.build()
    fun createService(): UserService {


      return Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .client(client)
          .build()
          .create(UserService::class.java)


    }
  }
}
