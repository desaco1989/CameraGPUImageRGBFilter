package com.desaco.desacocarrybeauty.camera2;



import com.desaco.desacocarrybeauty.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * com.desaco.carrybeauty.camera2.CameraTwoActivity
 * 发现你的美，携带好心情
 * @author desaco
 *
 */
public class CameraTwoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2Fragment.newInstance())
                    .commit();
        }
    }
}
