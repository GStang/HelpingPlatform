package com.swpuiot.helpingplatform.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;

import java.io.File;
import java.util.ArrayList;

public class ShareInformationActivity extends AppCompatActivity {
    private Spinner spinner;
    private Button choseFile;
    private TextView showFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_information);

        choseFile= (Button) findViewById(R.id.btn_share_file);
        showFileName= (TextView) findViewById(R.id.tv_share_file_name);

        choseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                    try {
                        startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), 1);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(ShareInformationActivity.this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                    }
            }


        });

        spinner= (Spinner) findViewById(R.id.spi_kinds);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner= (Spinner) parent;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String path = FileUtils.getPath(this, uri);
                    Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
                    System.out.println(path);
                    showFileName.setText(path);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static class FileUtils {
        public static String getPath(Context context, Uri uri) {

            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {"_data"};
                Cursor cursor = null;

                try {
                    cursor = context.getContentResolver().query(uri, projection, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow("_data");
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index);
                    }
                } catch (Exception e) {
                    // Eat it
                }
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        }
    }
}
