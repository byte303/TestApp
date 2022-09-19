package frog.company.testapplication.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import frog.company.testapplication.databinding.FragmentHomeBinding
import frog.company.testapplication.helper.UtilsNotifications

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        try{
            val intent1 = Intent(requireContext(), UtilsNotifications.Receiver::class.java)
            val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(
                    requireContext(),
                    1,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
            } else {
                PendingIntent.getBroadcast(
                    requireContext(),
                    1, intent1, PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            val am = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager

            val INTERVAL = (60 * 60 * 1000).toLong()

            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), INTERVAL , pendingIntent)
        }catch (ex : Exception){
            Toast.makeText(requireContext(), "Ошибка: ${ex.message.toString()}", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}