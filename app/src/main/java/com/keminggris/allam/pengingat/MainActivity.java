package com.keminggris.allam.pengingat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mengatur Onclick Listener.
        findViewById(R.id.tbAtur).setOnClickListener(this);
        findViewById(R.id.tbBatal).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText editTeks = findViewById(R.id.editTeks);
        TimePicker pilihanWaktu = findViewById(R.id.pilihanWaktu);

        //Mengatur ID notifikasi & teks.
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("daftarKegiatan", editTeks.getText().toString());

        // getBroadcast(context, requestCode, intent flags)
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        switch (view.getId()) {
            case R.id.tbAtur:
                int hour = pilihanWaktu.getCurrentHour();
                int minute = pilihanWaktu.getCurrentMinute();

                // Menyetel waktu.
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                // Mengatur alarm.
                // Mengatur(type, millisecond, intent)
                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);

                Toast.makeText(this, "Selesai!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tbBatal:
                alarm.cancel(alarmIntent);
                Toast.makeText(this, "Dibatalkan", Toast.LENGTH_LONG).show();
                break;
        }

    }

}
