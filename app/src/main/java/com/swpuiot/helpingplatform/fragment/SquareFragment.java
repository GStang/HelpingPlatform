package com.swpuiot.helpingplatform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.view.MainActivity;
import com.swpuiot.helpingplatform.view.SearchActivity;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class SquareFragment extends Fragment{

    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_square,container,false);

        toolbar= (Toolbar) view.findViewById(R.id.toolbar_square);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_square_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_square_search:
                        Intent intent=new Intent(getActivity(), SearchActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

        return view;
    }

}
