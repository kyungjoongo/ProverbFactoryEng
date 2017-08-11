package com.kyungjoon.thegenius.proverbfactoryeng;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kyungjoon on 2017-08-10.
 */

public class PreferenceUtils {

    public PreferenceUtils(){

    }

    // 값 불러오기
    public static String getPreferences( Context context){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        String result = pref.getString("curPage", "");

        return result;
    }

    // 값 저장하기
    public static void savePreferences(Context context,int page){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("curPage", Integer.toString(page) );
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    /*public static void removePreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("hi");
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    public static void removeAllPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }*/
}
