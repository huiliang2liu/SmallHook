package com.hook;

import java.util.List;

/**
 * com.hook
 * 2018/11/1 15:26
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class Response {
    public int code;
    public String message;
    List<Ad> ads;

    public class Ad {
        public String id;
        public int inventory_type;
        public int action;
        public String html_snippet;
        public int w;
        public int h;
        public String target_url;
        public List<String> click_trackers;
        public List<String> imp_trackers;
        public Config config;

        public class Config {
            public int show_time;
            public int forced_display_time;
            public int is_closable;
            public int close_btn_position;
            public int close_btn_height;
            public int close_btn_width;
        }
    }


}
