package com.lin.pattern.strategy.promotion;

public class PromotionActivity {

    PromotionStrategy promotionStrategy;
    public PromotionActivity(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public void excute() {
        this.promotionStrategy.doPromotion();
    }

}
