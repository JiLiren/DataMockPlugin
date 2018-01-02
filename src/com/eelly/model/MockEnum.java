package com.eelly.model;

import org.apache.http.util.TextUtils;

public enum MockEnum {

    /**
     * 随机名字
     * */
    Name {
        @Override
        public String getId() {
            return "0";
        }
        @Override
        public String getName() {
            return "randomName";
        }
        @Override
        public String getMsg() {
            return "名字";
        }
    },
    /**
     * 随机时间戳
     * */
    TimeStamp {
        @Override
        public String getId() {
            return "1";
        }
        @Override
        public String getName() {
            return "randomTimeStamp";
        }
        @Override
        public String getMsg() {
            return "时间戳";
        }
    },
    /**
     * 随机性别
     * */
    Sex {
        @Override
        public String getId() {
            return "2";
        }
        @Override
        public String getName() {
            return "randomSex";
        }
        @Override
        public String getMsg() {
            return "性别";
        }
    },
    /**
     * 随机地址
     * */
    Address {
        @Override
        public String getId() {
            return "3";
        }
        @Override
        public String getName() {
            return "randomAddress";
        }
        @Override
        public String getMsg() {
            return "地址";
        }
    },
    /**
     * 随机头像
     * */
    Portrait {
        @Override
        public String getId() {
            return "4";
        }
        @Override
        public String getName() {
            return "randomPortrait";
        }
        @Override
        public String getMsg() {
            return "头像";
        }
    },
    /**
     * 随机文本内容
     * */
    Content {
        @Override
        public String getId() {
            return "5";
        }
        @Override
        public String getName() {
            return "randomContent";
        }
        @Override
        public String getMsg() {
            return "文本内容";
        }
    },
    /**
     * 随机电话
     * */
    Moblie {
        @Override
        public String getId() {
            return "6";
        }
        @Override
        public String getName() {
            return "randomMoblie";
        }
        @Override
        public String getMsg() {
            return "电话";
        }
    },
    /**
     * 随机店铺名称
     * */
    StoreName {
        @Override
        public String getId() {
            return "7";
        }
        @Override
        public String getName() {
            return "randomStoreName";
        }
        @Override
        public String getMsg() {
            return "店铺名";
        }
    },
    /**
     * 随机公司名称
     * */
    CompanyName {
        @Override
        public String getId() {
            return "8";
        }
        @Override
        public String getName() {
            return "randomCompanyName";
        }
        @Override
        public String getMsg() {
            return "公司名";
        }
    },
    /**
     * 随机店铺或者公司名称
     * */
    StoreOrCompanyName  {
        @Override
        public String getId() {
            return "9";
        }
        @Override
        public String getName() {
            return "randomStoreOrCompanyName";
        }
        @Override
        public String getMsg() {
            return "店铺或者公司名称";
        }
    },
    /**
     * 任意类型
     * */
    Any  {
        @Override
        public String getId() {
            return "10";
        }
        @Override
        public String getName() {
            return "randomAny";
        }
        @Override
        public String getMsg() {
            return "任意";
        }
    },
    /**
     * 生成图片数组
     * */
    Portraits  {
        @Override
        public String getId() {
            return "11";
        }
        @Override
        public String getName() {
            return "randomPortraits";
        }
        @Override
        public String getMsg() {
            return "图片数组";
        }
    },
    /**
     * 随机数字
     * */
    Int   {
        @Override
        public String getId() {
            return "12";
        }
        @Override
        public String getName() {
            return "randomInt";
        }
        @Override
        public String getMsg() {
            return "数字";
        }
    };
    public abstract String getId();
    public abstract String getName();
    public abstract String getMsg();

    /**
     * 判断模拟类型是否有效
     * */
    public static boolean checkMock(String mock){
        if (TextUtils.isEmpty(mock)){
            //如果这里返回false的话，不小心点到输入总会弹出框来比较烦人
            return true;
        }
        int key=-1;
        try {
          key = Integer.parseInt(mock.trim());
        }catch (Exception e){
            return false;
        }
        return 0 <= key && key <= 12;
    }

    public static String getNameById(String id){
        for (MockEnum mockEnum : MockEnum.values()) {
            if (id.equals(mockEnum.getId())) {
                return mockEnum.getName();
            }
        }
        return "";
    }

    public static String getLabel(){
        //有标签是不可以用builder的
//        StringBuilder injection = new StringBuilder();
        String result = "";
        result += "<html>";
        int i=0;
        for (MockEnum mockEnum : MockEnum.values()) {
            result += mockEnum.getMsg()+"-";
            result += mockEnum.getId()+"; ";
            if (i == 5 || i == 9){
                result += "<br>";
            }
            i ++;
        }
        result += "</html>";
        return result;
    }
}
