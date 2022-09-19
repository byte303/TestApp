package frog.company.testapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import frog.company.testapplication.R
import frog.company.testapplication.databinding.FragmentSendCodeBinding
import frog.company.testapplication.enums.EnumStatusUser
import frog.company.testapplication.enums.EnumTypeCode
import frog.company.testapplication.enums.EnumTypePin
import frog.company.testapplication.helper.AppConst
import frog.company.testapplication.model.User
import io.paperdb.Paper

class SendCodeFragment : Fragment() {

    private var _binding : FragmentSendCodeBinding? = null
    private val binding get() = _binding!!

    private var param1: String? = null
    private var type: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            type = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSendCodeBinding.inflate(inflater, container, false)

        binding.txtNumberPhone.text = String.format("+$param1")

        binding.pinVIew.setOtpCompletionListener { otp ->
            val pin = otp

            if(EnumTypeCode.LOGIN.value == type){
                val fragment = SetPinCodeFragment.newInstance(EnumTypePin.CREATE.value)
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.viewPager,
                    fragment
                ).commit()
            }else{
                val user = User(
                    "",
                    param1!!,
                    EnumStatusUser.NOT_ACTIVE.value
                )
                Paper.book().write(AppConst.PAPER_DATA, user)

                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.viewPager,
                    SetNameFragment()
                ).addToBackStack(null).commit()
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }

    companion object {
        const val ARG_PARAM1 = "param1"
        const val ARG_PARAM2 = "param2"
    }
}