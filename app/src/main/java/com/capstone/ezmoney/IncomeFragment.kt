package com.capstone.ezmoney

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.Calendar
import java.util.Locale

class IncomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_income, container, false)

        val inputTanggal = view.findViewById<TextView>(R.id.inputTanggal)
        val iconCalendar = view.findViewById<View>(R.id.iconCalendar)
        val spinnerKategori = view.findViewById<Spinner>(R.id.spinnerKategori)
        val spinnerThreshold = view.findViewById<Spinner>(R.id.spinnerTreshold)
        val tvSavings = view.findViewById<TextView>(R.id.tvSavings)

        val calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            inputTanggal.text = String.format(
                Locale.getDefault(),
                "%02d/%02d/%d",
                dayOfMonth,
                month + 1,
                year
            )
        }

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        inputTanggal.setOnClickListener { datePickerDialog.show() }
        iconCalendar.setOnClickListener { datePickerDialog.show() }

        spinnerKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        spinnerThreshold.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Get the selected threshold category (Easy, Normal, Hard)
                val selectedThreshold = parent.getItemAtPosition(position).toString()

                // Handle the selected threshold to calculate the savings
                when (selectedThreshold) {
                    "Easy" -> {
                        // 30% savings
                    }
                    "Normal" -> {
                        // 50% savings
                    }
                    "Hard" -> {
                        // 80% savings
                    }
                }

                Log.d("ExpanseFragment", "Selected Threshold: $selectedThreshold")
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case where nothing is selected (optional)
            }
        }
        return view
    }
}
