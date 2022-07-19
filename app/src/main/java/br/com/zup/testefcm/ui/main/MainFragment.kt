package br.com.zup.testefcm.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.zup.testefcm.R
import br.com.zup.testefcm.databinding.FragmentMainBinding
import br.com.zup.testefcm.domain.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding : FragmentMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initObserver()
    }

    private fun initObserver(){
        viewModel.currentToken.observe(viewLifecycleOwner){
            binding.tvToken.text = it
        }
        viewModel.lastNotification.observe(viewLifecycleOwner){
            binding.tvNotificationTitle.text = it.title
            binding.tvNotificationBody.text = it.body
        }
    }
}