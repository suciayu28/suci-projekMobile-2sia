package com.example.suci_loyalty.pertemuan10

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import com.example.suci_loyalty.data.entity.PermohonanSuratEntity
import com.example.suci_loyalty.databinding.DialogQrBuktiSuratBinding
import com.example.suci_loyalty.databinding.ItemRiwayatSuratBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

// Class ini adalah Adapter, bukan Activity
class RiwayatSuratAdapter(private val listRiwayat: List<PermohonanSuratEntity>) :
    RecyclerView.Adapter<RiwayatSuratAdapter.ViewHolder>() {

    // Menghubungkan ke layout item_riwayat_surat.xml melalui View Binding
    class ViewHolder(val binding: ItemRiwayatSuratBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRiwayatSuratBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listRiwayat[position]

        // Memasukkan data dari Model ke TextView di XML
        holder.binding.tvNomorSurat.text = "${item.nomorPermohonan} (${item.jenisSurat})"
        holder.binding.tvStatus.text = item.status
        holder.binding.tvTanggal.text = item.waktu

        // Contoh logika warna berdasarkan status
        when (item.status) {
            "Ditolak" -> {
                holder.binding.tvStatus.setTextColor(Color.parseColor("#C53030"))
                holder.binding.tvStatus.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#FED7D7"))
                // Sembunyikan tombol QR jika ditolak
                holder.binding.btnGenerateQr.visibility = View.GONE
            }
            "Selesai" -> {
                holder.binding.tvStatus.setTextColor(Color.parseColor("#22543D"))
                holder.binding.tvStatus.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#C6F6D5"))
                // PERTEMUAN 13: Tampilkan tombol QR hanya jika surat Selesai
                holder.binding.btnGenerateQr.visibility = View.VISIBLE
                holder.binding.btnGenerateQr.setOnClickListener {
                    tampilkanDialogQr(holder, item)
                }
            }
            else -> {
                holder.binding.tvStatus.setTextColor(Color.parseColor("#2B6CB0"))
                holder.binding.tvStatus.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#EBF8FF"))
                holder.binding.btnGenerateQr.visibility = View.GONE
            }
        }
    }

    /**
     * PERTEMUAN 13: Menampilkan Dialog QR Code bukti surat.
     * QR Code berisi nomor permohonan, jenis surat, NIK, dan tanggal.
     * Dibuat menggunakan ZXing QRCodeWriter → BitMatrix → Bitmap.
     */
    private fun tampilkanDialogQr(holder: ViewHolder, item: PermohonanSuratEntity) {
        val context = holder.binding.root.context

        // Isi QR Code: data surat lengkap sebagai teks
        val isiQr = """
            BUKTI SURAT SAKUSURAT
            Nomor : ${item.nomorPermohonan}
            Jenis : ${item.jenisSurat}
            NIK   : ${item.nik}
            Tanggal: ${item.waktu}
            Status : ${item.status}
        """.trimIndent()

        // Generate QR Code Bitmap menggunakan ZXing
        val qrBitmap = generateQrBitmap(isiQr, 512)

        // Buat Dialog kustom menggunakan dialog_qr_bukti_surat.xml
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogQrBuktiSuratBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Set data ke dialog
        dialogBinding.tvNomorSuratDialog.text = "${item.nomorPermohonan} • ${item.jenisSurat}"
        dialogBinding.ivQrDialog.setImageBitmap(qrBitmap)
        dialogBinding.btnTutupDialog.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    /**
     * PERTEMUAN 13: Fungsi generate QR Code dari String teks menggunakan ZXing.
     * Sama persis dengan yang ada di GenerateQrFragment.
     */
    private fun generateQrBitmap(teks: String, ukuran: Int = 512): Bitmap? {
        return try {
            val hints = mapOf(
                EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H,
                EncodeHintType.MARGIN to 2,
                EncodeHintType.CHARACTER_SET to "UTF-8"
            )
            val bitMatrix = QRCodeWriter().encode(teks, BarcodeFormat.QR_CODE, ukuran, ukuran, hints)
            val lebar = bitMatrix.width
            val tinggi = bitMatrix.height
            val pixels = IntArray(lebar * tinggi) { i ->
                val x = i % lebar
                val y = i / lebar
                if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
            }
            Bitmap.createBitmap(lebar, tinggi, Bitmap.Config.ARGB_8888).apply {
                setPixels(pixels, 0, lebar, 0, 0, lebar, tinggi)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun getItemCount(): Int = listRiwayat.size
}