package com.cmcc.mobilehealthcare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cmcc.mobilehealthcare.R;
import com.justalk.cloud.juscall.MtcCallDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shuyang on 2017/3/16.
 */

public class PhoneFragment extends BaseFragment {
    @BindView(R.id.listview1)
    ListView listview1;
    @BindView(R.id.listview2)
    ListView listview2;
    private List<String> contentList1=new ArrayList<>();
    private List<String> contentList2=new ArrayList<>();
    private List<String> contentList3=new ArrayList<>();
    private List<String> contentList4=new ArrayList<>();
    private List<String> nameList=new ArrayList<>();
    private List<String> messageList=new ArrayList<>();
    private List<String> phoneList=new ArrayList<>();
    private List<String> tableList=new ArrayList<>();
    private BaseAdapter baseAdapter1,baseAdapter2;
    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        nameList.add("tree");
        nameList.add("tree");
        messageList.add("电话问诊");
        messageList.add("视屏问诊");
        phoneList.add("语音电话");
        phoneList.add("视频电话");
        tableList.add("问诊表");
        for(int i=0;i<5;i++){
            contentList1.add("tree"+i);
            contentList2.add("[图片]"+i);
            contentList3.add("高血压控制");
            contentList4.add("3-10 15:3"+i);
        }
        setAdapter1();
        setAdapter2();
    }
    //适配数据
    public void setAdapter1() {
        if(baseAdapter1==null){
            baseAdapter1=new BaseAdapter() {
                @Override
                public int getCount() {
                    return nameList.size();
                }

                @Override
                public Object getItem(int position) {
                    return nameList.get(position);
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
                        convertView = View.inflate(get_Activity(), R.layout.item_of_waithandle, null);
                        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                        viewHolder.message = (TextView) convertView.findViewById(R.id.message);
                        viewHolder.phone=(TextView)convertView.findViewById(R.id.phone);
                        viewHolder.table=(TextView)convertView.findViewById(R.id.table);
                        convertView.setTag(viewHolder);
                    } else {
                        viewHolder = (ViewHolder) convertView.getTag();
                    }
                    viewHolder.name.setText(nameList.get(position));
                    viewHolder.message.setText(messageList.get(position));
                    viewHolder.phone.setText(phoneList.get(position));
                    if("语音电话".equals(phoneList.get(position))){
                        viewHolder.phone.setOnClickListener((v)->{
//                            Intent intent=new Intent(get_Activity(),VoiceCallActivity.class);
//                            startActivity(intent);
                            MtcCallDelegate.call(nameList.get(position), null, null, false, null);
                        });
                    }else if("视频电话".equals(phoneList.get(position))){
                        viewHolder.phone.setOnClickListener((v)->{
//                            Intent intent=new Intent(get_Activity(),VideoCallActivity.class);
//                            startActivity(intent);
                            MtcCallDelegate.call(nameList.get(position), null, null, true, null);
                        });
                    }
                    if("语音电话".equals(phoneList.get(position))){
                        viewHolder.table.setVisibility(View.VISIBLE);
                        viewHolder.table.setText(tableList.get(position));
                    }else {
                        viewHolder.table.setVisibility(View.GONE);
                    }
                    return convertView;
                }

                class ViewHolder {
                    TextView name;
                    TextView message;
                    TextView phone;
                    TextView table;
                }
            };
            listview1.setAdapter(baseAdapter1);
        }else{
            baseAdapter1.notifyDataSetChanged();
        }
    }
    //适配数据
    public void setAdapter2() {
        if(baseAdapter2==null){
            baseAdapter2=new BaseAdapter() {
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
            listview2.setAdapter(baseAdapter2);
        }else{
            baseAdapter2.notifyDataSetChanged();
        }
    }
    @Override
    public View oncreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(get_Activity(), R.layout.fragment_phone, null);
        ButterKnife.bind(this, view);
        initData();
        initListener();
        return view;
    }
}