/*
 * Created by Kultala Aki on 5/14/22, 6:56 PM
 * Copyright (c) 2022. All rights reserved.
 * Last modified 5/14/22, 6:56 PM
 */

package kultalaaki.vpkapuri.alarmdetection;

import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Public class for detecting if sender is alarm provider, person who is attending alarm or
 * someone else.
 */
public class PhoneNumberDetector {

    private final String numberToCheck;
    private final ArrayList<String> alarmNumbers;
    private final ArrayList<String> memberNumbers;
    private final ArrayList<String> vapepaNumbers;
    private final SharedPreferences preferences;
    private final NumberFormatter formatter;


    public PhoneNumberDetector(String numberToCheck, SharedPreferences preferences) {
        this.numberToCheck = numberToCheck;
        this.alarmNumbers = new ArrayList<>();
        this.memberNumbers = new ArrayList<>();
        this.vapepaNumbers = new ArrayList<>();
        this.preferences = preferences;
        this.formatter = new NumberFormatter();
        populateNumbers();
        populateMembers();
        populateVapepaAlarmNumbers();
    }

    private void populateNumbers() {
        for (int i = 1; i <= 10; i++) {
            alarmNumbers.add(formatter.formatNumber(preferences.getString("halyvastaanotto" + i, null)));
        }
    }

    private void populateMembers() {
        for (int i = 1; i <= 50; i++) {
            memberNumbers.add(formatter.formatNumber(preferences.getString("nimi" + i, null)));
        }
    }

    public int getIndexPosition(String number) {
        return memberNumbers.indexOf(number);
    }

    private void populateVapepaAlarmNumbers() {
        for (int i = 1; i <= 5; i++) {
            vapepaNumbers.add(formatter.formatNumber(preferences.getString("vapepanumber" + i, null)));
        }
    }

    /**
     * @return number 0 is not in application settings
     * number 1 is alarm number
     * number 2 is member
     * number 3 is Vapepa
     */
    public int whoSent() {
        if (alarmNumbers.contains(numberToCheck)) {
            return 1;
        } else if (memberNumbers.contains(numberToCheck)) {
            return 2;
        } else if (vapepaNumbers.contains(numberToCheck)) {
            return 3;
        }
        return 0;
    }

    public void addNumberToAlarmNumberList(String number) {
        alarmNumbers.add(formatter.formatNumber(number));
    }

    public void addNumberToMemberNumberList(String number) {
        memberNumbers.add(formatter.formatNumber(number));
    }

    public void addNumberTovapepaNumberList(String number) {
        vapepaNumbers.add(formatter.formatNumber(number));
    }
}
