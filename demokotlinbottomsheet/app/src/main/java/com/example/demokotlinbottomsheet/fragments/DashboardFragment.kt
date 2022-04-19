package com.example.demokotlinbottomsheet.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demokotlinbottomsheet.R
import com.example.demokotlinbottomsheet.databinding.FragmentDashboardBinding
import com.example.demokotlinbottomsheet.databinding.FragmentLeadListBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter


/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    private lateinit var mBinding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_dashboard, container, false)
        mBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        initPieChart()

        setDataToPieChart()
        return mBinding.root
    }

    private fun initPieChart() {
        mBinding.pieChart.setUsePercentValues(true)
        mBinding.pieChart.description.text = "dsadsa"
        //hollow pie chart
        mBinding.pieChart.isDrawHoleEnabled = false
        mBinding.pieChart.setTouchEnabled(false)
        mBinding.pieChart.setDrawEntryLabels(false)
        //adding padding
        mBinding.pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        mBinding.pieChart.setUsePercentValues(true)
        mBinding.pieChart.isRotationEnabled = false
        mBinding.pieChart.setDrawEntryLabels(false)
        mBinding.pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        mBinding.pieChart.legend.isWordWrapEnabled = true

    }

    private fun setDataToPieChart() {
        mBinding.pieChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(72f, "Android"))
        dataEntries.add(PieEntry(26f, "Ios"))
        dataEntries.add(PieEntry(2f, "Other"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#4DD0E1"))
        colors.add(Color.parseColor("#FFF176"))
        colors.add(Color.parseColor("#FF8A65"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        mBinding.pieChart.data = data
        data.setValueTextSize(15f)
        mBinding.pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        mBinding.pieChart.animateY(1400, Easing.EaseInOutQuad)

        //create hole in center
        mBinding.pieChart.holeRadius = 58f
        mBinding.pieChart.transparentCircleRadius = 61f
        mBinding.pieChart.isDrawHoleEnabled = true
        mBinding.pieChart.setHoleColor(Color.WHITE)


        //add text in center
        mBinding.pieChart.setDrawCenterText(true);
        mBinding.pieChart.centerText = "Mobile OS Market share"



        mBinding.pieChart.invalidate()

    }
}