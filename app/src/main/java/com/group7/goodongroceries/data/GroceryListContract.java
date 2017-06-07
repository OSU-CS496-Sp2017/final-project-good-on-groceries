package com.group7.goodongroceries.data;


public class GroceryListContract {
    private GroceryListContract() {}

    public static class ListItems {
        public static final String TABLE_NAME = "gog_list";
        public static final String _ID = "_id";
        public static final String COLUMN_ENTRY = "entry";
        public static final String COLUMN_CHECKED = "checked";
        public static final String COLUMN_FOOD_ID = "food_id";
    }

    public static class Food {
        public static final String TABLE_NAME = "food";
        public static final String _ID = "_id";
        public static final String COLUMN_DESCRIPTION = "description";
    }

    public static class Nutrients {
        public static final String TABLE_NAME = "nutrients";
        public static final String _ID = "_id";
        public static final String COLUMN_GROUP = "ngroup";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_MEASUREMENT = "measurement";
        public static final String COLUMN_FOOD_ID = "food_id";
    }
}
