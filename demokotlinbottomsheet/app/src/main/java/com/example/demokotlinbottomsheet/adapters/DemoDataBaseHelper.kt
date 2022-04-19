package com.example.demokotlinbottomsheet.adapters

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import org.json.JSONObject

import android.database.Cursor

import org.json.JSONArray




class DemoDataBaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "DemoLeadDatabase"
        private const val TABLE_LEAD = "LeadTable"
        private const val KEY_ID = "id"
        private const val KEY_CUSTOMER_NAME = "name"
        private const val KEY_CONT_NUMBER = "contact_number"
        private const val KEY_PROJECT_NAME = "project_name"
        private const val KEY_FLAT_DETAILS = "flat_details"
        private const val KEY_PROPERTY_COST = "property_cost"
        private const val KEY_LOAN_REQUIREMENT = "loan_requirement"
        private const val KEY_STATE = "state"
        private const val KEY_CITY = "city"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_LEAD + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CUSTOMER_NAME + " TEXT,"
                + KEY_CONT_NUMBER + " TEXT,"
                + KEY_PROJECT_NAME + " TEXT,"
                + KEY_FLAT_DETAILS + " TEXT,"
                + KEY_PROPERTY_COST + " TEXT,"
                + KEY_LOAN_REQUIREMENT + " TEXT,"
                + KEY_STATE + " TEXT,"
                + KEY_CITY + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_LEAD")
        onCreate(db)
    }

    fun insertTagsIntoTagMaster(
        customerName: String?, contactNumber: String?, projectName: String?,
        flatDetails: String?, propertyCost: String?, loanRequirement: String?,
        state: String?, city: String?
    ): Long {
        var returnV: Long = 0
        val sqliteDatabase = this.writableDatabase
        sqliteDatabase.beginTransaction()
        try {
            val con2 = ContentValues()
            con2.put(KEY_CUSTOMER_NAME, customerName)
            con2.put(KEY_CONT_NUMBER, contactNumber)
            con2.put(KEY_PROJECT_NAME, projectName)
            con2.put(KEY_FLAT_DETAILS, flatDetails)
            con2.put(KEY_PROPERTY_COST, propertyCost)
            con2.put(KEY_LOAN_REQUIREMENT, loanRequirement)
            con2.put(KEY_STATE, state)
            con2.put(KEY_CITY, city)
            returnV = sqliteDatabase.insert(TABLE_LEAD, null, con2)
            sqliteDatabase.setTransactionSuccessful()
        } catch (e: Exception) {
            returnV = 0
        } finally {
            sqliteDatabase.endTransaction()
        }
        return returnV
    }

    fun getLeadFromTable(): JSONArray {
        val sqliteDatabase = this.writableDatabase
        val resultSet = JSONArray()
        var rowObject: JSONObject? = null
        sqliteDatabase.beginTransaction()
        try {
            val cursor = sqliteDatabase.rawQuery("SELECT * FROM $TABLE_LEAD", null)
            cursor.moveToFirst()

            //
            while (!cursor.isAfterLast) {
                val totalColumn = cursor.columnCount
                rowObject = JSONObject()
                for (i in 0 until totalColumn) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i))
                        } catch (e: Exception) {
                            Log.d("TAG", e.message!!)
                        }
                    }
                }
                resultSet.put(rowObject)
                cursor.moveToNext()
            }
            cursor.close()
            sqliteDatabase.setTransactionSuccessful()
        } catch (e: Exception) {
            rowObject = null
        } finally {
            sqliteDatabase.endTransaction()
        }
        return resultSet
    }

    fun deleteLead(id: String){
//        var returnV: Long = 0
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_LEAD WHERE $KEY_ID='$id'")
//        returnV = db.delete(TABLE_LEAD, id, null).toLong()
    }
}