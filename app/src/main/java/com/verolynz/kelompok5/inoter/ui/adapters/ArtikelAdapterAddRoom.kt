import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.room.ArtikelDatabase

class ArtikelAdapterAddRoom(private var artikelList: List<ArtikelDatabase>) :
    RecyclerView.Adapter<ArtikelAdapterAddRoom.ArtikelViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ArtikelDatabase)
        fun onMoreClicked(data: ArtikelDatabase, position: Int)
    }

    class ArtikelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artikelName: TextView = itemView.findViewById(R.id.textViewTitle)
        val artikelDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val artikelImage: ImageView = itemView.findViewById(R.id.imageViewArtikel)
        val btnMore: ImageButton = itemView.findViewById(R.id.edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rvarticlewithbtnmore, parent, false)
        return ArtikelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtikelViewHolder, position: Int) {
        val data = artikelList[position]

        holder.artikelName.text = data.name
        holder.artikelDescription.text = data.description.shorten(85)

        // Mengatur gambar dari Uri
        val uri = Uri.parse(data.image.toString()) // Data.image di sini berupa String URI
        holder.artikelImage.setImageURI(uri)

        holder.btnMore.setOnClickListener {
            onItemClickCallback.onMoreClicked(data, holder.absoluteAdapterPosition)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(data)
        }
    }

    override fun getItemCount(): Int = artikelList.size

    private fun String.shorten(maxLength: Int): String {
        return if (this.length <= maxLength) this else "${this.substring(0, maxLength)}..."
    }
}
