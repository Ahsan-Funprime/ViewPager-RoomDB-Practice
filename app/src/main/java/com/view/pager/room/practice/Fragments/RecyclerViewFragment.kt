package com.view.pager.room.practice.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.DatabaseView
import com.view.pager.room.practice.AdapterClasses.RecyclerAdapter
import com.view.pager.room.practice.CompanionClass
import com.view.pager.room.practice.CompanionClass.longPressValue
import com.view.pager.room.practice.CompanionClass.notify_
import com.view.pager.room.practice.CompanionClass.selectAll
import com.view.pager.room.practice.CompanionClass.selected_items_list
import com.view.pager.room.practice.R
import com.view.pager.room.practice.RoomDBClasses.DBEntity
import com.view.pager.room.practice.VMandRepo.DatabaseViewModel
import com.view.pager.room.practice.databinding.FragmentRecyclerViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecyclerViewFragment : Fragment() {

    var db_mutableList = mutableListOf<DBEntity>()

    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    private var viewModel: DatabaseViewModel? = null

    private var adapter_recycler: RecyclerAdapter? = null

    private var isLongPressed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            viewModel = DatabaseViewModel(requireContext())

            CoroutineScope(Dispatchers.IO).launch {
                viewModel?.getAllData()
            }

            adapter_recycler = RecyclerAdapter(requireActivity())
            recyclerData.layoutManager = LinearLayoutManager(requireContext())
            recyclerData.adapter = adapter_recycler

            viewModel?.dbData?.observe(viewLifecycleOwner) { dbData ->
                db_mutableList = dbData.toMutableList()
                Log.e("RecyclerFragmentLogs", "Data in List: " + db_mutableList.toString())
                recyclerData.adapter = adapter_recycler

                adapter_recycler?.data(db_mutableList)
            }

            longPressValue.observe(viewLifecycleOwner){_longPress->
                isLongPressed = _longPress
                if (isLongPressed){
                    constrainCheckbox.visibility = View.VISIBLE
                }else{
                    constrainCheckbox.visibility = View.GONE
                }
            }

            notify_.observe(viewLifecycleOwner){
                if (it){
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel?.getAllData()
                    }
                    adapter_recycler?.data(db_mutableList)
                    recyclerData.adapter = adapter_recycler
                }
            }

            swipeRefreshHistory.setOnRefreshListener{
                if (!isLongPressed){
                    adapter_recycler?.data(db_mutableList)
                    recyclerData.adapter = adapter_recycler
                }
                swipeRefreshHistory.isRefreshing = false
            }

            checkBoxSelectAll.setOnClickListener {
                selectAll = !selectAll
                selected_items_list.clear()
                if (selectAll){
                    db_mutableList.forEach{
                        it.id?.let { it1 -> selected_items_list.add(it1) }
                    }
                }else{
                    selected_items_list.clear()
                }
                Log.e("RecyclerFragmentLogs","Selected Item List Size[Select All]: "+ selected_items_list.size)
                adapter_recycler?.notifyDataSetChanged()
            }
        }
    }
}