package com.capstone.ezmoney.addItem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.ezmoney.ViewPagerAdapter
import com.capstone.ezmoney.databinding.ActivityAddItemBinding
import com.google.android.material.tabs.TabLayoutMediator

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Income"
                1 -> "Expense"
                else -> throw IllegalStateException("Invalid position")
            }
        }.attach()

        binding.btnSave.setOnClickListener {

        }
    }
}
