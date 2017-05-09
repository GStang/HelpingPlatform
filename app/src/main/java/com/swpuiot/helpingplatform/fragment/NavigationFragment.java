package com.swpuiot.helpingplatform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.view.MainActivity;

import at.markushi.ui.CircleButton;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class NavigationFragment extends Fragment implements View.OnClickListener {

    private RadioButton navigationFirst;
    private RadioButton navigationSquare;
    private ImageButton navigationChat;
    private RadioButton navigationInformation;
    private RadioButton navigationMy;
    private FragmentManager manager;
    private FirstFragment firstFragment;
    private SquareFragment squareFragment;
    private ChatFragment chatFragment;
    private InformationFragment informationFragment;
    private MyFragment myFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test", "Oncreate");
        manager = getActivity().getSupportFragmentManager();
        firstFragment = new FirstFragment();
        chatFragment = new ChatFragment();
        squareFragment = new SquareFragment();
        informationFragment = new InformationFragment();
        myFragment = new MyFragment();
        manager.beginTransaction().add(R.id.layout_context, firstFragment).add(R.id.layout_context, chatFragment)
                .add(R.id.layout_context, squareFragment)
                .add(R.id.layout_context, informationFragment)
                .add(R.id.layout_context, myFragment).commit();
        manager.beginTransaction().hide(squareFragment)
                .hide(chatFragment)
                .hide(informationFragment)
                .hide(myFragment).commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
//        Log.e("Test", firstFragment.toString());
//        manager.beginTransaction().add(R.id.layout_context, firstFragment).commit();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigationFirst = (RadioButton) view.findViewById(R.id.btn_navigation_first);
        navigationFirst.setOnClickListener(this);
        navigationSquare = (RadioButton) view.findViewById(R.id.btn_navigation_square);
        navigationSquare.setOnClickListener(this);
        navigationChat = (ImageButton) view.findViewById(R.id.btn_navigation_chat);
        navigationChat.setOnClickListener(this);
        navigationInformation = (RadioButton) view.findViewById(R.id.btn_navigation_information);
        navigationInformation.setOnClickListener(this);
        navigationMy = (RadioButton) view.findViewById(R.id.btn_navigation_my);
        navigationMy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_navigation_first:
                manager.beginTransaction().hide(squareFragment)
                        .hide(chatFragment)
                        .hide(informationFragment)
                        .hide(myFragment)
                        .show(firstFragment).commit();
                Log.e("Test", manager.getFragments().size() + "");
                System.out.println(manager.getFragments());
                navigationFirst.setChecked(true);
                break;
            case R.id.btn_navigation_square:
                manager.beginTransaction().hide(firstFragment)
                        .hide(chatFragment)
                        .hide(informationFragment)
                        .hide(myFragment)
                        .show(squareFragment).commit();
                System.out.println(manager.getFragments());
                Log.e("Test", manager.getFragments().size() + "");
                navigationSquare.setChecked(true);
                break;
            case R.id.btn_navigation_chat:
                manager.beginTransaction().hide(squareFragment)
                        .hide(firstFragment)
                        .hide(informationFragment)
                        .hide(myFragment)
                        .show(chatFragment).commit();
                System.out.println(manager.getFragments());
                Log.e("Test", manager.getFragments().size() + "");
                navigationFirst.setChecked(false);
                navigationSquare.setChecked(false);
                navigationInformation.setChecked(false);
                navigationMy.setChecked(false);
                break;
            case R.id.btn_navigation_information:
                manager.beginTransaction().hide(squareFragment)
                        .hide(chatFragment)
                        .hide(firstFragment)
                        .hide(myFragment)
                        .show(informationFragment).commit();
                System.out.println(manager.getFragments());
                Log.e("Test", manager.getFragments().size() + "");
                navigationInformation.setChecked(true);
                break;
            case R.id.btn_navigation_my:
                manager.beginTransaction().hide(squareFragment)
                        .hide(chatFragment)
                        .hide(informationFragment)
                        .hide(firstFragment)
                        .show(myFragment).commit();
                System.out.println(manager.getFragments());
                Log.e("Test", manager.getFragments().size() + "");
                navigationMy.setChecked(true);
                break;
            default:
                break;

        }
    }
}
