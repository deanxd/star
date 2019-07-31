package com.deanx.app.presonal;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.deanx.brouter.annotation.BRouter;
import com.deanx.brouter.annotation.Parameter;
import com.deanx.brouter.api.ParameterManager;
import com.deanx.brouter.api.RouterManager;
import com.deanx.lib.commom.base.BaseActivity;

import static com.deanx.lib.commom.constant.Constant.STAR_TAG;

@BRouter(path = "/app_personal/Personal_MainActivity")
public class Personal_MainActivity extends BaseActivity {

    @Parameter
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_main_activity);

        ParameterManager.getInstance().loadParameter(this);

        Log.e(STAR_TAG, "Personal_MainActivity loadparameter: name = " + name);
    }

    public void clickOrder(View view) {
        RouterManager.getInstance()
                .build("/app_order/OrderMainActivity")
                .withString("order_id", "5678, from module personal")
                .navigation(this);
    }

}
