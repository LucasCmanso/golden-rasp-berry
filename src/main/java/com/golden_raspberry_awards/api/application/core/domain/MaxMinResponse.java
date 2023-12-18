package com.golden_raspberry_awards.api.application.core.domain;

import java.util.List;

public class MaxMinResponse {
    private List<RangeOfWinnersResponse> min;
    private List<RangeOfWinnersResponse> max;

    public MaxMinResponse(){};

    public MaxMinResponse(List<RangeOfWinnersResponse> min, List<RangeOfWinnersResponse> max) {
        this.min = min;
        this.max = max;
    }


    public List<RangeOfWinnersResponse> getMin() {
        return min;
    }

    public void setMin(List<RangeOfWinnersResponse> min) {
        this.min = min;
    }
    public List<RangeOfWinnersResponse> getMax() {
        return max;
    }

    public void setMax(List<RangeOfWinnersResponse> max) {
        this.max = max;
    }
}
