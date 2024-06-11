package com.example.aplikasiposyandu_mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TimePicker;
import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private TimePicker  timePicker;

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

        // Initialize the TimePicker
        timePicker = view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        // Set listener for date selection
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Handle the selected date here
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                // You can perform any action with the selectedDate, like displaying it in a TextView
                // or passing it to another activity
            }
        });

        // Set listener for time selection
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // Handle the selected time here
                String selectedTime = hourOfDay + ":" + minute;
                // You can perform any action with the selectedTime, like displaying it in a TextView
                // or passing it to another activity
            }
        });
        return view;
    }
}
