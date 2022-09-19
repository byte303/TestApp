package frog.company.testapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import frog.company.testapplication.R
import frog.company.testapplication.databinding.FragmentRegistrationBinding
import frog.company.testapplication.enums.EnumTypeCode

class RegistrationFragment : Fragment() {

    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.viewPager,
                AuthorizationFragment()
            ).commit()
        }

        binding.btnNext.setOnClickListener {
            val number = binding.edtPhone.text.toString()
            val fragment = SendCodeFragment().apply {
                arguments = Bundle().apply {
                    putString(SendCodeFragment.ARG_PARAM1, number)
                    putInt(SendCodeFragment.ARG_PARAM2, EnumTypeCode.CREATE.value)
                }
            }

            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.viewPager,
                fragment
            ).addToBackStack(null).commit()
        }

        return binding.root
    }
}