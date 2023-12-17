package com.golden_raspberry_awards.api.application.core.domain;

import java.util.List;

public class MaxMinResponse {
    private List<RangeOfWinnersResponse> max;
    private List<RangeOfWinnersResponse> min;

    public MaxMinResponse(){};

    public MaxMinResponse(List<RangeOfWinnersResponse> max, List<RangeOfWinnersResponse> min) {
        this.max = max;
        this.min = min;
    }

    public List<RangeOfWinnersResponse> getMax() {
        return max;
    }

    public void setMax(List<RangeOfWinnersResponse> max) {
        this.max = max;
    }

    public List<RangeOfWinnersResponse> getMin() {
        return min;
    }

    public void setMin(List<RangeOfWinnersResponse> min) {
        this.min = min;
    }
}
