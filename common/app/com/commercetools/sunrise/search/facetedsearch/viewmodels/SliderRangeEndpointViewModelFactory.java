package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.ViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.SliderRangeFacetedSearchFormSettings;
import io.sphere.sdk.search.model.SimpleRangeStats;
import play.mvc.Http;

import javax.inject.Inject;

public class SliderRangeEndpointViewModelFactory extends ViewModelFactory {

    private final Http.Request httpRequest;

    @Inject
    public SliderRangeEndpointViewModelFactory(final Http.Request httpRequest) {
        this.httpRequest = httpRequest;
    }

    protected SliderRangeEndpointViewModel newViewModelInstance(final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        return new SliderRangeEndpointViewModel();
    }

    public final SliderRangeEndpointViewModel create(final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        final SliderRangeEndpointViewModel viewModel = newViewModelInstance(settings, rangeStats);
        initialize(viewModel, settings, rangeStats);
        return viewModel;
    }

    protected final void initialize(final SliderRangeEndpointViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        fillLowerName(viewModel, settings, rangeStats);
        fillUpperName(viewModel, settings, rangeStats);
        fillLowerValue(viewModel, settings, rangeStats);
        fillUpperValue(viewModel, settings, rangeStats);
        fillLowerEndpoint(viewModel, settings, rangeStats);
        fillUpperEndpoint(viewModel, settings, rangeStats);
        fillCount(viewModel, settings, rangeStats);
    }

    protected void fillLowerName(final SliderRangeEndpointViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        viewModel.setLowerName(settings.getLowerEndpointSettings().getFieldName());
    }

    protected void fillUpperName(final SliderRangeEndpointViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        viewModel.setUpperName(settings.getUpperEndpointSettings().getFieldName());
    }

    protected void fillLowerValue(final SliderRangeEndpointViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        viewModel.setLowerValue(settings.getLowerEndpointSettings().getSelectedValue(httpRequest));
    }

    protected void fillUpperValue(final SliderRangeEndpointViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        viewModel.setUpperValue(settings.getUpperEndpointSettings().getSelectedValue(httpRequest));
    }

    protected void fillLowerEndpoint(final SliderRangeEndpointViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        viewModel.setLowerEndpoint(rangeStats.getMin());
    }

    protected void fillUpperEndpoint(final SliderRangeEndpointViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        viewModel.setUpperEndpoint(rangeStats.getMax());
    }

    protected void fillCount(final SliderRangeEndpointViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        viewModel.setCount(rangeStats.getCount());
    }
}
