package com.example.wuxiang_.fragment;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuxiang_.com.example.wuxiang_.daobean.ImageInfo;
import com.example.wuxiang_.com.example.wuxiang_.daobean.VideoInfo;
import com.example.wuxiang_.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wuxiang_ on 2016/10/6.
 */

public class VideoContentFragment extends Fragment {
    private View currentView;
    private ListView listView;
    public static ArrayList<VideoInfo> sysVideoList;// 图片信息集合
    private Cursor cursor;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            new VideoAsynck().execute();
        }
    };
    String[] mediaColumns = { MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DISPLAY_NAME };
    String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
          MediaStore.Video.Thumbnails.VIDEO_ID };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_image,null);
        listView = (ListView)currentView.findViewById(R.id.lv_image);
        sysVideoList = new ArrayList<VideoInfo>();
        cursor = getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    //sleep(300);
                    handler.sendEmptyMessage(0);

                }catch (Exception e){

                }

            }
        }.start();
        return currentView;
    }
    class VideoBaseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return sysVideoList.size();
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
            ViewHolder videwHolder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_single_item,null);
                videwHolder = new ViewHolder();
                videwHolder.textView = (TextView)convertView.findViewById(R.id.image_name);
                videwHolder.imageView = (ImageView)convertView.findViewById(R.id.image_content);
                convertView.setTag(videwHolder);
            }
            else{
                videwHolder = (ViewHolder) convertView.getTag();
            }
            videwHolder.imageView.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(sysVideoList.get(position).getThumbPath()),200,200));
            videwHolder.textView.setText(sysVideoList.get(position).getDisplayName());
            return convertView;
        }
    }
    public class ViewHolder{
        TextView textView;
        ImageView imageView;
    }

    class VideoAsynck extends AsyncTask<Void,Void,ArrayList<VideoInfo>>{
        @Override
        protected void onPostExecute(ArrayList<VideoInfo> videoInfos) {
            super.onPostExecute(videoInfos);
            if(videoInfos == null){
                Toast.makeText(getContext(), "没有找到图片文件",Toast.LENGTH_SHORT).show();
            }
            sysVideoList.addAll(videoInfos);
            listView.setAdapter(new VideoBaseAdapter());
        }

        @Override
        protected ArrayList<VideoInfo> doInBackground(Void... params) {
            /*cursor = getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    mediaColumns, null, null, null);*/
            ArrayList<VideoInfo> results = new ArrayList<VideoInfo>();
            if(cursor==null){
                Toast.makeText(getContext(), "没有找到可播放视频文件", Toast.LENGTH_SHORT).show();
                return null;
            }
            if(cursor.moveToFirst()){
                do{
                    VideoInfo info = new VideoInfo();
                    int id = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Video.Media._ID));
                    Cursor thumbCursor =  getContext().getContentResolver().query(
                            MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                            thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                                    + "=" + id, null, null);
                    if(thumbCursor!=null) {
                        if (thumbCursor.moveToFirst()) {
                            info.setThumbPath(thumbCursor.getString(thumbCursor
                                    .getColumnIndex(MediaStore.Images.Thumbnails.DATA)));
                        }
                    }
                    info.setDisplayName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
                    info.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
                    info.setMimeType(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)));
                    results.add(info);
                }while (cursor.moveToNext());
            }
            return results;
        }
    }
}
