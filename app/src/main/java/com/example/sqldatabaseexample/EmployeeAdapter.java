package com.example.sqldatabaseexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sqldatabaseexample.model.Employee;
import com.example.sqldatabaseexample.util.DataBaseHelper;

import java.util.Arrays;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter {
    Context context;
    int layoutRes;
    List<Employee> employeeList;

    //  SQLiteDatabase sqLiteDatabase;

    DataBaseHelper sqLiteDatabase;

    public EmployeeAdapter(@NonNull Context context, int resource, List<Employee> employeeList, DataBaseHelper sqLiteDatabase) {
        super(context, resource, employeeList);
        this.employeeList = employeeList;
        this.sqLiteDatabase = sqLiteDatabase;
        this.context = context;
        this.layoutRes = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layoutRes, null);
        TextView nameTV = v.findViewById(R.id.tv_name);
        TextView salaryTV = v.findViewById(R.id.tv_salary);
        TextView departmentsTV = v.findViewById(R.id.tv_department);
        TextView joiningdateTV = v.findViewById(R.id.tv_joingdate);

        final Employee employee = employeeList.get(position);
        nameTV.setText(employee.getName());
        salaryTV.setText(String.valueOf(employee.getSalary()));
        departmentsTV.setText(employee.getDepartment());
        joiningdateTV.setText(employee.getJoingDate());

        v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee(employee);
            }
        });
        v.findViewById(R.id.btn_Delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee(employee);
            }
        });
        return v;
    }

    private void deleteEmployee(final Employee employee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are u sure to delete?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //      String sql = "DELETE FROM employee WHERE id = ?";
                //    sqLiteDatabase.execSQL(sql, new Integer[]{employee.getId()});
                if (sqLiteDatabase.deleteEmployee(employee.getId()))
                    loadEmployees();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Employee (" + employee.getName() + " ) note deleted", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadEmployees() {
        // String sql = "SELECT * FROM employee";
        //Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        Cursor cursor = sqLiteDatabase.getAllEmployees();

        employeeList.clear();
        if (cursor.moveToFirst()) {
            do {
                //create an employee instace
                employeeList.add(new Employee(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        notifyDataSetChanged();
    }

    private void updateEmployee(final Employee employee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_update_employee, null);
        builder.setView(v);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final EditText nameET = v.findViewById(R.id.etName);
        final EditText salaryET = v.findViewById(R.id.etSal);
        final Spinner departmentET = v.findViewById(R.id.spinnerDepartment);
        nameET.setText(employee.getName());
        salaryET.setText(String.valueOf(employee.getSalary()));

        String[] deptArray = context.getResources().getStringArray(R.array.departments);

        int postion = Arrays.asList(deptArray).indexOf(employee.getDepartment());
        departmentET.setSelection(postion);
        v.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString().trim();
                String salary = salaryET.getText().toString().trim();
                String department = departmentET.getSelectedItem().toString().trim();

                if (name.isEmpty()) {
                    nameET.setError("Fileds cannoy be empty");
                    nameET.requestFocus();
                    return;
                }

                if (salary.isEmpty()) {
                    salaryET.setError("Fileds cannoy be empty");
                    salaryET.requestFocus();
                    return;
                }
                //    String sql = "UPDATE employee SET name = ?, department = ?, salary = ? WHERE id = ?" ;

                //         sqLiteDatabase.execSQL(sql, new String[]{name,department,salary,String.valueOf(employee.getId())});
                if (sqLiteDatabase.updateEmployee(employee.getId(), name, department, Double.valueOf(salary)))
                    loadEmployees();
                alertDialog.dismiss();
            }
        });

    }
}
