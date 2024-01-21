package com.view.pager.room.practice.AdapterClasses

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.view.pager.room.practice.CompanionClass
import com.view.pager.room.practice.CompanionClass.longPressValue
import com.view.pager.room.practice.CompanionClass.selectAll
import com.view.pager.room.practice.CompanionClass.selected_items_list
import com.view.pager.room.practice.RoomDBClasses.DBEntity
import com.view.pager.room.practice.databinding.ItemViewLayoutBinding

class RecyclerAdapter(private val activity: Activity):
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewholder>() {

        var longPress = false

    var data_list = mutableListOf<DBEntity>()

        fun data( list: MutableList<DBEntity>){
            data_list.clear()
            list.forEach {
                data_list.add(it)
            }
            notifyDataSetChanged()
        }

    inner class RecyclerViewholder(val binding: ItemViewLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewholder {
        val binding =
            ItemViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewholder(binding)
    }

    override fun getItemCount(): Int {
        return data_list.size
    }

    override fun onBindViewHolder(holder: RecyclerViewholder, position: Int) {
        Log.e("AdapterLogs","Data in Adapter: "+data_list.toString())
        val item = data_list.get(position)
        holder.binding.nameRecycler.text = item.name
        holder.binding.sizeRecycler.text = item.size.toString()

        Log.e("AdapterLogs","Select All Value in Adapter: "+CompanionClass.selectAll)

        if (selected_items_list.size == data_list.size){
            holder.binding.checkboxRecycler.isChecked = true
        }
        if (selectAll){
            holder.binding.checkboxRecycler.isChecked = true
        } else {
            holder.binding.checkboxRecycler.isChecked = false
        }

        if (longPress){
            holder.binding.checkboxRecycler.visibility = View.VISIBLE
        }else{
            holder.binding.checkboxRecycler.visibility = View.GONE
        }

        holder.itemView.setOnLongClickListener {
            longPress = !longPress
            longPressValue.postValue(longPress)
            selected_items_list.clear()
           notifyDataSetChanged()
            holder.binding.checkboxRecycler.performClick()
            true
        }

        holder.itemView.setOnClickListener {
            holder.binding.checkboxRecycler.performClick()
        }

        holder.binding.checkboxRecycler.setOnClickListener {
            /*if (holder.binding.checkboxRecycler.isChecked){
                selected_items_list.add(it.id)
            }else{
                selected_items_list.remove(it.id)
            }*/

            Log.e("AdapterLogs","Selected Item List Size [Adapter]: "+ selected_items_list.size)
        }

    }

    fun areAllItemsSelected(): Boolean {
        return selected_items_list.size == itemCount
    }
}