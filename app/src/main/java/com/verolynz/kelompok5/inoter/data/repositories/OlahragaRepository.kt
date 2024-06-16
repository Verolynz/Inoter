package com.verolynz.kelompok5.inoter.data.repositories

import android.app.Service
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.verolynz.kelompok5.inoter.data.local.AtletDao
import com.verolynz.kelompok5.inoter.data.local.AtletEntity
import com.verolynz.kelompok5.inoter.data.local.CODao
import com.verolynz.kelompok5.inoter.data.local.COEntity
import com.verolynz.kelompok5.inoter.data.local.UsersDao
import com.verolynz.kelompok5.inoter.data.local.UsersEntity
import com.verolynz.kelompok5.inoter.data.remote.AtletResponse
import com.verolynz.kelompok5.inoter.data.remote.COResponse
import com.verolynz.kelompok5.inoter.data.remote.ConfigAPI
import com.verolynz.kelompok5.inoter.data.remote.ServiceAPI
import com.verolynz.kelompok5.inoter.utils.ExecutorsUtils
import com.verolynz.kelompok5.inoter.utils.NetworkUtils
import com.verolynz.kelompok5.inoter.utils.toListAtletEntity
import com.verolynz.kelompok5.inoter.utils.toListAtletResponse
import com.verolynz.kelompok5.inoter.utils.toListCOEntity
import com.verolynz.kelompok5.inoter.utils.toListCOResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OlahragaRepository private constructor(
    private val cODao: CODao,
    private val atletDao: AtletDao,
    private val usersDao: UsersDao,
    private val executorsUtils: ExecutorsUtils

)  {
    private val _listCO = MutableLiveData<List<COResponse>>()
    private val _listAtlet = MutableLiveData<List<AtletResponse>>()
    var listCO: LiveData<List<COResponse>> = _listCO
    var listAtlet: LiveData<List<AtletResponse>> = _listAtlet
    fun getAllCO() {
        val service = ConfigAPI.getApiService().getAllCO()
        service.enqueue(object : Callback<List<COResponse>> {
            override fun onResponse(
                call: Call<List<COResponse>>,
                response: Response<List<COResponse>>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listCO.value = response.body()

                    executorsUtils.diskIO().execute {
                        cODao.insertAllCO(responseBody.toListCOEntity())
                    }
                } else {
                    Log.e("Error on Response", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<COResponse>>, t: Throwable) {
                Log.e("Error on Failure", "onFailure: ${t.message}")
                executorsUtils.diskIO().execute {
                    _listCO.postValue(cODao.getAllCOList().toListCOResponse())
                }
            }
        })
    }
    fun getAllAtlet() {
        val service = ConfigAPI.getApiService().getAllAtlet()
        service.enqueue(object : Callback<List<AtletResponse>> {
            override fun onResponse(
                call: Call<List<AtletResponse>>,
                response: Response<List<AtletResponse>>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listAtlet.value = response.body()

                    executorsUtils.diskIO().execute {
                        atletDao.insertAllAtlet(responseBody.toListAtletEntity())
                    }
                } else {
                    Log.e("Error on Response", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<AtletResponse>>, t: Throwable) {
                Log.e("Error on Failure", "onFailure: ${t.message}")
                executorsUtils.diskIO().execute {
                    _listAtlet.postValue(atletDao.getAllAtletList().toListAtletResponse())
                }
            }
        })
    }
    fun insertCO(coEntity: COEntity) {
        executorsUtils.diskIO().execute { cODao.insertCO(coEntity) }
    }

    fun updateCO(coEntity: COEntity) {
        executorsUtils.diskIO().execute { cODao.updateCO(coEntity) }
    }

    fun deleteCO(coEntity: COEntity) {
        executorsUtils.diskIO().execute { cODao.deleteCO(coEntity) }
    }

    fun getAllCOlist(): LiveData<List<COEntity>> = cODao.getAllCO()
    fun insertAtlet(atletEntity: AtletEntity) {
        executorsUtils.diskIO().execute { atletDao.insertAtlet(atletEntity) }
    }

    fun updateAtlet(atletEntity: AtletEntity) {
        executorsUtils.diskIO().execute { atletDao.updateAtlet(atletEntity) }
    }

    fun deleteAtlet(atletEntity: AtletEntity) {
        executorsUtils.diskIO().execute { atletDao.deleteAtlet(atletEntity) }
    }

    fun insertUsers(usersEntity: UsersEntity) {
        executorsUtils.diskIO().execute { usersDao.insertUsers(usersEntity) }
    }
    fun updateUsers(usersEntity: UsersEntity) {
        executorsUtils.diskIO().execute { usersDao.updateUsers(usersEntity) }
    }
    fun deleteUsers(usersEntity: UsersEntity) {
        executorsUtils.diskIO().execute { usersDao.deleteUsers(usersEntity) }
    }
    fun getAllUserslist(): LiveData<List<UsersEntity>> = usersDao.getAllUsers()


    fun getAllAtletlist(): LiveData<List<AtletEntity>> = atletDao.getAllAtlet()

    suspend fun APItoDBCOData() {
        withContext(Dispatchers.IO) {
            val service = ConfigAPI.getApiService()
            val coResponse = service.getAllCO().execute().body()
            if (coResponse != null) {
                val coEntities = coResponse.toListCOEntity()
                cODao.insertAllCO(coEntities)
            }
        }
    }
    suspend fun APItoDBAtletData() {
        withContext(Dispatchers.IO) {
            val service = ConfigAPI.getApiService()
            val atletResponse = service.getAllAtlet().execute().body()
            if (atletResponse != null) {
                val atletEntities = atletResponse.toListAtletEntity()
                atletDao.insertAllAtlet(atletEntities)
            }
        }

    }

    suspend fun initializeData(context: Context) {
        if (NetworkUtils.isConnectedToInternet(context)) {
            APItoDBCOData()
            APItoDBAtletData()
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Tidak terhubung dengan jaringan", Toast.LENGTH_SHORT).show()
            }
        }

        val adminUser = usersDao.getUsersAdmin().value
        if (adminUser == null || adminUser.isEmpty()) {
            createDefaultAdmin()
        }
    }

    suspend fun createDefaultAdmin() {
        withContext(Dispatchers.IO) {
            val defaultAdmin = UsersEntity(
                id = 0, // Anda bisa mengatur ID sesuai kebutuhan
                username = "admin",
                password = "admin123",
                role = "admin"
            )
            usersDao.insertUsers(defaultAdmin)
        }
    }
    suspend fun createAccount(context: Context, username: String, password: String) {
        if (NetworkUtils.isConnectedToInternet(context)) {
            withContext(Dispatchers.IO) {
                val newUser = UsersEntity(
                    id = 0,
                    username = username,
                    password = password,
                    role = "user"
                )
                usersDao.insertUsers(newUser)
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Tidak terhubung dengan jaringan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    suspend fun loginAuth(username: String, password: String, context: Context) {
        usersDao.getUsersAuth(username, password).observe(context as LifecycleOwner, { users ->
            if (users.isNotEmpty()) {
                val user = users[0]
                when (user.role) {
                    "admin" -> {
                        // Navigasi ke AdminActivity
                        val intent = Intent(context, AdminActivity::class.java)
                        context.startActivity(intent)
                    }
                    "user" -> {
                        // Navigasi ke UserActivity
                        val intent = Intent(context, UserActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            } else {
                // Tampilkan pesan error
                (context as LifecycleOwner).lifecycleScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }






    companion object {
        @Volatile
        private var instance: OlahragaRepository? = null

        fun getInstance(
            cODao: CODao,
            atletDao: AtletDao,
            usersDao: UsersDao,
            executorsUtils: ExecutorsUtils
        ): OlahragaRepository =
        // Jika instance sudah ada, kembalikan instance tersebut.
            // Jika instance belum ada, buat instance baru.
            instance ?: synchronized(this) {
                instance ?: OlahragaRepository(cODao, atletDao, usersDao ,executorsUtils)
            }.also { instance = it }
    }
}