package com.cmcc.mobilehealthcare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cmcc.mobilehealthcare.R;
import com.cmcc.mobilehealthcare.activity.ChatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shuyang on 2017/3/16.
 */

public class MessageFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView listview;
    private BaseAdapter baseAdapter;
    private List<String> contentList1=new ArrayList<>();
    private List<String> contentList2=new ArrayList<>();
    private List<String> contentList3=new ArrayList<>();
    private List<String> contentList4=new ArrayList<>();
    @Override
    public void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(get_Activity(), ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        for(int i=0;i<5;i++){
            contentList1.add("tree"+i);
            contentList2.add("[图片]"+i);
            contentList3.add("高血压控制");
            contentList4.add("3-10 15:3"+i);
        }
        setAdapter();
    }
    //适配数据
    public void setAdapter() {
        if(baseAdapter==null){
            baseAdapter=new BaseAdapter() {
                @Override
                public int getCount() {
                    return contentList1.size();
                }

                @Override
                public Object getItem(int position) {
                    return contentList1.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    ViewHolder viewHolder;
                    if (convertView == null) {
                        viewHolder = new ViewHolder();
                        convertView = View.inflate(get_Activity(), R.layout.item_of_history, null);
                        viewHolder.content1 = (TextView) convertView.findViewById(R.id.content1);
                        viewHolder.content2 = (TextView) convertView.findViewById(R.id.content2);
                        viewHolder.content3=(TextView)convertView.findViewById(R.id.content3);
                        viewHolder.content4=(TextView)convertView.findViewById(R.id.content4);
                        convertView.setTag(viewHolder);
                    } else {
                        viewHolder = (ViewHolder) convertView.getTag();
                    }
                    viewHolder.content1.setText(contentList1.get(position));
                    viewHolder.content2.setText(contentList2.get(position));
                    viewHolder.content3.setText(contentList3.get(position));
                    viewHolder.content4.setText(contentList4.get(position));
                    return convertView;
                }

                class ViewHolder {
                    TextView content1;
                    TextView content2;
                    TextView content3;
                    TextView content4;
                }
            };
            listview.setAdapter(baseAdapter);
        }else{
            baseAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public View oncreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(get_Activity(), R.layout.fragment_message, null);
        ButterKnife.bind(this, view);
        initData();
        initListener();
        return view;
    }

}
