package com.view.pager.room.practice.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.view.pager.room.practice.AdapterClasses.PracticeViewpagerAdapter
import com.view.pager.room.practice.CompanionClass
import com.view.pager.room.practice.CompanionClass.longPressValue
import com.view.pager.room.practice.CompanionClass.notify_
import com.view.pager.room.practice.CompanionClass.selected_items_list
import com.view.pager.room.practice.VMandRepo.DatabaseViewModel
import com.view.pager.room.practice.databinding.FragmentPracticeViewPagerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PracticeViewPager : Fragment() {

    private var _binding: FragmentPracticeViewPagerBinding? = null
    private val binding get() = _binding!!

    var viewpagerAdapter: PracticeViewpagerAdapter? = null
    private var viewModel: DatabaseViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPracticeViewPagerBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpagerAdapter = PracticeViewpagerAdapter(childFragmentManager)

        binding.tabLayout.setupWithViewPager(binding.viewpager)
        binding.viewpager.offscreenPageLimit = 2

        viewpagerAdapter?.addFragment(AddDataFragment(),"Add Data")
        viewpagerAdapter?.addFragment(RecyclerViewFragment(),"Recycler View")

        binding.viewpager.adapter = viewpagerAdapter

        viewModel = DatabaseViewModel(requireContext())

        CompanionClass.longPressValue.observe(viewLifecycleOwner){
            if (it){
                binding.deleteBtn.visibility = View.VISIBLE
            }else{
                binding.deleteBtn.visibility = View.GONE
            }
        }

        binding.deleteBtn.setOnClickListener {
            Log.e("ViewPagerLogs","Selected Size on Delete btn click: "+ selected_items_list.size)
            if (selected_items_list.size == 0){
                Toast.makeText(requireContext(),"Select Item to delete...",Toast.LENGTH_SHORT).show()

            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    selected_items_list.forEach {
                        viewModel?.delete_byId(it)
                    }
                    notify_.postValue(true)
                    longPressValue.postValue(false)
                    selected_items_list.clear()
                }

            }
        }

    }

}