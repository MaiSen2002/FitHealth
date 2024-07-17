package com.bloodpressure.bloodtracker.bptracker.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentResultBloodSugarBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.BloodSugarAdapter
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.add.AddBloodSugarViewModel
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.bloodpressure.bloodtracker.bptracker.util.Utils.Companion.getStatusFromSugarConcentration
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class ResultBloodSugarFragment: BaseFragment<FragmentResultBloodSugarBinding>() {
    override fun createViewBinding() = FragmentResultBloodSugarBinding.inflate(layoutInflater)

    private lateinit var adapter: BloodSugarAdapter
    private val viewModel: AddBloodSugarViewModel by viewModels()

    companion object {
        var isEdit = false
        var isFromHistory = false
    }

    override fun initializeComponent() {
        if (arguments == null) {
            return
        }
        viewModel.initArguments(arguments)
        adapter = BloodSugarAdapter()
        binding.rcStatus.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(mContext, 4)
        binding.rcStatus.layoutManager = layoutManager
        binding.rcStatus.adapter = adapter

        adapter.data = Utils.getBloodSugarInfo(mContext)
    }

    override fun initializeEvent() {
        binding.btnBack.setOnSingleClickListener {
            if (!isFromHistory) {
                val intentPreview = Intent(requireContext(), HomeActivity::class.java)
                intentPreview.putExtra(Utils.KEY_TRACKER, 1)
                startActivity(intentPreview)
            } else {
                requireActivity().finish()
            }
        }

        binding.btnEdit.setOnSingleClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
//            intent.putExtra(Utils.KEY_EDIT,Utils.EDIT_BLOOD_SUGAR)
            intent.putExtra(Utils.KEY_TRACKER, 5)
            intent.putExtra(
                Utils.KEY_DATA,
                Gson().toJson(viewModel.bloodSugar)
            )
            startActivity(intent)
        }

        binding.txtViewAll.setOnSingleClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra(Utils.KEY_START_INFO, true)
            startActivity(intent)
        }

        binding.btnGoHome.setOnSingleClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    override fun initializeData() {
    }

    @SuppressLint("SetTextI18n")
    override fun bindView() {
        if (isEdit) {
            binding.txtTitle.text = getString(R.string.edit_blood_sugar)
        }
        val data = viewModel.bloodSugar
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = format.format(data.date)
        val time = data.time
        binding.txtDateTime.text = "$time - $date"
        binding.txtNote.text = data.note
        binding.txtSugarConcentration.text = "${data.sugarConcentration} mmol/l"
        binding.txtStatus.text =
            getStatusFromSugarConcentration(requireContext(), data.sugarConcentration)
    }

}