package wooteco.subway.domain.fare.policy.distance;

import wooteco.subway.domain.fare.DistancePolicy;

public class TenToFiftyKMPolicy implements DistancePolicy {
    static final int BASE_FEE = 1250;
    private static final int OVER_TEN_DISTANCE = 10;
    private static final double OVER_TEN_RATE = 5;

    @Override
    public int getFare(int distance) {
        return BASE_FEE + (int) (Math.ceil((distance - OVER_TEN_DISTANCE) / OVER_TEN_RATE) * 100);
    }
}