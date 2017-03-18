package com.swpuiot.helpingplatform.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.FirstRecyclerAdapter;
import com.swpuiot.helpingplatform.bean.First;
import com.swpuiot.helpingplatform.utils.BannerLoader;
import com.swpuiot.helpingplatform.view.InfImplActivity;
import com.swpuiot.helpingplatform.view.SearchActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class FirstFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private Banner banner;
    private List<Integer>bannerImage;
    private List<String>bannerTitle;
    private RecyclerView recyclerView;
    private FirstRecyclerAdapter firstRecyclerAdapter;
    private static final int REFRESH_COMPLETE=0x110;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private RecyclerViewHeader recyclerViewHeader;
    private PopupWindow mPopupWindow;
    private View popopWindow;
    private Button toastCancel;
    private First first;


    private List<String>a=Arrays.asList("你好");
    private List<String>b=Arrays.asList("好呀好呀");
    private List<Integer>c=Arrays.asList(R.drawable.car4);
    private List<String>d=Arrays.asList("3.18 17:30");
    private List<String>e=Arrays.asList("唐骚猪");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_first, container, false);
        first = new First();
        bannerImage= Arrays.asList(
                R.drawable.car3,
                R.drawable.car4,
                R.drawable.car7,
                R.drawable.car8,
                R.drawable.car9
        );
        bannerTitle=Arrays.asList(
                "成都街头惊现镀金兰博基尼",
                "布加迪是否如传说中的那么厉害",
                "超跑地狱崭露头角，震撼来袭",
                "美国地产大亨的大院与超跑",
                "第四届翼灵超跑大赛拉开序幕"
        );


        first.initData();

        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_first);
        firstRecyclerAdapter=new FirstRecyclerAdapter(getActivity(),first.getRecyclerTitle(),first.getRecyclerWord()
                ,first.getRecyclerImage(),first.getRecyclerTime(),first.getRecyclerName());
        recyclerView.setAdapter(firstRecyclerAdapter);
        recyclerViewHeader= (RecyclerViewHeader) view.findViewById(R.id.header_first);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()
                ,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHeader.attachTo(recyclerView, true);

        firstRecyclerAdapter.setOnItemClickListener(new FirstRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), InfImplActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }


            ;

            @Override
            public void onItemLongClick(View view, int position) {
//                Intent intent = new Intent();
//                intent.setClassName(getActivity(), "com.swpuiot.helpingplatform.view.ToastActivity");
//                getActivity().startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.in_from_bottom, 0);

                mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 0.7f;
                getActivity().getWindow().setAttributes(params);
            }
        });

        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_first);
        swipeRefreshLayout.setOnRefreshListener(this);

        banner= (Banner) view.findViewById(R.id.banner_first);
        banner.setImageLoader(new BannerLoader())
                .setImages(bannerImage)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setBannerTitles(bannerTitle)
                .isAutoPlay(true)
                .start();

        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), InfImplActivity.class);
                intent.putExtra("bannerTitle", bannerTitle.get(position - 1));
                startActivity(intent);
            }
        });


        toolbar= (Toolbar) view.findViewById(R.id.toolbar_first);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_first_toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_first_search:
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popopWindow=getActivity().getLayoutInflater().inflate(R.layout.activity_toast,null);

        mPopupWindow=new PopupWindow(popopWindow, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.setAnimationStyle(R.style.Animation);


        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
        mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        toastCancel= (Button) mPopupWindow.getContentView().findViewById(R.id.btn_toast_cancel);
        toastCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });


        return view;
    }


    @Override
    public void onRefresh() {

        Toast.makeText(getActivity(), "访问", Toast.LENGTH_SHORT).show();
        firstRecyclerAdapter.upData(a, b, c,d,e);
        firstRecyclerAdapter.notifyDataSetChanged();
        firstRecyclerAdapter.addData(0);
        swipeRefreshLayout.setRefreshing(false);
    }






}
