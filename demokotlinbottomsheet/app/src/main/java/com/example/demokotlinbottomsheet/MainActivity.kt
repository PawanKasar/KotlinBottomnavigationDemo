package com.example.demokotlinbottomsheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.demokotlinbottomsheet.databinding.ActivityMainBinding
import com.example.demokotlinbottomsheet.fragments.DashboardFragment
import com.example.demokotlinbottomsheet.fragments.LeadGenerationFragment
import com.example.demokotlinbottomsheet.fragments.LeadListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val firstFragment=DashboardFragment()
        val secondFragment=LeadGenerationFragment()
        val thirdFragment=LeadListFragment()

        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(firstFragment)
                R.id.lead_generation->setCurrentFragment(secondFragment)
                R.id.lead_list->setCurrentFragment(thirdFragment)

            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}