package com.ryungna.please;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class fragMe extends Fragment {
    Button addMyDiary;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //프래그먼트 상속받는 클래스 oncreate에 있어야함

    }

//    프래그먼트를 포함하고 있는 액티비티의 생성이 완료되
//    었을 때, 즉 액티비티의 onCreate() 메서드가 종료될 때 호출된다
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_frag_me, container, false);

        addMyDiary = (Button)v.findViewById(R.id.addMydiary);

        addMyDiary.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(getActivity(),newMyDiary.class);
            @Override
            public void onClick(View view) {
                startActivity(intent);

            }
        });





        return v;
    }







}
























