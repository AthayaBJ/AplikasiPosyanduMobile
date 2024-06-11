package com.example.aplikasiposyandu_mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private TextView scheduleText;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Initialize the CalendarView
        calendarView = view.findViewById(R.id.calendar_view);

        // Initialize the TextView
        scheduleText = view.findViewById(R.id.schedule_text);

        // Example schedule data
        final Map<String, String> scheduleData = new HashMap<>();
        scheduleData.put("15/6/2024", "Pemeriksaan Balita pada jam 10:00 AM");
        scheduleData.put("20/6/2024", "Pemberian Imunisasi pada jam 08:00 AM");

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
}
