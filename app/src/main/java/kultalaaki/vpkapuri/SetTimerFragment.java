package kultalaaki.vpkapuri;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SetTimerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SetTimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetTimerFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    DBTimer dbTimer;
    TextView hourSelector, minuteSelector, hourSelector2, minuteSelector2;
    EditText name;
    Switch stateSelector;
    Button monday, tuesday, wednesday, thursday, friday, saturday, sunday, cancel, save;
    boolean bMonday = false, bTuesday = false, bWednesday = false, bThursday = false, bFriday = false, bSaturday = false, bSunday = false, startOrStopSelector, selectoryo = false;
    String ma, ti, ke, to, pe, la, su, startTime, stopTime, state, timerName;

    private AlarmManager alarmMgrStart;
    private PendingIntent alarmIntentStart;
    private AlarmManager alarmMgrStop;
    private PendingIntent alarmIntentStop;
    Context ctx;

    private OnFragmentInteractionListener mListener;

    public SetTimerFragment() {
        // Required empty public constructor
    }

    public static SetTimerFragment newInstance(String primaryKey) {
        SetTimerFragment fragment = new SetTimerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, primaryKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    public void onStart() {
        super.onStart();
        dbTimer = new DBTimer(getActivity());
        if(getArguments()!= null) {
            populateTimer(mParam1);
            cancel.setText(R.string.poistaAjastin);
        } else {
            stateSelectorState();
            setOnClickListeners();
        }
    }

    void populateTimer(String primaryKey) {
        Cursor cursor = dbTimer.timerID(primaryKey);
        if(cursor != null) {
            timerName = cursor.getString(cursor.getColumnIndex(DBTimer.NAME));
            name.setText(timerName);
            ma = cursor.getString(cursor.getColumnIndex(DBTimer.MA));
            ti = cursor.getString(cursor.getColumnIndex(DBTimer.TI));
            ke = cursor.getString(cursor.getColumnIndex(DBTimer.KE));
            to = cursor.getString(cursor.getColumnIndex(DBTimer.TO));
            pe = cursor.getString(cursor.getColumnIndex(DBTimer.PE));
            la = cursor.getString(cursor.getColumnIndex(DBTimer.LA));
            su = cursor.getString(cursor.getColumnIndex(DBTimer.SU));
            startTime = cursor.getString(cursor.getColumnIndex(DBTimer.STARTTIME));
            stopTime = cursor.getString(cursor.getColumnIndex(DBTimer.STOPTIME));
            state = cursor.getString(cursor.getColumnIndex(DBTimer.SELECTOR));
            if(ma.equals("Ma")) { bMonday = true; monday.setTextColor(getResources().getColor(R.color.orange)); }
            if(ti.equals("Ti")) { bTuesday = true; tuesday.setTextColor(getResources().getColor(R.color.orange)); }
            if(ke.equals("Ke")) { bWednesday = true; wednesday.setTextColor(getResources().getColor(R.color.orange)); }
            if(to.equals("To")) { bThursday = true; thursday.setTextColor(getResources().getColor(R.color.orange)); }
            if(pe.equals("Pe")) { bFriday = true; friday.setTextColor(getResources().getColor(R.color.orange)); }
            if(la.equals("La")) { bSaturday = true; saturday.setTextColor(getResources().getColor(R.color.orange)); }
            if(su.equals("Su")) { bSunday = true; sunday.setTextColor(getResources().getColor(R.color.orange)); }
            if(state.equals("Yötila")) {
                selectoryo = true;
                stateSelector.setText(R.string.nightMode);
                stateSelector.setChecked(true);
            } else {
                selectoryo = false;
                stateSelector.setText(R.string.pref_ringtone_silent);
                stateSelector.setChecked(false);
            }
            hourSelector.setText(startTime.substring(0,2));
            minuteSelector.setText(startTime.substring(3,5));
            hourSelector2.setText(stopTime.substring(0,2));
            minuteSelector2.setText(stopTime.substring(3,5));
            setOnClickListeners();
            stateSelectorState();
        }
    }

    public void setOnClickListeners() {
        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bMonday) {
                    bMonday = true;
                    ma = "Ma";
                    monday.setTextColor(getResources().getColor(R.color.orange));
                } else {
                    bMonday = false;
                    ma = "";
                    monday.setTextColor(getResources().getColor(R.color.text_color_primary));
                }
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bTuesday) {
                    bTuesday = true;
                    ti = "Ti";
                    tuesday.setTextColor(getResources().getColor(R.color.orange));
                } else {
                    bTuesday = false;
                    ti = "";
                    tuesday.setTextColor(getResources().getColor(R.color.text_color_primary));
                }
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bWednesday) {
                    bWednesday = true;
                    ke = "Ke";
                    wednesday.setTextColor(getResources().getColor(R.color.orange));
                } else {
                    bWednesday = false;
                    ke = "";
                    wednesday.setTextColor(getResources().getColor(R.color.text_color_primary));
                }
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bThursday) {
                    bThursday = true;
                    to = "To";
                    thursday.setTextColor(getResources().getColor(R.color.orange));
                } else {
                    bThursday = false;
                    to = "";
                    thursday.setTextColor(getResources().getColor(R.color.text_color_primary));
                }
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bFriday) {
                    bFriday = true;
                    pe = "Pe";
                    friday.setTextColor(getResources().getColor(R.color.orange));
                } else {
                    bFriday = false;
                    pe = "";
                    friday.setTextColor(getResources().getColor(R.color.text_color_primary));
                }
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bSaturday) {
                    bSaturday = true;
                    la = "La";
                    saturday.setTextColor(getResources().getColor(R.color.orange));
                } else {
                    bSaturday = false;
                    la = "";
                    saturday.setTextColor(getResources().getColor(R.color.text_color_primary));
                }
            }
        });
        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bSunday) {
                    bSunday = true;
                    su = "Su";
                    sunday.setTextColor(getResources().getColor(R.color.orange));
                } else {
                    bSunday = false;
                    su = "";
                    sunday.setTextColor(getResources().getColor(R.color.text_color_primary));
                }
            }
        });
        hourSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrStopSelector = true;
                DialogFragment timePicker = new TimePickerFragment();
                if(getFragmentManager() != null) {
                    timePicker.show(getFragmentManager(), "time picker");
                }
            }
        });
        minuteSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrStopSelector = true;
                DialogFragment timePicker = new TimePickerFragment();
                if(getFragmentManager() != null) {
                    timePicker.show(getFragmentManager(), "time picker");
                }
            }
        });
        hourSelector2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrStopSelector = false;
                DialogFragment timePicker = new TimePickerFragment();
                if(getFragmentManager() != null) {
                    timePicker.show(getFragmentManager(), "time picker");
                }
            }
        });
        minuteSelector2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrStopSelector = false;
                DialogFragment timePicker = new TimePickerFragment();
                if(getFragmentManager() != null) {
                    timePicker.show(getFragmentManager(), "time picker");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getArguments() != null) {
                    int sija = Integer.parseInt(mParam1);
                    dbTimer.deleteRow(sija);
                    deleteAlarms(mParam1);
                    if(getActivity() != null) {
                        getActivity().onBackPressed();
                    }
                } else {
                    if(getActivity() != null) {
                        getActivity().onBackPressed();
                    }
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getArguments() != null) {
                    deleteAlarms(mParam1);
                    timerName = name.getText().toString();
                    startTime = hourSelector.getText().toString() + ":" + minuteSelector.getText().toString();
                    stopTime = hourSelector2.getText().toString() + ":" + minuteSelector2.getText().toString();
                    dbTimer.tallennaMuutokset(mParam1, timerName, startTime, stopTime, ma, ti, ke, to, pe, la, su, state, "on");
                    if(getActivity() != null) {
                        setAlarms(mParam1, startTime, stopTime);
                        Toast.makeText(getActivity(), "Tallennettu", Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                } else {
                    saveTimerToDBs();
                }
            }
        });
    }

    public void stateSelectorState() {
        stateSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    selectoryo = true;
                    state = "Yötila";
                    stateSelector.setText(R.string.nightMode);
                } else {
                    selectoryo = false;
                    state = "Äänetön";
                    stateSelector.setText(R.string.pref_ringtone_silent);
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        stateSelector = view.findViewById(R.id.switchAaneton);
        monday = view.findViewById(R.id.buttonMonday);
        tuesday = view.findViewById(R.id.buttonTuesday);
        wednesday = view.findViewById(R.id.buttonWednesday);
        thursday = view.findViewById(R.id.buttonThursday);
        friday = view.findViewById(R.id.buttonFriday);
        saturday = view.findViewById(R.id.buttonSaturday);
        sunday = view.findViewById(R.id.buttonSunday);
        cancel = view.findViewById(R.id.buttonCancel);
        save = view.findViewById(R.id.buttonSave);
        name = view.findViewById(R.id.ajastinNimi2);
        hourSelector = view.findViewById(R.id.hourSelector);
        minuteSelector = view.findViewById(R.id.minuteSelector);
        hourSelector2 = view.findViewById(R.id.hourSelector2);
        minuteSelector2 = view.findViewById(R.id.minuteSelector2);
    }

    public void setTimerTimes(int hour, int minute) {
        String min = String.valueOf(minute);
        String hou = String.valueOf(hour);
        if(startOrStopSelector) {
            if(minute < 10) { min = "0" + minute; }
            if(hour < 10) {hou = "0" + hour; }
            hourSelector.setText(hou);
            minuteSelector.setText(min);
        } else {
            if(minute < 10) { min = "0" + minute; }
            if(hour < 10) {hou = "0" + hour; }
            hourSelector2.setText(hou);
            minuteSelector2.setText(min);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener.showAddTimer();
        mListener = null;
        dbTimer.close();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        long saveTimerToDB(String name, String startTime, String stopTime, String ma, String ti, String ke, String to,
                           String pe, String la, String su, String selector, String isiton);
        //void updateListview();
    }

    void saveTimerToDBs() {
        String ma="", ti="", ke="", to="", pe="", la="", su="", selector, timerName, startTime, stopTime;
        if(bMonday){ma="Ma";}if(bTuesday){ti="Ti";}if(bWednesday){ke="Ke";}if(bThursday){to="To";}if(bFriday){pe="Pe";}if(bSaturday){la="La";}if(bSunday){su="Su";}
        if(selectoryo){ selector = "Yötila"; } else { selector = "Äänetön"; }
        timerName = name.getText().toString();
        startTime = hourSelector.getText().toString() + ":" + minuteSelector.getText().toString();
        stopTime = hourSelector2.getText().toString() + ":" + minuteSelector2.getText().toString();
        long rowId = mListener.saveTimerToDB(timerName, startTime, stopTime, ma, ti, ke, to, pe, la, su, selector, "on");
        //mListener.updateListview();
        int rowIdToInt = (int)rowId;
        String  rowIdString = String.valueOf(rowIdToInt);
        if(getActivity() != null && setAlarms(rowIdString, startTime, stopTime)) {
            getActivity().onBackPressed();
        }
    }

    /*  Setting alarm intents
    *   RequestCode is key + Hour + Minute for canceling reasons
    *   Setting time based on user input
    */
    boolean setAlarms(String key, String startTime, String stopTime) {

        if(ctx != null) {
            // requestCode is key + Hour + Minute for canceling reasons

            String startHour = startTime.substring(0,2);
            String startMinute = startTime.substring(3,5);
            if(startHour.charAt(0) == '0') { startHour = startTime.substring(1,2); }
            if(startMinute.charAt(0) == '0') { startMinute = startTime.substring(4,5); }
            int startHourPar = Integer.parseInt(startHour);
            int startMinutePar = Integer.parseInt(startMinute);
            int requestCode = Integer.parseInt(key) + startHourPar + startMinutePar;
            //Toast.makeText(getActivity(), "Hour: " + startHourPar + " Minute: " + startMinutePar, Toast.LENGTH_LONG).show();
            Log.e("TAG", "Hour: " + startHourPar + " Minute: " + startMinutePar);

            alarmMgrStart = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
            Intent intentStart = new Intent(getActivity(), AlarmReceiver.class);
            intentStart.putExtra("primaryKey", key);
            intentStart.putExtra("StartOrStop", "Starting alarm");
            alarmIntentStart = PendingIntent.getBroadcast(getActivity(), requestCode, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);


            // Setting time based on user input
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, startHourPar);
            calendar.set(Calendar.MINUTE, startMinutePar);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmMgrStart.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntentStart);
            }
            alarmMgrStart.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntentStart);



            String stopHour = stopTime.substring(0,2);
            String stopMinute = stopTime.substring(3,5);
            if(stopHour.charAt(0) == '0') { stopHour = stopTime.substring(1,2); }
            if(stopMinute.charAt(0) == '0') { stopMinute = stopTime.substring(4,5);}
            int stopHourPar = Integer.parseInt(stopHour);
            int stopMinutePar = Integer.parseInt(stopMinute);
            requestCode = Integer.parseInt(key) + stopHourPar + stopMinutePar;

            alarmMgrStop = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
            Intent intentStop = new Intent(getActivity(), AlarmReceiver.class);
            intentStop.putExtra("primaryKey", key);
            intentStop.putExtra("StartOrStop", "Stopping alarm");
            alarmIntentStop = PendingIntent.getBroadcast(getActivity(), requestCode, intentStop, PendingIntent.FLAG_UPDATE_CURRENT);


            // Setting time based on user input
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(System.currentTimeMillis());
            calendar1.set(Calendar.HOUR_OF_DAY, stopHourPar);
            calendar1.set(Calendar.MINUTE, stopMinutePar);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmMgrStop.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), alarmIntentStop);
            }
            alarmMgrStop.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntentStop);
            return true;
        }
        return false;
    }

    void deleteAlarms(String key) {
        // cancel starting intent.
        String startHour = startTime.substring(0,2);
        String startMinute = startTime.substring(3,5);
        if(startHour.charAt(0) == '0') { startHour = startTime.substring(1,2); }
        if(startMinute.charAt(0) == '0') { startMinute = startTime.substring(4,5); }
        int startHourPar = Integer.parseInt(startHour);
        int startMinutePar = Integer.parseInt(startMinute);
        int requestCode = Integer.parseInt(key) + startHourPar + startMinutePar;

        alarmMgrStart = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        Intent intentStart = new Intent(getActivity(), AlarmReceiver.class);
        intentStart.putExtra("primaryKey", key);
        intentStart.putExtra("StartOrStop", "Starting alarm");
        alarmIntentStart = PendingIntent.getBroadcast(getActivity(), requestCode, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);
        if(alarmMgrStart != null) {
            alarmMgrStart.cancel(alarmIntentStart);
        }

        // cancel stopping intent.
        String stopHour = stopTime.substring(0,2);
        String stopMinute = stopTime.substring(3,5);
        if(stopHour.charAt(0) == '0') { stopHour = stopTime.substring(1,2); }
        if(stopMinute.charAt(0) == '0') { stopMinute = stopTime.substring(4,5); }
        int stopHourPar = Integer.parseInt(stopHour);
        int stopMinutePar = Integer.parseInt(stopMinute);
        requestCode = Integer.parseInt(key) + stopHourPar + stopMinutePar;

        alarmMgrStop = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        Intent intentStop = new Intent(getActivity(), AlarmReceiver.class);
        intentStop.putExtra("primaryKey", key);
        intentStop.putExtra("StartOrStop", "Stopping alarm");
        alarmIntentStop = PendingIntent.getBroadcast(getActivity(), requestCode, intentStop, PendingIntent.FLAG_UPDATE_CURRENT);
        if(alarmMgrStop != null) {
            alarmMgrStop.cancel(alarmIntentStop);
        }
    }
}