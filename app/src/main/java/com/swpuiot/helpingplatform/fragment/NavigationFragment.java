package com.swpuiot.helpingplatform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.view.MainActivity;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class NavigationFragment extends Fragment implements View.OnClickListener {

    private RadioButton navigationFirst;
    private RadioButton navigationSquare;
    private RadioButton navigationChat;
    private RadioButton navigationInformation;
    private RadioButton navigationMy;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigationFirst = (RadioButton) view.findViewById(R.id.btn_navigation_first);
        navigationFirst.setOnClickListener(this);
        navigationSquare = (RadioButton) view.findViewById(R.id.btn_navigation_square);
        navigationSquare.setOnClickListener(this);
        navigationChat = (RadioButton) view.findViewById(R.id.btn_navigation_chat);
        navigationChat.setOnClickListener(this);
        navigationInformation = (RadioButton) view.findViewById(R.id.btn_navigation_information);
        navigationInformation.setOnClickListener(this);
        navigationMy = (RadioButton) view.findViewById(R.id.btn_navigation_my);
        navigationMy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        android.support.v4.app.FragmentManager fragmentManager=getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        FirstFragment firstFragment=new FirstFragment();
        SquareFragment squareFragment=new SquareFragment();
        ChatFragment chatFragment=new ChatFragment();
        InformationFragment informationFragment=new InformationFragment();
        MyFragment myFragment=new MyFragment();

        switch (v.getId()){
            case R.id.btn_navigation_first:
                if(firstFragment.isAdded()){
                    fragmentTransaction.hide(squareFragment).hide(chatFragment).hide(informationFragment)
                            .hide(myFragment).show(firstFragment).commit();
                }else {
                    fragmentTransaction.hide(squareFragment).hide(chatFragment).hide(informationFragment)
                            .hide(myFragment).add(R.id.layout_context,firstFragment).commit();
                }
                ((MainActivity)getActivity()).gettoolbar().setTitle("首页");
                break;
            case R.id.btn_navigation_square:
                if(squareFragment.isAdded()){
                    fragmentTransaction.hide(firstFragment).hide(chatFragment).hide(informationFragment)
                            .hide(myFragment).show(squareFragment).commit();
                }else {
                    fragmentTransaction.hide(firstFragment).hide(chatFragment).hide(informationFragment)
                            .hide(myFragment).add(R.id.layout_context,squareFragment).commit();
                }
                ((MainActivity)getActivity()).gettoolbar().setTitle("广场");
                break;
            case R.id.btn_navigation_chat:
                if(chatFragment.isAdded()){
                    fragmentTransaction.hide(squareFragment).hide(firstFragment).hide(informationFragment)
                            .hide(myFragment).show(chatFragment).commit();
                }else {
                    fragmentTransaction.hide(squareFragment).hide(firstFragment).hide(informationFragment)
                            .hide(myFragment).add(R.id.layout_context,chatFragment).commit();
                }
                ((MainActivity)getActivity()).gettoolbar().setTitle("约");
                break;
            case R.id.btn_navigation_information:
                if(informationFragment.isAdded()){
                    fragmentTransaction.hide(squareFragment).hide(chatFragment).hide(firstFragment)
                            .hide(myFragment).show(informationFragment).commit();
                }else {
                    fragmentTransaction.hide(squareFragment).hide(chatFragment).hide(firstFragment)
                            .hide(myFragment).add(R.id.layout_context,informationFragment).commit();
                }
                ((MainActivity)getActivity()).gettoolbar().setTitle("资料");
                break;
            case R.id.btn_navigation_my:
                if(myFragment.isAdded()){
                    fragmentTransaction.hide(squareFragment).hide(chatFragment).hide(informationFragment)
                            .hide(firstFragment).show(myFragment).commit();
                }else {
                    fragmentTransaction.hide(squareFragment).hide(chatFragment).hide(informationFragment)
                            .hide(firstFragment).add(R.id.layout_context,myFragment).commit();
                }
                ((MainActivity)getActivity()).gettoolbar().setTitle("我的");
                break;
            default:
                break;

        }
    }
}
