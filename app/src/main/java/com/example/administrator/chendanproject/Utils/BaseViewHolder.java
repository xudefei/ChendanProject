package com.example.administrator.chendanproject.Utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by Administrator on 2017/3/16.
 */

public class BaseViewHolder {
    /**
     * view:适配的View
     * id:适配的View中子View的id
     * */
    public static <T extends View> T get(View view,int id){
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if(viewHolder == null){
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View ChildView = viewHolder.get(id);
        if(ChildView == null){
            ChildView = view.findViewById(id);
            viewHolder.put(id,ChildView);
        }
        return (T) ChildView;

    }

}
