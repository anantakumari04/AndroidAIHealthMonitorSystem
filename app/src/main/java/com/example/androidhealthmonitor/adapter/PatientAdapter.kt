package com.example.androidhealthmonitor.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidhealthmonitor.R
import com.example.androidhealthmonitor.data.db.Patient


class PatientAdapter(private val onClick: (Long) -> Unit) : ListAdapter<Patient, PatientAdapter.VH>(DIFF) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Patient>() {
            override fun areItemsTheSame(oldItem: Patient, newItem: Patient) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Patient, newItem: Patient) = oldItem == newItem
        }
    }


    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvName: TextView = v.findViewById(R.id.tvName)
        val tvSummary: TextView = v.findViewById(R.id.tvSummary)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
        return VH(v)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = getItem(position)
        holder.tvName.text = p.name
        holder.tvSummary.text = "Temp: ${p.temperature}°C · HR: ${p.heartRate} · BP: ${p.systolic}/${p.diastolic}"
        holder.itemView.setOnClickListener { onClick(p.id) }
    }
}