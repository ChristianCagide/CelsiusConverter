package com.cagide.celciusconverter.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.cagide.celciusconverter.models.Registry
import java.util.*
import kotlin.collections.ArrayList


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        private val DATABASE_NAME = "CelsiusConverter"
        private val TABLE_NAME = "Conversion"
        private val COL_DATE = "Fecha"
        private val COL_CELSIUS = "Celsius"
        private val COL_FAHRENHEIT = "Fahrenheit"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_DATE + " VARCHAR(10)," +
                COL_CELSIUS + " FLOAT," +
                COL_FAHRENHEIT + " FLOAT)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val deleteTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(deleteTable)
        onCreate(db)
    }

    fun insertData(celsius: Float, fahrenheit: Float){
        val db = this.writableDatabase

        val cv = ContentValues().apply {
            put(COL_DATE, Calendar.getInstance().time.toString())
            put(COL_CELSIUS, celsius)
            put(COL_FAHRENHEIT, fahrenheit)
        }

        var result = db?.insert(TABLE_NAME, null, cv)
        if(result == -1.toLong())
            Log.e("db insert", "Falló al insertar")
        else
            Log.i("db insert", "Valores insertados: Celsius:" + celsius + " Fahrenheit: " + fahrenheit)
    }

    val allRecords:List<Registry>
    get() {
        val lstRegistry = ArrayList<Registry>()
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        if(cursor.moveToFirst()){
            do {
                val registry = Registry()
                registry.date = cursor.getString(cursor.getColumnIndex(COL_DATE))
                registry.celsius = cursor.getFloat(cursor.getColumnIndex(COL_CELSIUS))
                registry.fahrenheit = cursor.getFloat(cursor.getColumnIndex(COL_FAHRENHEIT))

                lstRegistry.add(registry)
            } while (cursor.moveToNext())
        }
        return lstRegistry
    }

}