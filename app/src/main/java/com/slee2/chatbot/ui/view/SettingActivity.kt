package com.slee2.chatbot.ui.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.slee2.chatbot.data.model.MessageViewModel
import com.slee2.chatbot.data.model.Setting
import com.slee2.chatbot.databinding.ActivitySettingBinding
import com.slee2.chatbot.ui.viewmodel.SettingViewModel
import com.slee2.chatbot.utils.Calculator
import com.slee2.chatbot.utils.SettingType.FREQUENCY_PENALTY
import com.slee2.chatbot.utils.SettingType.PREFERENCE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val settingViewModel: SettingViewModel by viewModels()
    private val messageViewModel: MessageViewModel by viewModels()

    private lateinit var calculator: Calculator

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("SettingActivity", "Start Setting Activity")
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        calculator = Calculator()

        binding.resetBtn.setOnClickListener {
            Log.i("SettingActivity", "Reset Button Click")
            messageViewModel.deleteAllMessage()
            Log.i("SettingActivity", "Delete Success")
            finish()
        }

        binding.saveBtn.setOnClickListener {
            val preference: Double = calculator.convertIntToPreference(binding.tpSeekBar.progress)
            val preferenceSetting = Setting(PREFERENCE, preference)
            val frequencyPenalty: Double = calculator.convertIntToFrequencyPenalty(binding.fpSeekBar.progress)
            val frequencyPenaltySetting = Setting(FREQUENCY_PENALTY, frequencyPenalty)
            settingViewModel.saveSetting(preferenceSetting)
            settingViewModel.saveSetting(frequencyPenaltySetting)
            binding.saveBtn.setTextColor(Color.parseColor("#276221"))
            binding.saveBtn.setBackgroundColor(Color.parseColor("#cce7c9"))
            Toast.makeText(this, "Save Success", Toast.LENGTH_LONG).show()
            finish()
        }

        lifecycleScope.launch {
            settingViewModel.allSettings.collectLatest { settings ->
                for (setting in settings) {
                    if (setting.type == PREFERENCE) {
                        binding.tpProgress.text = setting.value.toString()
                        binding.tpSeekBar.progress = calculator.convertIntByPreference(setting.value)
                    } else if (setting.type == FREQUENCY_PENALTY) {
                        binding.fpProgress.text = setting.value.toString()
                        binding.fpSeekBar.progress = calculator.convertIntByFrequencyPenalty(setting.value)
                    }
                }
            }
        }

        binding.tpSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.tpProgress.text = calculator.convertIntToPreference(progress).toString()
                binding.saveBtn.setTextColor(Color.parseColor("#cce7c9"))
                binding.saveBtn.setBackgroundColor(Color.parseColor("#276221"))
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.fpSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.fpProgress.text = calculator.convertIntToFrequencyPenalty(progress).toString()
                binding.saveBtn.setTextColor(Color.parseColor("#cce7c9"))
                binding.saveBtn.setBackgroundColor(Color.parseColor("#276221"))
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}