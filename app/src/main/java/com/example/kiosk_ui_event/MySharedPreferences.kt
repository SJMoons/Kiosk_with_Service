package com.example.kiosk_ui_event

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {
//    val PREFS_FILENAME = "prefs"
    val prefs: SharedPreferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE)
    /* 파일 이름과 EditText를 저장할 Key 값을 만들고 prefs 인스턴스 초기화 */

    var setLanguage: String
        get() = prefs.getString("setLanguage","en").toString()
        set(value) = prefs.edit().putString("setLanguage",value).apply()
    /* get/set 함수 임의 설정. get 실행 시 저장된 값을 반환하며 default 값은 ""
     * set(value) 실행 시 value로 값을 대체한 후 저장 */
}