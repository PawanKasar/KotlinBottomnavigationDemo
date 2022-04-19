package com.example.demokotlinbottomsheet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demokotlinbottomsheet.databinding.FragmentLeadListBinding
import org.json.JSONException
import android.util.Log
import org.json.JSONArray
import android.os.Build
import androidx.annotation.RequiresApi

import android.os.AsyncTask
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demokotlinbottomsheet.adapters.DemoDataBaseHelper
import com.example.demokotlinbottomsheet.adapters.LeadGenAdapter
import com.example.demokotlinbottomsheet.customdialog.CustomDialog
import com.example.demokotlinbottomsheet.model.LeadData
import com.google.gson.Gson


/**
 * A simple [Fragment] subclass.
 * Use the [LeadListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeadListFragment : Fragment() {

    private lateinit var mBinding: FragmentLeadListBinding
    //
    private lateinit var demoDataBaseHelper: DemoDataBaseHelper
    var jsonArray: JSONArray? = null
    private var leadArrayList = ArrayList<LeadData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_lead_list, container, false)
        mBinding = FragmentLeadListBinding.inflate(inflater, container, false)
        demoDataBaseHelper = DemoDataBaseHelper(context)

        getDataFromDatabase()

        return mBinding.root
    }

    private fun getDataFromDatabase(){
        object : AsyncTask<Any?, Any?, JSONArray>() {
            override fun onPreExecute() {
                super.onPreExecute()
                /*pd = ProgressDialog(activity, R.style.MyTheme)
                pd.setCancelable(false)
                pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small)
                pd.show()*/
            }

            override fun doInBackground(vararg params: Any?): JSONArray {
                jsonArray =
                    demoDataBaseHelper.getLeadFromTable() // roomNo : 26 , rackId : 203R001S0026
                Log.d("json", jsonArray.toString() + "")
                //sqLiteDatabase.close();
                return jsonArray!!
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            override fun onPostExecute(jsonArray: JSONArray) {
                super.onPostExecute(jsonArray)
                Log.d("jsonArrayOne", jsonArray.toString())
                /*if (pd.isShowing()) {
                    pd.dismiss()
                }*/
                try {
                    //arrayModelList.clear();
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("HomeFragment", "getting jsonObject $jsonObject")
                        val leadData = Gson().fromJson(jsonObject.toString(), LeadData::class.java)
                        Log.e("HomeFragment", "LeadData $leadData")

                        leadArrayList?.add(leadData)

                    }
                    mBinding.rvLeadList.layoutManager = LinearLayoutManager(context)
                    val adapterListView =
                        LeadGenAdapter(context!!, leadArrayList, object : LeadGenAdapter.ClickItemListener{
                            override fun onClicked(model: Any, position: Int) {
                                CustomDialog().show(requireActivity().supportFragmentManager,"CustomDialog")
                            }

                            override fun onRemoveClicked(model: Any, position: Int) {
                                demoDataBaseHelper.deleteLead(position.toString())
//                                Log.d("LeadListFrag", "LastID $lastId")
                                Toast.makeText(context,"Product deleted successfully", Toast.LENGTH_SHORT).show()
//                                demoDataBaseHelper.close()
                                leadArrayList.clear()
                            }

                        })
                    mBinding.rvLeadList.adapter = adapterListView
                    adapterListView.notifyDataSetChanged()

                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.execute()
    }
}