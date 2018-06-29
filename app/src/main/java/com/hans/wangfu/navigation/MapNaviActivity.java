package com.hans.wangfu.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;

import java.util.ArrayList;
import java.util.List;

public class MapNaviActivity extends AppCompatActivity implements AMapNaviListener, AMapNaviViewListener {

    //导航View
    private AMapNaviView aMapNaviView;
    private AMapNavi mAMapNavi;
    private static final String TAG = "MapNaviActivity";
    //起、终点list
    private List<NaviLatLng> mStartList;
    private List<NaviLatLng> mEndList;

    //出发与结束的经纬度
    public static void startActivity(Context context, double startlatitude, double startlongitude,
                                     double endlatitude, double endlongitude) {
        Intent intent = new Intent(context, MapNaviActivity.class);
        intent.putExtra(Constans.LATITUDE, startlatitude);
        intent.putExtra(Constans.LONGITUDE, startlongitude);
        intent.putExtra(Constans.ENDLATITUDE, endlatitude);
        intent.putExtra(Constans.ENDLONGITUDE, endlongitude);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_nvi);
        aMapNaviView = (AMapNaviView) this.findViewById(R.id.aMapNaviView);
        aMapNaviView.onCreate(savedInstanceState);
        initView();
    }

    /**
     * 初始化导航配置
     */
    private void initView() {
        //开始导航监听
        aMapNaviView.setAMapNaviViewListener(this);
        //获取AMapNavi实例
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        //添加监听回调，用于处理算路成功
        mAMapNavi.addAMapNaviListener(this);
        //设置模拟导航的行车速度（可坐在 onCalculateRouteSuccess方法中进行测试）
        //设置超过120，返回的速度为60
        mAMapNavi.setEmulatorNaviSpeed(120);
        //设置导航的起始点经纬度
        addLocation();
    }

    private void addLocation() {
        if (mStartList == null) {
            mStartList = new ArrayList<>();
        }
        if (mEndList == null) {
            mEndList = new ArrayList<>();
        }
        //起点纬度
        double startlatitude = getIntent().getDoubleExtra(Constans.LATITUDE, 0);
        //起点经度
        double startlongitude = getIntent().getDoubleExtra(Constans.LONGITUDE, 0);
        //终点纬度
        double endlatitude = getIntent().getDoubleExtra(Constans.ENDLATITUDE, 0);
        //终点经度
        double endlongitude = getIntent().getDoubleExtra(Constans.ENDLONGITUDE, 0);

        //起点、终点、多路径（null），基本参数
        NaviLatLng startLatLng = new NaviLatLng(startlatitude, startlongitude);
        NaviLatLng endLatLng = new NaviLatLng(endlatitude, endlongitude);
        mStartList.add(startLatLng);
        mEndList.add(endLatLng);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aMapNaviView != null) {
            aMapNaviView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (aMapNaviView != null) {
            aMapNaviView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (aMapNaviView != null) {
            aMapNaviView.onDestroy();
            mAMapNavi.stopNavi();
            mAMapNavi.destroy();
        }
    }

    /**
     * AMapNaviListener方法
     */
    @Override
    public void onInitNaviFailure() {
        showToast("初始化导航失败");
        Log.e(TAG, "初始化导航失败");
    }

    @Override
    public void onInitNaviSuccess() {
        showToast("初始化导航成功");
        Log.e(TAG, "初始化导航成功");
        try {
            int strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
            mAMapNavi.calculateDriveRoute(mStartList, mEndList, null, strategy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStartNavi(int i) {
        Log.e(TAG, "开始导航");
    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        Log.e(TAG, "当前实时位置的经度" + aMapNaviLocation.getCoord().getLongitude());
        Log.e(TAG, "当前实时位置的纬度" + aMapNaviLocation.getCoord().getLatitude());

    }

    @Override
    public void onGetNavigationText(int i, String s) {
        //i=1返回成功 （可自行添加语音播报）
        Log.e(TAG, "播报类型和播报文字回调: " + i + "\n" + s);
    }

    @Override
    public void onGetNavigationText(String s) {
        //播报类型和播报文字回调(旧方法) 会执行但S不会有返回
    }

    //结束模拟导航
    @Override
    public void onEndEmulatorNavi() {
    }

    //导航结束
    @Override
    public void onArriveDestination() {
    }

    //路线计算失败
    @Override
    public void onCalculateRouteFailure(int i) {
    }

    //偏航后重新计算路线回调
    @Override
    public void onReCalculateRouteForYaw() {
    }

    //拥堵后重新计算路线回调
    @Override
    public void onReCalculateRouteForTrafficJam() {
    }

    @Override
    public void onArrivedWayPoint(int i) {
    }

    @Override
    public void onGpsOpenStatus(boolean b) {
        Log.e(TAG, "GPS开启的状态: " + b);
        if (b) {
            showToast("GPS已开启");
        } else {
            showToast("GPS未开启");
        }
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        Log.e(TAG, "当前行驶的速度：" + naviInfo.getCurrentSpeed());
        Log.e(TAG, "当前行驶的道路：" + naviInfo.getCurrentRoadName());
    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        Log.e(TAG, "路线计算成功: ");
        //GPS 模式 1 EMULATOR 模拟导航 2
//        mAMapNavi.startNavi(NaviType.EMULATOR);
        mAMapNavi.startNavi(NaviType.GPS);
    }

    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            showToast("当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            showToast("当前在主路");
            return;
        }
        if (i == 2) {
            showToast("当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    /****************************************************************AMapNaviViewListener 方法 底部导航View监听*/
    @Override
    public void onNaviSetting() {
        Log.e(TAG, "底部导航设置点击回调: ");
    }

    @Override
    public void onNaviCancel() {
        this.finish();
        Log.e(TAG, "取消导航设置点击回调: ");
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {
        Log.e(TAG, "地图的模式: " + i);
    }

    @Override
    public void onNaviTurnClick() {
        Log.e(TAG, "转弯view的点击回调: ");
    }

    @Override
    public void onNextRoadClick() {
        Log.e(TAG, "下一个道路View点击回调: ");
    }

    @Override
    public void onScanViewButtonClick() {

    }

    @Override
    public void onLockMap(boolean b) {

    }

    @Override
    public void onNaviViewLoaded() {

    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
