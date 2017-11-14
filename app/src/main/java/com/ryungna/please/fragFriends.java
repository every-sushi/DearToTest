package com.ryungna.please;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


public class fragFriends extends Fragment {

    private ListView listView;
    List fileList = new ArrayList<>();
    ArrayAdapter adapter;
    static boolean calledAlready = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //프래그먼트 상속받는 클래스 oncreate에 있어야함

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_frag_friends, container, false);

        UserModel u=new UserModel();
        final String id=u.getuserId();


        final EditText search=(EditText)v.findViewById(R.id.email_search);

        if (!calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true); // 다른 인스턴스보다 먼저 실행되어야 한다.
            calledAlready = true;
        }

        listView= (ListView)v.findViewById(R.id.friends_listview);

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_listitem, fileList); /*!!!!!!!!!!!!!!!!!!!!*/
        listView.setAdapter(adapter);

        FirebaseDatabase putfriends=FirebaseDatabase.getInstance();
        final DatabaseReference fRef=putfriends.getReference("user");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference("user");

        final DatabaseReference databaseRef2 = database.getReference("user");

        databaseRef2.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                fileList.clear();
                Iterator<DataSnapshot> child = dataSnapshot.child(id).child("Friends").getChildren().iterator();
                //users의 모든 자식들의 key값과 value 값들을 iterator로 참조합니다.

                while(child.hasNext())
                {
                    String str=child.next().getKey();
                    String str1=dataSnapshot.child(id).child("Friends").child(str).getValue(String.class);
                    fileList.add(str1);

                }

                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG: ", "Failed to read value", databaseError.toException());
            }
        });



        Button button=(Button)v.findViewById(R.id.search_btn);
        // Read from the database

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                databaseRef.addValueEventListener(new ValueEventListener() {

                    int flag=0;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String a=search.getText().toString();
                        Iterator<DataSnapshot> child = dataSnapshot.child(id).child("Friends").getChildren().iterator();
                        String str=null;
                        String str1=null;
                        String str2=null;
                        // 클래스 모델이 필요?
                        for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                            //MyFiles filename = (MyFiles) fileSnapshot.getValue(MyFiles.class);
                            str = fileSnapshot.child("email").getValue(String.class);
                            str2=dataSnapshot.child(id).child("email").getValue(String.class);


                            while(child.hasNext()) {
                                String str3 = child.next().getKey();
                                String str4 = dataSnapshot.child(id).child("Friends").child(str3).getValue(String.class);
                                if(str4.equals(a)){
                                    flag=2;
                                    break;
                                }
                            }

                            if(a.equals(str2)==false&&flag!=2){//자기랑 같은 값인지 확인
                                if(a.equals(str)&&flag!=1){//가입되어있는 이메일인지 확인
                                    Calendar c=Calendar.getInstance();
                                    long now = c.getTimeInMillis();
                                    String friends_cnt="friend"+now;
                                    fRef.child(id).child("Friends").child(friends_cnt).setValue(str);
                                    flag=1;
                                    break;
                                }
                            }
                            if(flag==1) break;
                        }
                        if(flag==0){
                            Toast.makeText(getActivity(), "해당하는 이메일이 없습니다.", Toast.LENGTH_LONG).show();
                        }
                        else if(flag==2){
                            Toast.makeText(getActivity(), "이미 추가되었습니다.", Toast.LENGTH_LONG).show();
                        }
                        //flag=0;
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("TAG: ", "Failed to read value", databaseError.toException());
                    }
                });
            }

        });





        return v;

    }


}
