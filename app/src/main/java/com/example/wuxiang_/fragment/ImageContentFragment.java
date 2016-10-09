package com.example.wuxiang_.fragment;



import android.database.Cursor;

import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import android.os.AsyncTask;
import android.provider.MediaStore;

import android.support.v4.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;


import com.example.wuxiang_.com.example.wuxiang_.daobean.ImageInfo;

import com.example.wuxiang_.myapplication.R;
import com.example.wuxiang_.view.MyPullUpListView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by wuxiang_ on 2016/10/5.
 */


public class ImageContentFragment extends Fragment {

    private View currentView;
    private MyPullUpListView listView;
    //private ArrayList<HashMap<String, Object>> listItems;
    public static ArrayList<ImageInfo> sysImageList;// 图片信息集合
    private Cursor cursor;
    private int count = 6;
    int totalCount = 0;
    public MyBaseAdapter baseAdapter;
    // MediaStore.Images.Thumbnails.DATA:图片缩略图的文件路径
    //String[] thumbColumns = { MediaStore.Images.Thumbnails.DATA,
     //       MediaStore.Images.Thumbnails.IMAGE_ID };

    // MediaStore.Images.Media.DATA：图片文件路径；
    // MediaStore.Images.Media.DISPLAY_NAME : 图片文件名，如 testImages.jpg
    // MediaStore.Images.Media.TITLE: 图片标题 : testImages
    String[] mediaColumns = { MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA, MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.DISPLAY_NAME };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_image,
                container,false);
        listView = (MyPullUpListView)currentView.findViewById(R.id.lv_image);
        listView.initBottomView();
        listView.setMyPullUpListViewCallBack(new MyPullUpListView.MyPullUpListViewCallBack() {
            @Override
            public void scrollBottomState() {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try{
                            sleep(200);
                            new LoadImage().execute();
                        }catch (Exception e){

                        }

                    }
                }.start();
            }
        });
        sysImageList = new ArrayList<ImageInfo>();
        //new LoadImage().execute();
        baseAdapter = new MyBaseAdapter();
       /* cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    mediaColumns, null, null, null);*/

 /*        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        //加载更多功能的代码
                        //sysImageList.clear();
                       *//**//* new LoadImage().execute();*//**//*
                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                try{
                                    sleep(200);
                                    new LoadImage().execute();
                                }catch (Exception e){

                                }

                            }
                        }.start();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });*/
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    sleep(300);
                    new LoadImage().execute();
                }catch (Exception e){

                }

            }
        }.start();

        return currentView;
    }

    public ImageContentFragment() {
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    class LoadImage extends AsyncTask<Void,Void,ArrayList<ImageInfo>>{
        @Override
        protected ArrayList<ImageInfo> doInBackground(Void... params) {
            ArrayList<ImageInfo> results = new  ArrayList<ImageInfo>();
            cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    mediaColumns, null, null, "_id asc LIMIT  "+0+","+count);

            if(cursor==null){
                //Toast.makeText(ImageContentFragment.this, "没有找到可播放视频文件", 1).show();
                return null;
            }


            if (cursor.moveToFirst()) {
                do {
                    ImageInfo info = new ImageInfo();
                    /*int id = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Images.Media._ID));
                    Cursor thumbCursor =  getContext().getContentResolver().query(
                            MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                            thumbColumns, MediaStore.Images.Thumbnails.IMAGE_ID
                                    + "=" + id, null, null);
                    if(thumbCursor!=null) {
                        if (thumbCursor.moveToFirst()) {
                            info.setThumbPath(thumbCursor.getString(thumbCursor
                                    .getColumnIndex(MediaStore.Images.Thumbnails.DATA)));
                        }
                    }*/
                    info.setPath(cursor.getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));

                    info.setTitle(cursor.getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.TITLE)));

                    info.setDisplayName(cursor.getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));

                    info.setMimeType(cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)));

                    results.add(info);
                    totalCount++;
                } while (cursor.moveToNext());
            }
            count+=6;
            cursor.close();
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<ImageInfo> results) {
            super.onPostExecute(results);
            if(results == null){
                Toast.makeText(getContext(), "没有找到图片文件",Toast.LENGTH_SHORT).show();
            }
            else{
               /* listItems = new ArrayList<HashMap<String, Object>>();
                for(ImageInfo imageInfo :sysImageList){
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("name", imageInfo.getDisplayName());
                    map.put("image", ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imageInfo.getPath()),200,200));
                    Log.e("path", imageInfo.getPath());
                    listItems.add(map);
                }*/
               /* listItemAdapter = new SimpleAdapter(getContext(),listItems,   // listItems数据源
                        R.layout.image_single_item, //ListItem的XML布局实现
                        new String[] {"name","image" },     //动态数组与ImageItem对应的子项
                        new int[ ] {R.id.image_name,R.id.image_content}      //list_item.xml布局文件里面的一个ImageView的ID,一个TextView 的ID
                );
                //ViewBinder该类可以帮助SimpleAdapter加载图片(如：Bitmap,Drawable)
                listItemAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data, String textRepresentation) {
                        if(view instanceof ImageView && data instanceof Bitmap){
                            ImageView i = (ImageView)view;
                            i.setImageBitmap((Bitmap) data);
                            return true;
                        }
                        return false;
                    }
                });*/
                sysImageList.removeAll(sysImageList);
                //sysImageList = results;
                sysImageList.addAll(results);
                //listView.setAdapter(baseAdapter);
                listView.setAdapter(baseAdapter);
                listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);

                baseAdapter.notifyDataSetChanged();

                listView.setSelection(count-6);
                Toast.makeText(getContext(), "图片数目："+count,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }



    /*@Override
    public void onStart() {
        super.onStart();
        new LoadImage().execute();
    }*/

    @Override
    public void onResume() {
        super.onResume();
    }
    class MyBaseAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return sysImageList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                convertView =  LayoutInflater.from(getContext()).inflate(R.layout.image_single_item,null);
                holder = new ViewHolder();
                holder.imageView = (ImageView)convertView.findViewById(R.id.image_content);
                holder.textView = (TextView)convertView.findViewById(R.id.image_name);
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.imageView.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(sysImageList.get(position).getPath()),200,200));
            holder.textView.setText(sysImageList.get(position).getDisplayName());
            return convertView;
        }
    }
    public class ViewHolder {
        TextView textView;
        ImageView imageView;

    }
    //下拉刷新
    class Refresh extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            sysImageList.clear();
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try{
                        sleep(200);
                        new LoadImage().execute();
                    }catch (Exception e){

                    }

                }
            }.start();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        count=6;
    }
}
