package wooteco.subway.domain.fare;

import java.util.List;
import wooteco.subway.domain.element.Line;
import wooteco.subway.domain.fare.policy.FarePolicy;
import wooteco.subway.domain.fare.policy.age.AgeDiscountPolicyGenerator;
import wooteco.subway.domain.fare.policy.distance.BasePolicy;
import wooteco.subway.domain.fare.policy.distance.DistancePolicyGenerator;
import wooteco.subway.domain.fare.policy.line.LineExtraFeePolicy;

public class PolicyFactory {
    public static BasePolicy createBase(int distance) {
        return DistancePolicyGenerator.of(distance);
    }

    public static FarePolicy createAgeDiscount(int age) {
        return AgeDiscountPolicyGenerator.of(age);
    }

    public static FarePolicy createLineFee(List<Line> lines) {
        return new LineExtraFeePolicy(lines);
    }
}
