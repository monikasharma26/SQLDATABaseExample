package com.example.sqldatabaseexample.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "employe_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "employee";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DEPT = "department";
    private static final String COLUMN_JOIN_DATE = "joining_date";
    private static final String COLUMN_SALARY = "salary";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " VARCHAR(20) NOT NULL, " +
                COLUMN_DEPT + "  VARCHAR(20) NOT NULL, " +
                COLUMN_JOIN_DATE + " DATETIME NOT NULL, " +
                COLUMN_SALARY + " DOUBLE NOT NULL);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop Table and then create it
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }
    //Insert

    /**
     * add employee - insert employee into employee table
     *
     * @param name
     * @param department
     * @param joiningDate
     * @param salary
     * @return boolean value - true (inserted) false (not inserted)
     */
    public boolean addEmployee(String name, String department, String joiningDate, double salary) {
        //we need a writeable instace of SQLite database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //We nedd to define values in order to insert data into our database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DEPT, department);
        contentValues.put(COLUMN_JOIN_DATE, joiningDate);
        contentValues.put(COLUMN_SALARY, String.valueOf(salary));

        // The insert method associated to sqlite database insatce return -1 if nothing inserted
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) > 0;

    }

    /**
     * Query database - Select All the Employees
     *
     * @return cursor
     */
    public Cursor getAllEmployees() {
        //we Need a readAble insatce of a database
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);

    }

    /**
     * Update employee - UPADTE employee in database
     *
     * @param id
     * @param name
     * @param department
     * @param salary
     * @return boolean value - true (updated) false (not updated)
     */
    public boolean updateEmployee(int id, String name, String department, double salary) {
        //we need a writeable instace of SQLite database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //We nedd to define values in order to insert data into our database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DEPT, department);
        contentValues.put(COLUMN_SALARY, String.valueOf(salary));
        //update Method Associated to SQLiteDataBASE instace returns number of rows EFFECTED
        return sqLiteDatabase.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;

    }

    /**
     * Delete Employe --- From DATABASE
     *
     * @param id
     */
    public boolean deleteEmployee(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //The delete MEthod associated to the SQLiteDATABASE instance
        return sqLiteDatabase.delete(TABLE_NAME,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }
}
