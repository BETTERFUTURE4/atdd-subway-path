package wooteco.subway.domain.fare.policy.age;

import wooteco.subway.domain.fare.AgeDiscountPolicy;

public class AdultDiscountPolicy implements AgeDiscountPolicy {
    public AdultDiscountPolicy() {
    }

    @Override
    public double calculate(double baseFare) {
        return baseFare;
    }
}