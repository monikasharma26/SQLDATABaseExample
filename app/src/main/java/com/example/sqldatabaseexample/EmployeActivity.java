package com.example.sqldatabaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sqldatabaseexample.model.Employee;
import com.example.sqldatabaseexample.util.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EmployeActivity extends AppCompatActivity {

   // SQLiteDatabase sqLiteDatabase;

    DataBaseHelper sqLiteDatabase;

    List<Employee> employeeList;

    ListView employeeListView;
   // private ArrayList<Employee> employeeLi;
    private EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employe);
        employeeListView = findViewById(R.id.lv_employees);
        employeeList = new ArrayList<>();
        //sqLiteDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);

        sqLiteDatabase = new DataBaseHelper(this);

        loadEmployees();
        employeeAdapter = new EmployeeAdapter(this,R.layout.list_layout_employee,employeeList,sqLiteDatabase);

        employeeListView.setAdapter(employeeAdapter);
    }

    private void loadEmployees() {
        //String sql = "SELECT * FROM employee";
       // Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        Cursor cursor = sqLiteDatabase.getAllEmployees();

        if (cursor.moveToFirst()) {
            do{
                //create an employee instace
                employeeList.add(new Employee(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)));
            }while(cursor.moveToNext());
            cursor.close();
        }
       // sqLiteDatabase.execSQL(sql);

        //create and adapter to display the employees

    }
}