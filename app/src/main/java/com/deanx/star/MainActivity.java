package com.deanx.star;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.deanx.brouter.annotation.BRouter;
import com.deanx.brouter.annotation.Parameter;
import com.deanx.brouter.api.ParameterManager;
import com.deanx.brouter.api.RouterManager;
import com.deanx.star.module.service.order.drawable.OrderDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;


@BRouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_img)
    ImageView imageView;

    @Parameter(name = "/app_order/getDrawable")
    OrderDrawable orderDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ParameterManager.getInstance().loadParameter(this);

//        OrderDrawable navigation = (OrderDrawable) RouterManager.getInstance()
//                .build("/app_order/getDrawable")
//                .navigation(this);

        imageView.setImageResource(orderDrawable.getDrawable());
    }

    public void jumpOrder(View view) {
        RouterManager.getInstance()
                .build("/app_order/OrderMainActivity")
                .withString("order_id", "123456, from main app")
                .navigation(this);
    }

    public void jumpPersonal(View view) {
        RouterManager.getInstance()
                .build("/app_personal/Personal_MainActivity")
                .withString("name", "zhang san, from main app")
                .navigation(this);
    }
}
