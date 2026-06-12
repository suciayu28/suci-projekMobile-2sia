package com.example.suci_loyalty.complain

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suci_loyalty.data.AppDatabase
import com.example.suci_loyalty.databinding.FragmentComplaintBinding
import com.example.suci_loyalty.data.entity.ComplaintEntity
import kotlinx.coroutines.launch

class ComplaintFragment : Fragment() {

    private var _binding: FragmentComplaintBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ComplaintAdapter
    private lateinit var db: AppDatabase
    private val complaints = mutableListOf<ComplaintEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComplaintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())

        // Setup RecyclerView & Adapter
        adapter = ComplaintAdapter(complaints, this)
        binding.rvComplaints.layoutManager = LinearLayoutManager(requireContext())
        binding.rvComplaints.adapter = adapter

        fetchComplaints()

        // Klik FAB (+) untuk membuka form input aduan
        binding.fabAddComplaint.setOnClickListener {
            val intent = Intent(requireContext(), ComplaintFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchComplaints() {
        lifecycleScope.launch {
            val data = db.complaintDao().getAll()
            complaints.clear()
            complaints.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    // Fungsi hapus data aduan dari database Room
    fun deleteComplaint(complaint: ComplaintEntity) {
        lifecycleScope.launch {
            db.complaintDao().delete(complaint)
            fetchComplaints() // Segarkan list setelah dihapus
        }
    }

    override fun onResume() {
        super.onResume()
        fetchComplaints() // Auto-refresh saat kembali dari form aduan
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}