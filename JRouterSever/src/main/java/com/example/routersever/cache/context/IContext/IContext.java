package com.example.routersever.cache.context.IContext;

import android.content.Context;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 10:19.
 * Function :
 */
public interface IContext {

    void put(Context context);

    Context get();
}
