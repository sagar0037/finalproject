package com.example.resultchecker;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class BackgroundTask {
    private static BackgroundTask bIns;
    private RequestQueue reqQue;
    private Context ctx;

    public BackgroundTask (Context ctx){
        this.ctx = ctx;
        reqQue = getReq_que();
    }

    public  RequestQueue getReq_que(){
        if(reqQue==null){
            Cache cache = new DiskBasedCache(ctx.getCacheDir(),1024*1024);
            Network network = new BasicNetwork(new HurlStack());
            reqQue = new RequestQueue(cache, network);
            reqQue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return reqQue;
    }

    public static synchronized BackgroundTask getBgIns(Context context){
        if(bIns==null){
            bIns = new BackgroundTask(context);
        }
        return bIns;
    }

    public <T> void addToRequestQueue(Request<T> request){
        reqQue.add(request);
    }

}
