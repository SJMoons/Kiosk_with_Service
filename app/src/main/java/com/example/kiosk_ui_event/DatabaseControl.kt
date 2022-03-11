package com.example.kiosk_ui_event

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class DatabaseControl {

    fun makeReadSql(table: String, column: ArrayList<String>, data:ArrayList<String>): String {
        var sql = "SELECT" + " * " + "FROM " + table + " WHERE "
        for (index in 0 until column.count()) {
            sql +=  column[index] + "=" + "'" + data[index] + "'"
            if (index < column.count() - 1) {
                sql += " and "
            }
        }
        return sql
    }

    fun readIdPW(database:SQLiteDatabase,sql:String): ArrayList<ArrayList<String>>{
        var result: Cursor = database.rawQuery(sql, null)
        val dataList = ArrayList<ArrayList<String>>()

        while (result.moveToNext()) {// 다음 raw로 넘어감
            val seq = result.getInt(0)
            val id = result.getString(1)
            val pw = result.getString(2)
            val raw = arrayListOf(seq.toString(),id,pw)
            dataList.add(raw)
        }
        result.close()
        return dataList
    }

    fun readPaymentHistory(database:SQLiteDatabase,sql:String): ArrayList<ArrayList<String>>{
        var result: Cursor = database.rawQuery(sql, null)
        val dataList = ArrayList<ArrayList<String>>()

        while (result.moveToNext()) {// 다음 raw로 넘어감
            val image = result.getString(2)
            val menu = result.getString(3)
            val raw = arrayListOf(image,menu)
            dataList.add(raw)
        }
        result.close()
        return dataList
    }

    fun create(database:SQLiteDatabase, table:String, column:ArrayList<String>,data:ArrayList<String>) {
        var sql = "INSERT INTO " + table + "("
        for (index in 0 until column.count()) {
            sql += "'"+ column[index] + "'"
            if (index < column.count() -1) {
                sql += ", "
            }
        }
        sql += ")"
        sql += "VALUES("
        for (index in 0 until column.count()) {
            sql += "'" + data[index] + "'"
            if (index < column.count() -1) {
                sql += ", "
            }
        }
        sql += ")"
        Log.d("anstmd","$sql")
        database.execSQL(sql)
    }
}