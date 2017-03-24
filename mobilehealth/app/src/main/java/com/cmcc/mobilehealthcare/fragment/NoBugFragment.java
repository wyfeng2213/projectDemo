package com.cmcc.mobilehealthcare.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;


/**
 *
 * 重写fragment
 * @author  shuyang on 2016/1/25.
 */
public abstract class NoBugFragment extends RxFragment {

    protected View view;
    private Activity activity;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(view!=null){
            ViewGroup viewGroup=(ViewGroup) view.getParent();
            if(viewGroup!=null){
                viewGroup.removeView(view);
            }
        }
    }

    public Activity get_Activity(){
        return activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){
            activity=getActivity();
            view=oncreateView(inflater, container, savedInstanceState);
        }
        return view;
    }
    public abstract View oncreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);
}

