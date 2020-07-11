package com.example.sqldatabaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //
    public static final String DATABASE_NAME = "myData";
    SQLiteDatabase sqLiteDatabase;
    EditText etanme,EtSalrt;
    Spinner spDep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etanme = findViewById(R.id.etName);
        EtSalrt = findViewById(R.id.etSal);
        spDep = findViewById(R.id.spinnerDepartment);

        findViewById(R.id.btnAdd).setOnClickListener((View.OnClickListener) this);//(View.OnClickListener) this);
        findViewById(R.id.ViewEmp).setOnClickListener((View.OnClickListener) this);

        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
        createTable();
    }

    private void createTable() {
        String sql ="CREATE TABLE IF NOT EXISTS employee ( "+
                "id INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR(20) NOT NULL,"+
                "department VARCHAR(20) NOT NULL, "+
                "joining_date DATETIME NOT NULL, " +
                "Salary DOUBLE NOT NULL);";
        sqLiteDatabase.execSQL(sql);

    }

   public void onClick(View v)
   {
       switch ((v.getId())){
           case R.id.btnAdd:
               addEmployee();
               break;
           case R.id.ViewEmp:
               startActivity(new Intent(this,EmployeActivity.class));
               break;
       }

   }
   private void addEmployee()
   {
       String name = etanme.getText().toString().trim();
       String Salary = EtSalrt.getText().toString().trim();
       String department = spDep.getSelectedItem().toString();

       Calendar cal = Calendar.getInstance();
       SimpleDateFormat sdf = new SimpleDateFormat("YYY/mm/dd");
       String joining_date = sdf.format(cal.getTime());

       if(!name.isEmpty())
       {
           etanme.setError("Fileds cannoy be empty");
           etanme.requestFocus();
           return;
       }

       if(!Salary.isEmpty())
       {
           EtSalrt.setError("Fileds cannoy be empty");
           EtSalrt.requestFocus();
           return;
       }
       String sql = "INSERT INTO employee (name, department, Salary,joining_date)"+
               "VAlUES(?, ?, ?, ?, ?)";
       sqLiteDatabase.execSQL(sql,new String[]{name,department,joining_date,Salary});

   }
}