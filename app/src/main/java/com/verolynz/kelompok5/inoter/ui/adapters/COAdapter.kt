package com.verolynz.kelompok5.inoter.ui.adapters//package com.verolynz.kelompok5.inoter.ui.adapters
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.verolynz.kelompok5.inoter.R
//import com.verolynz.kelompok5.inoter.data.local.COEntity
//import com.vinz.projectcontoh1.R
//import com.vinz.projectcontoh1.data.room.AppEntity
//
//class COAdapter(private var coList: List<COEntity>) :
//    RecyclerView.Adapter<COAdapter.COViewHolder>() {
//
//    // Deklarasi variabel untuk callback ketika item diklik
//    private lateinit var onItemClickCallback: OnItemClickCallback
//
//    // Fungsi untuk mengatur callback ketika item diklik
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }
//
//    // Interface untuk callback ketika item diklik
//    interface OnItemClickCallback {
//        fun onItemClicked(data: COEntity)
//        fun onDelete(data: COEntity, position: Int)
//        fun onUpdate(data: COEntity)
//    }
//
//    // Kelas ViewHolder untuk menyimpan referensi view yang digunakan dalam RecyclerView
//    class COViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
////        val foodName: TextView = itemView.findViewById(R.id.food_name)
////        val foodCategory: TextView = itemView.findViewById(R.id.food_category)
////        val btnEdit: ImageView = itemView.findViewById(R.id.btn_edit)
////        val btnHapus: ImageView = itemView.findViewById(R.id.btn_hapus)
//    }
//
//    // Fungsi untuk membuat ViewHolder (Melakukan setting untuk XML yang akan kita gunakan untuk menampilkan data)
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): COViewHolder {
//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_, parent, false)
//        return COViewHolder(view)
//    }
//
//    // Fungsi untuk mengikat data dengan ViewHolder (memasukkan data yang kita miliki ke dalam XML ViewHolder)
//    override fun onBindViewHolder(holder: COViewHolder, position: Int) {
//        val data = coList[position]
//
////        holder.foodName.text = data.name
////        holder.foodCategory.text = data.kategori
//
//        // Mengatur aksi ketika item diklik
//        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(coList[holder.adapterPosition]) }
//
//        // Mengatur aksi ketika button dihapus
//        holder.btnHapus.setOnClickListener {
//            onItemClickCallback.onDelete(
//                coList[holder.adapterPosition],
//                holder.adapterPosition
//            )
//        }
//
//        // Mengatur aksi ketika button diupdate
//        holder.btnEdit.setOnClickListener { onItemClickCallback.onUpdate(coList[holder.adapterPosition]) }
//
//    }
//
//    // Fungsi untuk mendapatkan jumlah item
//    override fun getItemCount(): Int = coList.size
//}