package com.hans.wangfu.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {

    @Bind(R.id.searchBar)
    MaterialSearchBar searchBar;
    @Bind(R.id.list)
    ListView list;

    private Context mContext = this;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
                InputtipsQuery inputquery = new InputtipsQuery(String.valueOf(charSequence), "上海");
                //限制在当前城市
                inputquery.setCityLimit(true);
                //构造 Inputtips 对象，并设置监听。
                Inputtips inputTips = new Inputtips(mContext, inputquery);
                //回调结果集
                inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                    @Override
                    public void onGetInputtips(final List<Tip> tips, int i) {
                        //1000为成功
                        if (i == 1000) {
                            adapter = new Adapter(mContext, tips, R.layout.item_list);
                            list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                //调用 PoiSearch 的 requestInputtipsAsyn() 方法发送请求
                inputTips.requestInputtipsAsyn();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                if (!TextUtils.isEmpty(intent.getStringExtra("begin"))) {
                    Tip poi = ((Tip) parent.getItemAtPosition(position));
                    Intent begin = new Intent(mContext, NaviActivity.class);
                    begin.putExtra("begin", poi.getName());
                    begin.putExtra("beginLatitude", poi.getPoint().getLatitude() + "");
                    begin.putExtra("beginLongitude", poi.getPoint().getLongitude() + "");
                    setResult(1, begin);
                    finish();

                } else if (!TextUtils.isEmpty(intent.getStringExtra("end"))) {
                    Tip poi = ((Tip) parent.getItemAtPosition(position));
                    Intent end = new Intent(mContext, NaviActivity.class);
                    end.putExtra("end", poi.getName());
                    end.putExtra("endLatitude", poi.getPoint().getLatitude() + "");
                    end.putExtra("endLongitude", poi.getPoint().getLongitude() + "");
                    setResult(2, end);
                    finish();
                }

            }
        });
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }


    private class Adapter extends CommonAdapter<Tip> {
        public Adapter(Context context, List<Tip> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(CommonViewHolder holder, Tip datas) {
            holder.setText(R.id.text, datas.getName());
            Log.e("返回结果", datas.toString());
        }
    }
}
