package com.lthwea.finedust.alarm;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

public class MyAlarmDialog extends Dialog {
    public MyAlarmDialog(@NonNull Context context) {
        super(context);
    }

    /*protected static int default_width = WindowManager.LayoutParams.WRAP_CONTENT; // 기본 폭
    protected static int default_height = WindowManager.LayoutParams.WRAP_CONTENT;// 디폴트의 높이
    public static int TYPE_TWO_BT = 2;
    public static int TYPE_NO_BT = 0;
    public TextView dialog_title;
    public EditText dialog_message;
    public Button bt_cancel, bt_confirm;
    private LinearLayout ll_button;
    protected Context mContext;
    private View.OnClickListener listener;
    private View customView;
    //	@Bind(R.id.icon)
    ImageView icon;


    public MyAlarmDialog(Context context, int style) {
        super(context, R.style.FullScreenDialog);

        mContext = context;
        customView = LayoutInflater.from(context).inflate(R.layout.alarm_dialog_simple, null);

        icon = (ImageView) customView.findViewById(R.id.icon);

        ll_button = (LinearLayout) customView.findViewById(R.id.ll_button);
        dialog_title = (TextView) customView.findViewById(R.id.dialog_title);
        setTitle("알림");
        dialog_message = (EditText) customView.findViewById(R.id.dialog_message);
        dialog_message.clearFocus();
        bt_confirm = (Button) customView.findViewById(R.id.dialog_confirm);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(customView);
        //ButterKnife  view绑定
        //ButterKnife.bind(this,customView);
    }

    public MyAlarmDialog setClickListener(View.OnClickListener listener) {
        this.listener = listener;
        bt_confirm.setOnClickListener(listener);
        return this;
    }

    public MyAlarmDialog setMessage(String message) {
        dialog_message.setText(message);
        return this;
    }

    public MyAlarmDialog setTitle(String title) {
        dialog_title.setText(title);
        return this;
    }

    public MyAlarmDialog setIcon(int iconResId) {
        dialog_title.setVisibility(View.GONE);
        icon.setVisibility(View.VISIBLE);
        icon.setBackgroundResource(iconResId);

        return this;
    }*/

}
