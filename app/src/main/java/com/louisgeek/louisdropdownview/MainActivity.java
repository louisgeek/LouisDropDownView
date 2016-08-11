package com.louisgeek.louisdropdownview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.louisgeek.dropdownviewlib.ClassfiySeletView;
import com.louisgeek.dropdownviewlib.MultiSelectView;
import com.louisgeek.dropdownviewlib.javabean.MultiSelectHasChildBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    List<Map<String, Object>> mMultiSelectMapListOut;
  List<MultiSelectHasChildBean> mMultiSelectHasChildBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
       // initHasChildData();

        MultiSelectView multiSelectView= (MultiSelectView) findViewById(R.id.id_msv);
        multiSelectView.setupMultiSelectMapListOutter(mMultiSelectMapListOut);

       /* final MultiSelectView_HasChild id_msv_gac= (MultiSelectView_HasChild) findViewById(R.id.id_msv_gac);
        Log.d("tag", "louis:1 onCreate: mMultiSelectHasChildBeanList:"+mMultiSelectHasChildBeanList);*/
       //### id_msv_gac.setupMultiSelectHasChildBeanListOutter(mMultiSelectHasChildBeanList);

      /*  final Button id_btn= (Button) findViewById(R.id.id_btn);
        id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_btn.setText(id_msv_gac.getSelectedKey());
            }
        });*/
        ClassfiySeletView id_csv = (ClassfiySeletView) findViewById(R.id.id_csv);
        id_csv.setOnContentViewChangeListener(new ClassfiySeletView.OnContentViewChangeListener() {
            @Override
            public void onContentViewShow() {

            }

            @Override
            public void onContentViewDismiss() {

            }

            @Override
            public void onContentViewItemSeleted(String key, String name) {

            }
        });
    }

    private void initData() {
        mMultiSelectMapListOut=new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            Map<String,Object> map=new HashMap<>();
            map.put("name", "name" + i);
            map.put("key", "key" + i);
            map.put("checked", false);
            mMultiSelectMapListOut.add(map);
        }
    }


}
