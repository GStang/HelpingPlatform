package com.swpuiot.helpingplatform.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.FirstRecyclerAdapter;
import com.swpuiot.helpingplatform.bean.First;
import com.swpuiot.helpingplatform.bean.FirstBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.utils.BannerLoader;
import com.swpuiot.helpingplatform.utils.CameraUtils;
import com.swpuiot.helpingplatform.view.BannerActivity;
import com.swpuiot.helpingplatform.view.InfImplActivity;
import com.swpuiot.helpingplatform.view.SearchActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

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
    private Button addData;
    private List<FirstBean>datas;
    private User user;
    public static final String InFlmp = "InFlmp";
    private Boolean refreshing=false;



    private List<String>bannerUri=Arrays.asList(
            "https://kuaibao.qq.com/s/20170318A03LZN00\n",
            "https://kuaibao.qq.com/s/20170107I01ACJ00\n",
            "https://kuaibao.qq.com/s/20170319A00U5700\n",
            "https://kuaibao.qq.com/s/20170317C06DLA00\n"
    );

    public void getDatas(){
        BmobQuery<FirstBean>query=new BmobQuery<>();
        query.include("author");
        query.findObjects(new FindListener<FirstBean>() {
            @Override
            public void done(List<FirstBean> list, BmobException e) {

                if (e==null){
                    datas.clear();
                    firstRecyclerAdapter.notifyItemRangeRemoved(0, firstRecyclerAdapter.getItemCount());
                    swipeRefreshLayout.setRefreshing(false);
                    refreshing=false;
                    Toast.makeText(getContext(),"查询成功，共有"+list.size()+"条数据",Toast.LENGTH_SHORT).show();
                    datas.addAll(list);
                    firstRecyclerAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(),"查询失败",Toast.LENGTH_SHORT).show();
                    refreshing = false;
                }
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_first, container, false);
        datas=new ArrayList<>();
        user=BmobUser.getCurrentUser(User.class);
        bannerImage= Arrays.asList(
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3,
                R.drawable.banner4
        );
        bannerTitle=Arrays.asList(
                "每日一笑",
                "思维问答",
                "时事政要",
                "前沿科技"
        );


//        first.initData();

        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_first);
        firstRecyclerAdapter=new FirstRecyclerAdapter(getActivity(),datas);
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
                intent.putExtra(InFlmp, datas.get(position));
                startActivity(intent);
            }


            @Override
            public void onItemLongClick(View view, int position) {
                mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 0.7f;
                getActivity().getWindow().setAttributes(params);
            }
        });

        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_first);
        refreshing = true;
        swipeRefreshLayout.setOnRefreshListener(this);
        getDatas();
        swipeRefreshLayout.setRefreshing(true);

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
                Intent intent = new Intent(getActivity(), BannerActivity.class);
                intent.putExtra("bannerUri", bannerUri.get(position - 1));
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
    public void onResume() {
        super.onResume();
//        swipeRefreshLayout.setRefreshing(true);
//        refreshing=true;
//        getDatas();
    }
    @Override
    public void onRefresh() {
        if (refreshing){
            return;
        }
        else {
            refreshing=true;
            getDatas();
        }
    }






}
