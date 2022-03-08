package com.example.kiosk_ui_event

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBase(context: Context?, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(database: SQLiteDatabase?) { //테이블 설계 내용
        val sql= "CREATE TABLE IF NOT EXISTS user_table(seq INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, id TEXT, email TEXT, phonenum INTEGER, pw TEXT);"
        val sql2= "CREATE TABLE idpw_table(seq INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, pw TEXT);"
        database!!.execSQL(sql)
        database!!.execSQL(sql2)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {   //table 삭제할 때 사용
    }
}