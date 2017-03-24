package com.cmcc.patient.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmcc.patient.R;


/**
 * Description:
 * Date：2017/03/16 10:02
 * Author: wangyong
 */

public class MineItemView extends RelativeLayout {

    private TextView item_tv_left;
    private TextView item_tv_center;
    private TextView tv_rightarrow;

    public MineItemView(Context context) {
        super(context);
    }

    public MineItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.mine_item_view, this, true);
        item_tv_left = (TextView) findViewById(R.id.item_tv_left);
        item_tv_center = (TextView) findViewById(R.id.item_tv_center);
        tv_rightarrow = (TextView) findViewById(R.id.tv_rightarrow);

        //设置文字
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MineItemView);

        CharSequence leftText = a.getText(R.styleable.MineItemView_left_text);
        if (leftText != null) {
            item_tv_left.setText(leftText);
        }

        CharSequence centerText = a.getText(R.styleable.MineItemView_center_text);
        if (centerText != null) {
            item_tv_center.setText(centerText);
        }

        CharSequence rightText = a.getText(R.styleable.MineItemView_right_text);
        if (leftText != null) {
            tv_rightarrow.setText(rightText);
        }

        //设置是否可见
        boolean flagCenter = a.getBoolean(R.styleable.MineItemView_center_tv_visible, true);
        if (flagCenter) {
            item_tv_center.setVisibility(View.VISIBLE);
        } else {
            item_tv_center.setVisibility(View.INVISIBLE);
        }
        a.recycle();
    }
}
