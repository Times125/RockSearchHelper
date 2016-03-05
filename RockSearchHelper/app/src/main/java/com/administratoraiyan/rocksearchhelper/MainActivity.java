package com.administratoraiyan.rocksearchhelper;

import android.support.v7.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * created by 李春辉 on 2016/2/18
 */

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 0;
    private String inputTextNamePath;
    static  ArrayList<String> search_result = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.result_list);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,search_result));
        Button button1 = (Button)findViewById(R.id.search_go_btn);
        final EditText editText1 = (EditText)findViewById(R.id.search_src_text);

        requestExternalStoragePermissions();//安卓6.0系统动态获取权限
        SDcardTools.creatFile();

        button1.setOnClickListener(new View.OnClickListener() {//搜索点击事件
            @Override
            public void onClick(View v) {

                if (search_result != null) {
                    search_result.clear();
                }
                SearchFile.searchFile(MainActivity.this,editText1.getText().toString().trim(), Environment.getExternalStorageDirectory() +
                        "/RockSearchHelper");
                ((ArrayAdapter) ((ListView) MainActivity.this.findViewById(R.id.result_list)).getAdapter()).notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String filesChoose = search_result.get(position);//所选文件名称
                inputTextNamePath = Environment.getExternalStorageDirectory() + "/RockSearchHelper/";
                //Toast.makeText(MainActivity.this,inputTextNamePath,Toast.LENGTH_SHORT).show();
                /*****
                 * poi实现打开word文档，基本思路如下
                 * 1.将FTP上的文件下载到本地
                 * 2.调用POI将word文档转换成html文件
                 * 3.利用webview打开html文件
                 */

                Intent intent = new Intent(MainActivity.this,ResultShow.class);
                intent.putExtra("path",inputTextNamePath);//向下一个活动传送文件所在路径
                intent.putExtra("name",filesChoose);//向下一个活动传送岩石名称
                startActivity(intent);
            }
        });
    }


    /*****************************************************************************************
     * 安卓6.0系统获取动态运行权限
     ***************************************************************************************/

    private void requestExternalStoragePermissions() {

        // 如果版本低于Android6.0，不需要申请运行时权限
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
            } else {
                // Permission Denied
                finish();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /********************************************************************************************
     * 菜单选项
     * @param item
     * @return
     *********************************************************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)  {//下载选项
            case R.id.downLoad:
                Toast.makeText(MainActivity.this,"downLoad undone",Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Intent intent = new Intent(MainActivity.this,Helper.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this,"help",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
}
