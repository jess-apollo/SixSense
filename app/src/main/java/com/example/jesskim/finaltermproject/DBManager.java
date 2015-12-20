package com.example.jesskim.finaltermproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JessKim on 2015-12-20.
 * DataBase 관리하는  DBManager 클래스 생성
 *
 * DBMananger Table 생성자
 * public void onCreate(SQLiteDatabase db)
 *
 * _query 대로 DB에 저장
 * public void insert(String _query)
 *
 * DB에 저장된 데이터 모두 출력
 * public String PrintData()
 *
 * DB에 저장된 데이터 중 _kind에 해당하는 데이터만 출력
 * public String PrintKind(String _kind)
 *
 */

public class DBManager extends SQLiteOpenHelper {

    // 생성자
    public DBManager(Context context, String title, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, title, factory, version);
    }

    //  DBManager Table 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MEMO_LIST( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, today TEXT, kind TEXT, " +
                "memo TEXT, lng DOUBLE, lat DOUBLE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldBersion, int newVersion) {
    }

    // _query 대로 DB에 저장
    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    // DB에 저장된 데이터 모두 출력
    public String PrintData() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from MEMO_LIST", null);
        while(cursor.moveToNext()) {
            str = str
                    + cursor.getString(1)
                    + " "
                    + cursor.getString(2)
                    + " <"
                    + cursor.getString(3)
                    + "> *"
                    + cursor.getString(4)
                    + "\n"
                    + "lng:"
                    + cursor.getDouble(5)
                    + " lat:"
                    + cursor.getDouble(6)
                    + "\n\n";
        }

        return str;
    }

    // DB에 저장된 데이터 중 _kind에 해당하는 데이터만 출력
    public String PrintKind(String _kind) {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from MEMO_LIST where kind="+ "'" + _kind + "';", null);
        while(cursor.moveToNext()) {
            str = str
                    + cursor.getString(1)
                    + " "
                    + cursor.getString(2)
                    + " <"
                    + cursor.getString(3)
                    + "> *"
                    + cursor.getString(4)
                    + "\n"
                    + "lng:"
                    + cursor.getDouble(5)
                    + " lat:"
                    + cursor.getDouble(6)
                    + "\n\n";
        }

        return str;
    }
}
