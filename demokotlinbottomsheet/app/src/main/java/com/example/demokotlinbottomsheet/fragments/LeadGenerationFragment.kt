package com.example.demokotlinbottomsheet.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.demokotlinbottomsheet.databinding.FragmentLeadGenerationBinding
import java.lang.Exception
import java.util.ArrayList

import android.provider.MediaStore
import android.database.Cursor

import android.net.Uri
import android.R.attr.data

import android.content.ClipData
import android.content.ClipData.Item
import android.util.Log
import com.example.demokotlinbottomsheet.adapters.DemoDataBaseHelper


/**
 * A simple [Fragment] subclass.
 * Use the [LeadGenerationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeadGenerationFragment : Fragment() {

    private lateinit var mBinding: FragmentLeadGenerationBinding

    private var PICK_IMAGE_MULTIPLE = 1
    private lateinit var imageEncoded: String
    private lateinit var imagesEncodedList: ArrayList<String>
    //
    private lateinit var demoDataBaseHelper: DemoDataBaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_lead_generation, container, false)
        mBinding = FragmentLeadGenerationBinding.inflate(inflater, container, false)

        demoDataBaseHelper = DemoDataBaseHelper(context)

        mBinding.imgvImagePicker.setOnClickListener {
//            openGallery()
        }

        mBinding.btnAddData.setOnClickListener{
            insertDataIntoDatabase(mBinding.edtCustName.text.toString(), mBinding.edtContNumber.text.toString(),
                mBinding.edtProjectName.text.toString(), mBinding.edtFlatDetails.text.toString(),
            mBinding.edtPropertyCost.text.toString(), mBinding.edtLoanRequirement.text.toString(),
            mBinding.edtState.text.toString(), mBinding.edtCity.text.toString())
        }

        return mBinding.root
    }

    private fun insertDataIntoDatabase(
        customerName: String?, contactNumber: String?, projectName: String?,
        flatDetails: String?, propertyCost: String?, loanRequirement: String?,
        state: String?, city: String?){
        val lastId = demoDataBaseHelper.insertTagsIntoTagMaster(customerName, contactNumber, projectName,
            flatDetails, propertyCost, loanRequirement, state, city)
        Log.d("LeadGenFrag", "LastID $lastId")
        Toast.makeText(context,"Product inserted successfully",Toast.LENGTH_SHORT).show()
        demoDataBaseHelper.close()
    }

    private fun openGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try{
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                && null != data){
                // Get the Image from data


                // Get the Image from data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                imagesEncodedList = ArrayList()

                if (data.data !=null){
                    val mImageUri: Uri = data.data!!

                    // Get the cursor

                    // Get the cursor
                    val cursor: Cursor? = activity?.contentResolver?.query(
                        mImageUri,
                        filePathColumn, null, null, null
                    )
                    // Move to first row
                    // Move to first row
                    cursor?.moveToFirst()

                    val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
                    if (cursor != null) {
                        imageEncoded = cursor.getString(columnIndex!!)
                    }
                    cursor?.close()
                }else {
                    if (data.clipData != null) {
//                        val mClipData: ClipData = attr.data.getClipData()
                        val mClipData: ClipData = data.clipData!!
                        val mArrayUri = ArrayList<Uri>()
                        for (i in 0 until mClipData.itemCount) {
                            val item = mClipData.getItemAt(i)
                            val uri = item.uri
                            mArrayUri.add(uri)
                            // Get the cursor
                            val cursor: Cursor? =
                                activity?.contentResolver?.query(uri, filePathColumn, null, null, null)
                            // Move to first row
                            cursor?.moveToFirst()
                            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
                            if (cursor != null) {
                                imageEncoded = cursor.getString(columnIndex!!)
                                imagesEncodedList.add(imageEncoded)
                            }
                            cursor?.close()
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size);
                    }
                }
            }else {
                Toast.makeText(context, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
            }
        }catch (ex: Exception){
            Log.v("LOG_TAG", "Error : " + ex.printStackTrace());
            /*Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
                .show()*/
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}