package frog.company.testapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import frog.company.testapplication.R
import frog.company.testapplication.enums.EnumTypeCode
import frog.company.testapplication.helper.AppConst
import frog.company.testapplication.model.User
import frog.company.testapplication.databinding.FragmentAuthorizationBinding
import frog.company.testapplication.enums.EnumStatusUser
import io.paperdb.Paper

class AuthorizationFragment : Fragment() {

    private var _binding : FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.viewPager,
                RegistrationFragment()
            ).commit()
        }

        binding.btnNext.setOnClickListener {
            val number = binding.edtPhone.text.toString()

            val user = Paper.book().read<User>(AppConst.PAPER_DATA, User())!!
            user.status = EnumStatusUser.ACTIVE.value
            user.phone = number
            user.name = "Имя Фамилия"
            Paper.book().write(AppConst.PAPER_DATA, user)

            val fragment = SendCodeFragment().apply {
                arguments = Bundle().apply {
                    putString(SendCodeFragment.ARG_PARAM1, number)
                    putInt(SendCodeFragment.ARG_PARAM2, EnumTypeCode.LOGIN.value)
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