package com.example.hangoutz.data.remote

import com.example.hangoutz.BuildConfig
import com.example.hangoutz.data.models.AvatarRequest
import com.example.hangoutz.data.models.EventCardAvatar
import com.example.hangoutz.data.models.User
import com.example.hangoutz.data.models.UserRequest
import com.example.hangoutz.data.models.UserUpdateRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserAPI {
    @GET("${BuildConfig.REQUEST_URL}users")
    suspend fun getUserByName(@Query("name") name: String): Response<List<User>>

    @GET("${BuildConfig.REQUEST_URL}users")
    suspend fun getUserById(@Query("id") id: String): Response<List<User>>

    @GET("${BuildConfig.REQUEST_URL}users")
    suspend fun getUserByEmailAndPassword(
        @Query("email") email: String, @Query("password_hash") password: String
    ): Response<List<User>>

    @POST("${BuildConfig.REQUEST_URL}users")
    suspend fun insertUser(@Body user: UserRequest) : Response<Unit>

    @GET("${BuildConfig.REQUEST_URL}users?select=avatar")
    suspend fun getUserAvatar(
        @Query("id") id: String
    ): Response<List<EventCardAvatar>>

    @PATCH("${BuildConfig.REQUEST_URL}users")
    suspend fun patchUserById(
        @Query("id") id: String, @Body newName: UserUpdateRequest
    ): Response<Unit>

    @PATCH("${BuildConfig.REQUEST_URL}users")
    suspend fun patchUserAvatarById(
        @Query("id") id: String, @Body newAvatar: AvatarRequest
    ): Response<Unit>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpzanh3Zmp1dHN0cnlidmx0am92Iiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTczMDgwODE0MSwiZXhwIjoyMDQ2Mzg0MTQxfQ.nBHlsKMnhgB-_iwXFa_FNZXJ6t-15c-5srRy4QjRXN0")
    @DELETE("https://zsjxwfjutstrybvltjov.supabase.co/storage/v1/object/avatar/{avatarName}")
    suspend fun deleteAvatarByName(
        @Path("avatarName") avatarName: String
    ): Response<Unit>


    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpzanh3Zmp1dHN0cnlidmx0am92Iiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTczMDgwODE0MSwiZXhwIjoyMDQ2Mzg0MTQxfQ.nBHlsKMnhgB-_iwXFa_FNZXJ6t-15c-5srRy4QjRXN0")
    @Multipart
    @POST("https://zsjxwfjutstrybvltjov.supabase.co/storage/v1/object/avatar/{avatarName}")
    suspend fun postAvatar(
        @Part image: MultipartBody.Part, @Path("avatarName") avatarName: String
    ): Response<Unit>
}
}