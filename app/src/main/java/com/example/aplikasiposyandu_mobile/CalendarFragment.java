package com.example.aplikasiposyandu_mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private TextView scheduleText;
    private Map<String, String> scheduleData = new HashMap<>();

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchScheduleData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Initialize the CalendarView
        calendarView = view.findViewById(R.id.calendar_view);

        // Initialize the TextView
        scheduleText = view.findViewById(R.id.JadwalTanggal);

        // Set listener for date selection
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Handle the selected date here
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                String schedule = scheduleData.get(selectedDate);
                if (schedule != null) {
                    scheduleText.setText("Jadwal untuk tanggal " + selectedDate + ":\n" + schedule);
                } else {
                    scheduleText.setText("Tidak ada jadwal untuk tanggal " + selectedDate);
                }
            }
        });

        return view;
    }

    private void fetchScheduleData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userID = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance("https://aplikasiposyandu-mobile-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("imunisasi");
            reference.orderByChild("userID").equalTo(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    scheduleData.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ImunisasiData data = snapshot.getValue(ImunisasiData.class);
                        if (data != null) {
                            String date = data.getTanggalImunisasi();
                            String schedule = "Nama Anak: " + data.getNama();
                            scheduleData.put(date, schedule);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle possible errors.
                }
            });
        }
    }
}