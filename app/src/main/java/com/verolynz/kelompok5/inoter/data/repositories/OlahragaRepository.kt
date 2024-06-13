package com.verolynz.kelompok5.inoter.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.verolynz.kelompok5.inoter.data.local.AtletDao
import com.verolynz.kelompok5.inoter.data.local.AtletEntity
import com.verolynz.kelompok5.inoter.data.local.CODao
import com.verolynz.kelompok5.inoter.data.local.COEntity
import com.verolynz.kelompok5.inoter.data.remote.AtletResponse
import com.verolynz.kelompok5.inoter.data.remote.COResponse
import com.verolynz.kelompok5.inoter.data.remote.ConfigAPI
import com.verolynz.kelompok5.inoter.utils.ExecutorsUtils
import com.verolynz.kelompok5.inoter.utils.toListAtletEntity
import com.verolynz.kelompok5.inoter.utils.toListAtletResponse
import com.verolynz.kelompok5.inoter.utils.toListCOEntity
import com.verolynz.kelompok5.inoter.utils.toListCOResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OlahragaRepository private constructor(
    private val cODao: CODao,
    private val atletDao: AtletDao,
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

    fun getAllAtletlist(): LiveData<List<AtletEntity>> = atletDao.getAllAtlet()
    companion object {
        @Volatile
        private var instance: OlahragaRepository? = null

        fun getInstance(
            cODao: CODao,
            atletDao: AtletDao,
            executorsUtils: ExecutorsUtils
        ): OlahragaRepository =
        // Jika instance sudah ada, kembalikan instance tersebut.
            // Jika instance belum ada, buat instance baru.
            instance ?: synchronized(this) {
                instance ?: OlahragaRepository(cODao, atletDao, executorsUtils)
            }.also { instance = it }
    }
}