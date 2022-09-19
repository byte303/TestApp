package frog.company.testapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import frog.company.testapplication.R
import frog.company.testapplication.databinding.FragmentCompleteRegistrationBinding
import frog.company.testapplication.enums.EnumStatusUser
import frog.company.testapplication.helper.AppConst
import frog.company.testapplication.model.User
import io.paperdb.Paper

class CompleteRegistrationFragment : Fragment() {

    private var _binding : FragmentCompleteRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompleteRegistrationBinding.inflate(inflater, container, false)

        binding.btnDone.setOnClickListener {
            val user = Paper.book().read<User>(AppConst.PAPER_DATA, User())!!
            user.status = EnumStatusUser.ACTIVE.value
            Paper.book().write(AppConst.PAPER_DATA, user)

            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.viewPager,
                HomeFragment()
            ).commit()
        }

        return binding.root
    }
}