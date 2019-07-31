package com.deanx.app.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.deanx.brouter.annotation.BRouter;
import com.deanx.brouter.annotation.Parameter;
import com.deanx.brouter.api.ParameterManager;
import com.deanx.brouter.api.RouterManager;

import static com.deanx.lib.commom.constant.Constant.STAR_TAG;


@BRouter(path = "/app_order/OrderMainActivity")
public class Order_MainActivity extends AppCompatActivity {

    @Parameter
    String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_main_activity);

        ParameterManager.getInstance().loadParameter(this);

        Log.e(STAR_TAG, " Order_MainActivity load params:  order_id=" + order_id);
    }

    public void clickPersonal(View view) {
        RouterManager.getInstance()
                .build("/app_personal/Personal_MainActivity")
                .withString("name", "Li shi, from module order")
                .navigation(this);
    }
}
