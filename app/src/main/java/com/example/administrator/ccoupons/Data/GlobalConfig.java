package com.example.administrator.ccoupons.Data;

import com.example.administrator.ccoupons.R;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class GlobalConfig {

    static final String dormBase = "http://10.132.55.96:8000";
    static final String localBase = "http://192.168.206.11:8000";
    static final String teamBase = "http://192.168.207.222:8000";
    static final String serverBase = "http://119.23.72.43";
    public static final String base_URL = teamBase;
    public static final String login_URL = "/loginForAndroid";
    public static final String register_URL = "/signUpForAndroid";
    public static final String requestMsg_URL = "/sendMessage";
    public static final String requestSearch_URL = "/searchForAndroid";
    public static final String requestPreSearch_URL = "/preSearch";//预搜索
    public static final String requestDetail_URL = "/returnInformation";
    public static final String purchase_URL = "/buyCoupon";
    public static final String resetPass_URL = "/updatePassword";
    public static final String requestBoughtList_URL = "/getBoughtList"; //已购买
    public static final String requestFollowList_URL = "/getLikeList";//已关注
    public static final String requestSoldList_URL = "/getSoldList";//已卖出
    public static final String updateUserInformation_URL = "/updateUserInformation";//修改个人资料
    public static final String updatePhoneNumber_URL = "/updatePhonenumOrEmail";

    public static final String postFollow_URL = "/likeCoupon";//关注
    public static final String postUnFollow_URL = "/dislikeCoupon";//取消关注
    public static final String postAvatar_URL = "/updateAvatar";//上传头像

    public static final String postBanner_URL = "/getBanner";//获取轮播图
    public static final String postRecommend_URL = "/homepageCoupon";//主页推荐


    public static final String postChangeCouponState_URL = "/changeCouponState";//换优惠券状态

    public static final String postGetEvaluation_URL = "/getValue";//获取估值

    public static final String postAddCoupon_URL = "/addCoupon";//添加优惠券

    public static final String requestOwnList_URL = "/getOwnList";//获取我的优惠券

    public static final String requestCatRecommend_URL = "/searchByCategory";//分类推荐
    public static final String requestCatSearch_URL = "/searchInCertainCategory";//按分类搜索
    public static final String requestCatPreSearch_URL = "/preSearchInCategory";//分类下预搜索

    public static final String requestSellerInfo_URL = "/sellerInformation";//获取卖家信息


    public static class Categories {
        public static String[] nameList = "生活百货 美妆装饰 文娱体育 家具家居 电子产品 服装装饰 旅行住宿 饮食保健".split(" ");
        public static int[] covers = {R.drawable.category_daily, R.drawable.category_decorate, R.drawable.category_sports, R.drawable.category_furnitures,
                R.drawable.category_electronics, R.drawable.category_cloths, R.drawable.category_travel, R.drawable.category_food};
    }

    //用户信息
    public static class User {
        public static String username = "用户名";
        public static boolean sex = true;//TODO:要改成gender那样的
        public static int age = 18;
        public static int portraitId = R.drawable.testportrait;
        public static int UB = 100;
    }

    public static class MessageClasses {
        public static int[] strings = {R.string.coupon_bought_title, R.string.coupon_abouttoexpire_title,
                R.string.coupon_expired_title, R.string.coupon_followed_abouttoexpire_title, R.string.coupon_mine_aboutto_expire_title,
                R.string.coupon_system_title};
    }

    //无I O U V
    public static class Cities {

        public static final String[] popCityList = ("上海 北京 杭州 广州 成都 苏州" +
                " 深圳 南京 天津 重庆 厦门 武汉").split(" ");
        public static final String[] cityList = {
                "A", "阿坝", "阿拉善", "阿里", "安康", "安庆", "鞍山", "安顺", "安阳", "澳门", "B", "北京", "白银",
                "保定", "宝鸡", "保山", "包头", "巴中", "北海", "蚌埠", "本溪", "毕节", "滨州", "百色", "亳州",
                "C", "重庆", "成都", "长沙", "长春", "沧州", "常德", "昌都", "长治", "常州", "巢湖", "潮州", "承德",
                "郴州", "赤峰", "池州", "崇左", "楚雄", "滁州", "朝阳", "D", "大连", "东莞", "大理", "丹东", "大庆",
                "大同", "大兴安岭", "德宏", "德阳", "德州", "定西", "迪庆", "东营", "E", "鄂尔多斯", "恩施", "鄂州",
                "F", "福州", "防城港", "佛山", "抚顺", "抚州", "阜新", "阜阳", "G", "广州", "桂林", "贵阳", "甘南",
                "赣州", "甘孜", "广安", "广元", "贵港", "果洛", "H", "杭州", "哈尔滨", "合肥", "海口", "呼和浩特",
                "海北", "海东", "海南", "海西", "邯郸", "汉中", "鹤壁", "河池", "鹤岗", "黑河", "衡水", "衡阳",
                "河源", "贺州", "红河", "淮安", "淮北", "怀化", "淮南", "黄冈", "黄南", "黄山", "黄石", "惠州",
                "葫芦岛", "呼伦贝尔", "湖州", "菏泽", "J", "济南", "佳木斯", "吉安", "江门", "焦作", "嘉兴", "嘉峪关",
                "揭阳", "吉林", "金昌", "晋城", "景德镇", "荆门", "荆州", "金华", "济宁", "晋中", "锦州", "九江",
                "酒泉", "K", "昆明", "开封", "L", "兰州", "拉萨", "来宾", "莱芜", "廊坊", "乐山", "凉山", "连云港",
                "聊城", "辽阳", "辽源", "丽江", "临沧", "临汾", "临夏", "临沂", "林芝", "丽水", "六安", "六盘水",
                "柳州", "陇南", "龙岩", "娄底", "漯河", "洛阳", "泸州", "吕梁", "M", "马鞍山", "茂名", "眉山", "梅州",
                "绵阳", "牡丹江", "N", "南京", "南昌", "南宁", "宁波", "南充", "南平", "南通", "南阳", "那曲", "内江",
                "宁德", "怒江", "P", "盘锦", "攀枝花", "平顶山", "平凉", "萍乡", "莆田", "濮阳", "Q", "青岛", "黔东南",
                "黔南", "黔西南", "庆阳", "清远", "秦皇岛", "钦州", "齐齐哈尔", "泉州", "曲靖", "衢州", "R", "日喀则",
                "日照", "S", "上海", "深圳", "苏州", "沈阳", "石家庄", "三门峡", "三明", "三亚", "商洛", "商丘", "上饶",
                "山南", "汕头", "汕尾", "韶关", "绍兴", "邵阳", "十堰", "朔州", "四平", "绥化", "遂宁", "随州", "宿迁",
                "宿州", "T", "天津", "太原", "泰安", "泰州", "台州", "唐山", "天水", "铁岭", "铜川", "通化", "通辽",
                "铜陵", "铜仁", "台湾", "W", "武汉", "乌鲁木齐", "无锡", "威海", "潍坊", "文山", "温州", "乌海", "芜湖",
                "乌兰察布", "武威", "梧州", "X", "厦门", "西安", "西宁", "襄樊", "湘潭", "湘西", "咸宁", "咸阳", "孝感",
                "邢台", "新乡", "信阳", "新余", "忻州", "西双版纳", "宣城", "许昌", "徐州", "香港", "锡林郭勒", "兴安",
                "Y", "银川", "雅安", "延安", "延边", "盐城", "阳江", "阳泉", "扬州", "烟台", "宜宾", "宜昌", "宜春",
                "营口", "益阳", "永州", "岳阳", "榆林", "运城", "云浮", "玉树", "玉溪", "玉林", "Z", "杂多县", "赞皇县",
                "枣强县", "枣阳市", "枣庄", "泽库县", "增城市", "曾都区", "泽普县", "泽州县", "札达县", "扎赉特旗",
                "扎兰屯市", "扎鲁特旗", "扎囊县", "张北县", "张店区", "章贡区", "张家港", "张家界", "张家口", "漳平市",
                "漳浦县", "章丘市", "樟树市", "张湾区", "彰武县", "漳县", "张掖", "漳州", "长子县", "湛河区", "湛江",
                "站前区", "沾益县", "诏安县", "召陵区", "昭平县", "肇庆", "昭通", "赵县", "昭阳区", "招远市", "肇源县",
                "肇州县", "柞水县", "柘城县", "浙江", "镇安县", "振安区", "镇巴县", "正安县", "正定县", "正定新区",
                "正蓝旗", "正宁县", "蒸湘区", "正镶白旗", "正阳县", "郑州", "镇海区", "镇江", "浈江区", "镇康县",
                "镇赉县", "镇平县", "振兴区", "镇雄县", "镇原县", "志丹县", "治多县", "芝罘区", "枝江市",
                "芷江侗族自治县", "织金县", "中方县", "中江县", "钟楼区", "中牟县", "中宁县", "中山", "中山区",
                "钟山区", "钟山县", "中卫", "钟祥市", "中阳县", "中原区", "周村区", "周口", "周宁县", "舟曲县", "舟山",
                "周至县", "庄河市", "诸城市", "珠海", "珠晖区", "诸暨市", "驻马店", "准格尔旗", "涿鹿县", "卓尼",
                "涿州市", "卓资县", "珠山区", "竹山县", "竹溪县", "株洲", "株洲县", "淄博", "子长县", "淄川区", "自贡",
                "秭归县", "紫金县", "自流井区", "资溪县", "资兴市", "资阳"};
    }
}
