package com.example.wuxiang_.myapplication;




import android.Manifest;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;



import com.example.wuxiang_.fragment.ImageContentFragment;
import com.example.wuxiang_.fragment.VideoContentFragment;

public class MainActivity extends AppCompatActivity  {
    private ListView listView;
    private String[] strs = {"图片","视频","小说"};
    private FragmentManager fragmentManager;
    private DrawerLayout dw;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("文件一览");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,strs);
        ImageContentFragment imageContentFragment = new ImageContentFragment();
        imageContentFragment.onRequestPermissionsResult(100, new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},null);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, imageContentFragment).commit();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setItem(position);
            }
        });

    }
    public void init(){
        listView = (ListView)this.findViewById(R.id.lv_1);
        dw = (DrawerLayout)this.findViewById(R.id.drawer_layout);
        mDrawerLayout =(DrawerLayout)this.findViewById(R.id.drawer_layout);
    }
    public void setItem(int position){

       switch(position){
           case 0:

                ImageContentFragment imageContentFragment = new ImageContentFragment();
                imageContentFragment.onRequestPermissionsResult(100, new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},null);

                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, imageContentFragment).commit();

                break;
            case 1:

                VideoContentFragment videoContentFragment = new VideoContentFragment();
                videoContentFragment.onRequestPermissionsResult(100, new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},null);

                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, videoContentFragment).commit();

                break;
            case 2:

                VideoContentFragment videoContentFragment2 = new VideoContentFragment();

                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, videoContentFragment2).commit();

                break;
            default:

                VideoContentFragment videoContentFragment3 = new VideoContentFragment();

                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, videoContentFragment3).commit();
                break;

        }

        //选择结束后，关闭listview
        listView.setItemChecked(position,true);
        dw.closeDrawer(listView);
    }


}
