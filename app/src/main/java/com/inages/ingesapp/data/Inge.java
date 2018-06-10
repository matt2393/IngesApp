package com.inages.ingesapp.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.inages.ingesapp.data.IngesContract.IngeEntry;
import java.util.UUID;

public class Inge {
    private String id;
    private String name;
    private String specialty;
    private String phoneNumber;
    private String bio;
    private String avatarUri;

    public Inge(String name,
                String specialty, String phoneNumber,
                String bio, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.specialty = specialty;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.avatarUri = avatarUri;
    }

    public Inge(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(IngeEntry.ID));
        name = cursor.getString(cursor.getColumnIndex(IngeEntry.NAME));
        specialty = cursor.getString(cursor.getColumnIndex(IngeEntry.SPECIALTY));
        phoneNumber = cursor.getString(cursor.getColumnIndex(IngeEntry.PHONE_NUMBER));
        bio = cursor.getString(cursor.getColumnIndex(IngeEntry.BIO));
        avatarUri = cursor.getString(cursor.getColumnIndex(IngeEntry.AVATAR_URI));
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(IngeEntry.ID, id);
        values.put(IngeEntry.NAME, name);
        values.put(IngeEntry.SPECIALTY, specialty);
        values.put(IngeEntry.PHONE_NUMBER, phoneNumber);
        values.put(IngeEntry.BIO, bio);
        values.put(IngeEntry.AVATAR_URI, avatarUri);
        return values;
    }

}
