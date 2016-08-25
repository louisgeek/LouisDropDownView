package com.louisgeek.louisdropdownview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.louisgeek.dropdownviewlib.ClassfiySeletView;
import com.louisgeek.dropdownviewlib.MultiSelectView;
import com.louisgeek.dropdownviewlib.NumberPickerView;
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
            public void onContentViewItemSeleted(String key, String keyWithOutFix, String name) {

            }

        });



       final NumberPickerView numberPickerView= (NumberPickerView) findViewById(R.id.id_npv);
        //numberPickerView.setMinAndMaxAndIncrement(1,10,0.5);
        // numberPickerView.setMinAndMaxAndIncrement(1,10,1);
        //  numberPickerView.setNowValue("xx");
        Button id_btn= (Button) findViewById(R.id.id_btn);
        id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, numberPickerView.getNowValue(), Toast.LENGTH_SHORT).show();
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
