package com.example.baseremotecontroller.utility;

public class Constants {

    public static final String DB_NAME = "page_database";

    public static final String VIEW_FORMAT = ".widget.%s.%sView";

    public static final String ENTITY_FORMAT = ".widget.%s.%sEntity";

    public static final String VIEWHOLDER_FORMAT = ".widget.%s.%sDetailVH";

    public static final String DETAIL_LAYOUT_FORMAT = "widget_detail_%s";

    public static final String WIDGET_NAMING = "%S #%d";

    // ViewPager2 Constants ------------------------------------------------------------------------
    public static final int HOME_FRAGMENT_NUM = 0;

    public static final int RVIZ_FRAGMENT_NUM = 1;

    public static final int MAP_FRAGMENT_NUM = 2;

    public static final int SETTING_FRAGMENT_NUM = 3;

    public static String getMenuName(int position) {
        String name = "Default";

        switch (position) {
            case HOME_FRAGMENT_NUM:
                name = "HOME";
                break;
            case RVIZ_FRAGMENT_NUM:
                name = "VIZ";
                break;
            case MAP_FRAGMENT_NUM:
                name = "DETAIL";
                break;
            case SETTING_FRAGMENT_NUM:
                name = "SETTING";
                break;
        }

        return name;
    }
}
