package com.example.demokotlinbottomsheet.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.demokotlinbottomsheet.R
import com.example.demokotlinbottomsheet.model.LeadData
import java.util.ArrayList

class LeadGenAdapter(context: Context, private val leadList: ArrayList<LeadData>?,
                     private val listener: ClickItemListener) : RecyclerView.Adapter<LeadGenAdapter.CustomViewHolder >() {

    interface ClickItemListener {
        fun onClicked(model: Any,position:Int)
        fun onRemoveClicked(model: Any,position:Int)
    }

    class CustomViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val tv_name: TextView = itemView.findViewById(R.id.tv_name)
        val tv_contactNumber: TextView = itemView.findViewById(R.id.tv_contactNumber)
        val btn_remove_data: AppCompatButton = itemView.findViewById(R.id.btn_remove_data)
        val btn_preview: AppCompatButton = itemView.findViewById(R.id.btn_preview_data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lead_item, parent, false)

        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeadGenAdapter.CustomViewHolder, position: Int) {
        Log.d("Adapter","Leading List ${leadList?.get(position)}")

        holder.tv_name.text = leadList?.get(position)?.name
        holder.tv_contactNumber.text = leadList?.get(position)?.contact_number

        holder.btn_preview.setOnClickListener {
            listener.onClicked(leadList!!,position)
        }

        holder.btn_remove_data.setOnClickListener {
            listener.onRemoveClicked(leadList!!,position)
        }
    }

    override fun getItemCount(): Int {
        return leadList!!.size
    }


}