package com.hans.wangfu.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NaviActivity extends AppCompatActivity {

    @Bind(R.id.tv_begin)
    TextView tvBegin;
    @Bind(R.id.ll_begin)
    LinearLayout llBegin;
    @Bind(R.id.tv_end)
    TextView tvEnd;
    @Bind(R.id.ll_end)
    LinearLayout llEnd;
    @Bind(R.id.nts_center)
    NavigationTabStrip mTab;
    @Bind(R.id.map)
    MapView mMapView;
    @Bind(R.id.search)
    ImageView search;
    private AMap aMap;

    private Double beginLatitude;
    private Double beginLongitude;
    private Double endLatitude;
    private Double endLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        initUI();
        setUI();
    }

    private void initUI() {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra("address"))) {
            tvBegin.setText(intent.getStringExtra("address"));
            beginLatitude = Double.parseDouble(intent.getStringExtra("beginLatitude"));
            beginLongitude = Double.parseDouble(intent.getStringExtra("beginLongitude"));
        }
    }

    private void setUI() {
        mTab.setTabIndex(0);
    }

    @OnClick({R.id.tv_begin, R.id.tv_end, R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_begin:
                Intent begin = new Intent(this, SearchActivity.class);
                begin.putExtra("begin", "begin");
                startActivityForResult(begin, 1);
                break;
            case R.id.tv_end:
                Intent end = new Intent(this, SearchActivity.class);
                end.putExtra("end", "end");
                startActivityForResult(end, 2);
                break;
            case R.id.search:
                Toast.makeText(this, "路线规划", Toast.LENGTH_SHORT).show();
                MapNaviActivity.startActivity(NaviActivity.this, beginLatitude, beginLongitude, endLatitude, endLongitude);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {//起点
            tvBegin.setText(intent.getStringExtra("begin"));
            beginLatitude = Double.parseDouble(intent.getStringExtra("beginLatitude"));
            beginLongitude = Double.parseDouble(intent.getStringExtra("beginLongitude"));
        } else if (requestCode == 2) {
            tvEnd.setText(intent.getStringExtra("end"));
            endLatitude = Double.parseDouble(intent.getStringExtra("endLatitude"));
            endLongitude = Double.parseDouble(intent.getStringExtra("endLongitude"));
        }
    }
}
