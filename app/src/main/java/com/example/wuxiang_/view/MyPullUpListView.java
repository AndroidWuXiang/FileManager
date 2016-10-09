package com.example.wuxiang_.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wuxiang_.myapplication.R;

/**
 * Created by wuxiang on 16-10-9.
 */

 public class MyPullUpListView extends ListView implements AbsListView.OnScrollListener{

    private View footerView = null;
    private Context context;
    /** 上拉刷新的ListView的回调监听 */
    private MyPullUpListViewCallBack myPullUpListViewCallBack;
    /** 记录第一行Item的数值 */
    private int firstVisibleItem;

    public MyPullUpListView(Context context) {
        super(context);
        this.context = context;
        initListView();
    }

    public MyPullUpListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initListView();
    }

    //初始化ListView
    public void initListView(){
        // 为ListView设置滑动监听
        setOnScrollListener(this);
        // 去掉底部分割线
        setFooterDividersEnabled(false);
    }



    public void initBottomView(){
        footerView = LayoutInflater.from(context).inflate(R.layout.listview_bottom,null);
        TextView t = (TextView) footerView.findViewById(R.id.lv_tv_1);
        Log.e("wuxiang",t.getText()+"@@@@@@@");
        addFooterView(footerView);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //当滑动到底部时
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && firstVisibleItem != 0) {
            myPullUpListViewCallBack.scrollBottomState();
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        this.firstVisibleItem = firstVisibleItem;

        if (footerView != null) {
            //判断可视Item是否能在当前页面完全显示
            if (visibleItemCount < totalItemCount) {
                // removeFooterView(footerView);
                footerView.setVisibility(View.VISIBLE);//隐藏底部布局
            } else {
                // addFooterView(footerView);
                footerView.setVisibility(View.GONE);//显示底部布局
            }
        }
    }
    public void setMyPullUpListViewCallBack(
            MyPullUpListViewCallBack myPullUpListViewCallBack) {
        this.myPullUpListViewCallBack = myPullUpListViewCallBack;
    }
    public interface MyPullUpListViewCallBack {

        void scrollBottomState();
    }
}