package com.louisgeek.louisdropdownview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.louisgeek.dropdownviewlib.ClassfiySeletView;
import com.louisgeek.dropdownviewlib.DateSelectView;
import com.louisgeek.dropdownviewlib.DropDownView;
import com.louisgeek.dropdownviewlib.MultiSelectView;
import com.louisgeek.dropdownviewlib.MultiSelectView_HasChild;
import com.louisgeek.dropdownviewlib.ProCateSelectView;
import com.louisgeek.dropdownviewlib.ProvinceCityAreaSelectView;
import com.louisgeek.dropdownviewlib.javabean.MultiSelectHasChildBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    List<Map<String, Object>> mMultiSelectMapListOut;
  List<MultiSelectHasChildBean> mMultiSelectHasChildBeanList;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MultiSelectView_HasChild idmsvgac = (MultiSelectView_HasChild) findViewById(R.id.id_msv_gac);

        final ProCateSelectView idpcsv = (ProCateSelectView) findViewById(R.id.id_pcsv);
        final ProvinceCityAreaSelectView idpcasv = (ProvinceCityAreaSelectView) findViewById(R.id.id_pcasv);
        final DateSelectView iddsv = (DateSelectView) findViewById(R.id.id_dsv);
        final DropDownView idddv = (DropDownView) findViewById(R.id.id_ddv);
        initData();
       // initHasChildData();

        final MultiSelectView multiSelectView= (MultiSelectView) findViewById(R.id.id_msv);
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
        final ClassfiySeletView id_csv = (ClassfiySeletView) findViewById(R.id.id_csv);





        Button id_btn= (Button) findViewById(R.id.id_btn);
        id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"=================DropDownView======================");
                Log.i(TAG, "onClick:getSelectKey "+idddv.getSelectKey());
                Log.i(TAG, "onClick: getSelectName"+idddv.getSelectName());
                Log.i(TAG,"=================DateSelectView======================");
                Log.i(TAG, "onClick: getNowSelectData"+iddsv.getNowSelectData());
                Log.i(TAG, "onClick: getNowSelectDataFixedNowData"+iddsv.getNowSelectDataFixedNowData());
                Log.i(TAG,"=================ProvinceCityAreaSelectView======================");
                Log.i(TAG, "onClick: getProvinceCityAreaKey"+idpcasv.getProvinceCityAreaKey());
                Log.i(TAG, "onClick: getProvinceCityAreaNameStr"+idpcasv.getProvinceCityAreaNameStr());
                Log.i(TAG, "onClick: getProvinceCityAreaNameStrWithOutFix"+idpcasv.getProvinceCityAreaNameStrWithOutFix());
                Log.i(TAG,"=================ProCateSelectView======================");
                Log.i(TAG, "onClick: getProCateKey"+idpcsv.getProCateKey());
                Log.i(TAG, "onClick: getProCateNameOnlyChild"+idpcsv.getProCateNameOnlyChild());
                Log.i(TAG, "onClick: getProCateKeyOnlyChildId"+idpcsv.getProCateKeyOnlyChildId());
                Log.i(TAG,"=================MultiSelectView======================");
                Log.i(TAG, "onClick: getSelectedKey"+multiSelectView.getSelectedKey());
                Log.i(TAG,"==================MultiSelectView_HasChild=====================");
                Log.i(TAG, "onClick: getSelectedKey"+idmsvgac.getSelectedKey());
                Log.i(TAG,"==================ClassfiySeletView=====================");
                Log.i(TAG, "onClick: getSelectedClassfiyKey"+id_csv.getSelectedClassfiyKey());
                Log.i(TAG, "onClick: getSelectedClassfiyKeyWithoutFix"+id_csv.getSelectedClassfiyKeyWithoutFix());
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
