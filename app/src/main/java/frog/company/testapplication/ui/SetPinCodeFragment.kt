package frog.company.testapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import frog.company.testapplication.R
import frog.company.testapplication.custom.pincode.PinLockListener
import frog.company.testapplication.databinding.FragmentSetPinCodeBinding
import frog.company.testapplication.enums.EnumStatusUser
import frog.company.testapplication.enums.EnumTypePin
import frog.company.testapplication.helper.AppConst
import frog.company.testapplication.model.User
import io.paperdb.Paper

class SetPinCodeFragment : Fragment() {

    private var _binding : FragmentSetPinCodeBinding? = null
    private val binding get() = _binding!!

    private var forgotStr = ""

    private var param1: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(SendCodeFragment.ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetPinCodeBinding.inflate(inflater, container, false)

        binding.pinLockView.setPinLockListener(mPinLockListener)
        binding.pinLockView.attachIndicatorDots(binding.indicatorDots)

        onUpdateText()
        return binding.root
    }

    private var mPinLockListener: PinLockListener = object : PinLockListener {
        override fun onComplete(pin: String) {
            Log.d("TAG", "Pin complete: $pin")

            when(param1){
                EnumTypePin.RELOAD.value ->{
                    if(forgotStr == pin){
                        Paper.book().write(AppConst.PINCODE, pin)

                        val user = Paper.book().read<User>(AppConst.PAPER_DATA, User())!!
                        if(user.status == EnumStatusUser.ACTIVE.value){
                            requireActivity().supportFragmentManager.beginTransaction().replace(
                                R.id.viewPager,
                                HomeFragment()
                            ).commit()
                         }else{
                             requireActivity().supportFragmentManager.beginTransaction().replace(
                                 R.id.viewPager,
                                 CompleteRegistrationFragment()
                             ).commit()
                         }
                    }else{
                        Toast.makeText(requireContext(), "Код-пароль введён неверно!", Toast.LENGTH_SHORT).show()
                        binding.pinLockView.resetPinLockView()
                    }
                }
                EnumTypePin.CREATE.value ->{
                    param1 = EnumTypePin.RELOAD.value
                    forgotStr = pin
                    onUpdateText()
                    binding.pinLockView.resetPinLockView()
                }
                EnumTypePin.LOGIN.value ->{
                    val pinCode = Paper.book().read(AppConst.PINCODE, "")
                    if(pinCode == pin){
                        requireActivity().supportFragmentManager.beginTransaction().replace(
                            R.id.viewPager,
                            HomeFragment()
                        ).commit()
                    }else{
                        Toast.makeText(requireContext(), "Код-пароль введён неверно!", Toast.LENGTH_SHORT).show()
                        binding.pinLockView.resetPinLockView()
                    }
                }
            }
        }

        override fun onEmpty() {
            Log.d("TAG", "Pin empty")
        }

        override fun onPinChange(pinLength: Int, intermediatePin: String) {
            Log.d("TAG", "Pin changed, new length $pinLength with intermediate pin $intermediatePin")
        }
    }

    private fun onUpdateText(){
        when(param1){
            EnumTypePin.CREATE.value ->{
                binding.txtTitle.text = "Создайте код-пароль"
                binding.txtSubtitle.text = "Пожалуйста, придумайте код-пароль для входа в приложение"
            }
            EnumTypePin.RELOAD.value ->{
                binding.txtTitle.text = "Введите код-пароль повторно"
                binding.txtSubtitle.text = "Пожалуйста, придумайте код-пароль для входа в приложение"
            }
            EnumTypePin.LOGIN.value ->{
                binding.txtTitle.text = "Введите код-пароль"
                binding.txtSubtitle.text = "Пожалуйста, введите ваш код-пароль для входа в приложение"
            }
        }
    }

    companion object {
        const val ARG_PARAM1 = "param1"
        @JvmStatic
        fun newInstance(param1: Int) =
            SetPinCodeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}