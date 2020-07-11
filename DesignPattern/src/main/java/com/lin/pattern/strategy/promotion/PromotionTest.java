package com.lin.pattern.strategy.promotion;

import com.sun.deploy.util.StringUtils;

public class PromotionTest {

//    public static void main(String[] args) {
//        PromotionActivity activity618 = new PromotionActivity(new CouponStrategy());
//        activity618.excute();
//
//        PromotionActivity activity1111 = new PromotionActivity(new CashbackStrategy());
//        activity1111.excute();
//    }

//    public static void main(String[] args) {
//        PromotionActivity promotionActivity = null;
//
//        String promotion = "COUPON";
//
//        if ("COUPON".equals(promotion)) {
//            promotionActivity = new PromotionActivity(new CouponStrategy());
//        } else if ("CASHBACK".equals(promotion)) {
//            promotionActivity = new PromotionActivity(new CashbackStrategy());
//        }
//
//        promotionActivity.excute();
//
//    }

    public static void main(String[] args) {
        String promotionKey = "COUPON";
        PromotionActivity promotionActivity = new PromotionActivity(PromotionStrategyFactory.getPromotionStrategy(promotionKey));
        promotionActivity.excute();
    }
}
