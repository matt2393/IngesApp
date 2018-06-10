package com.inages.ingesapp.data;

import android.provider.BaseColumns;

public class IngesContract {

    public static abstract class IngeEntry implements BaseColumns {

        public static final String TABLE_NAME ="Inge";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String SPECIALTY = "specialty";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String AVATAR_URI = "avatarUri";
        public static final String BIO = "bio";

    }
}
