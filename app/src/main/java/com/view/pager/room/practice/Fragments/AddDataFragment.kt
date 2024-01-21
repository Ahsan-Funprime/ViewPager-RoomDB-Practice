package com.view.pager.room.practice.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.view.pager.room.practice.R
import com.view.pager.room.practice.VMandRepo.DatabaseViewModel
import com.view.pager.room.practice.databinding.FragmentAddDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDataFragment : Fragment() {

    private var _binding: FragmentAddDataBinding? = null
    private val binding get() = _binding!!

    private var viewModel: DatabaseViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddDataBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            viewModel = DatabaseViewModel(requireContext())

            submitDataBtn.setOnClickListener {
                val name = editTextName.text.toString()
                val size = editTextSize.text.toString()
                if (name == "" && size == ""){
                    Toast.makeText(requireContext(),"Fill Form to Add Data",Toast.LENGTH_SHORT).show()
                }else{
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel?.insertData(name,size.toInt())
                    }
                    Toast.makeText(requireContext(),"Data Inserted",Toast.LENGTH_SHORT).show()
                    editTextName.setText("")
                    editTextSize.setText("")
                }
            }

        }

    }
}