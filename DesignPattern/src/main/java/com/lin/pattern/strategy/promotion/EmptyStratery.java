package com.lin.pattern.strategy.promotion;

public class EmptyStratery implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("无促销活动");
    }
}
