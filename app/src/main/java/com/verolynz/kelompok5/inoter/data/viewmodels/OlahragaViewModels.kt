package com.verolynz.kelompok5.inoter.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.verolynz.kelompok5.inoter.data.local.AtletEntity
import com.verolynz.kelompok5.inoter.data.local.COEntity
import com.verolynz.kelompok5.inoter.data.local.UsersEntity
import com.verolynz.kelompok5.inoter.data.remote.AtletResponse
import com.verolynz.kelompok5.inoter.data.remote.COResponse
import com.verolynz.kelompok5.inoter.data.repositories.OlahragaRepository

class OlahragaViewModels(private val olaharagaRepository: OlahragaRepository) : ViewModel(){
    val listCO: LiveData<List<COResponse>> = olaharagaRepository.listCO
    val ListAtlet: LiveData<List<AtletResponse>> = olaharagaRepository.listAtlet
    fun getAllCO() {
        olaharagaRepository.getAllCO()
    }
    fun getAllCOlist(): LiveData<List<COEntity>> {
        return olaharagaRepository.getAllCOlist()
    }
    fun insertCO(coEntity: COEntity) {
        olaharagaRepository.insertCO(coEntity)
    }
    fun updateCO(coEntity: COEntity) {
        olaharagaRepository.updateCO(coEntity)
    }
    fun deleteCO(coEntity: COEntity) {
        olaharagaRepository.deleteCO(coEntity)
    }
    fun getAllAtlet() {
        olaharagaRepository.getAllAtlet()
    }
    fun getAllAtletlist(): LiveData<List<AtletEntity>> {
        return olaharagaRepository.getAllAtletlist()
    }
    fun insertAtlet(atletEntity: AtletEntity) {
        olaharagaRepository.insertAtlet(atletEntity)
    }
    fun updateAtlet(atletEntity: AtletEntity) {
        olaharagaRepository.updateAtlet(atletEntity)
    }
    fun deleteAtlet(atletEntity: AtletEntity) {
        olaharagaRepository.deleteAtlet(atletEntity)
    }
    fun getAllUserslist(): LiveData<List<UsersEntity>> {
        return olaharagaRepository.getAllUserslist()
    }
    fun insertUsers(usersEntity: UsersEntity) {
        olaharagaRepository.insertUsers(usersEntity)
    }
    fun updateUsers(usersEntity: UsersEntity) {
        olaharagaRepository.updateUsers(usersEntity)
    }
    fun deleteUsers(usersEntity: UsersEntity) {
        olaharagaRepository.deleteUsers(usersEntity)
    }








}