package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.template.i18n.I18nIdentifierResolver;
import com.commercetools.sunrise.search.facetedsearch.BucketRangeFacetedSearchFormSettings;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.RangeStats;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.commercetools.sunrise.search.facetedsearch.RangeUtils.mapRangeToStats;
import static com.commercetools.sunrise.search.facetedsearch.RangeUtils.optionToFacetRange;

@RequestScoped
public class BucketRangeFacetSelectorViewModelFactory extends AbstractFacetSelectorViewModelFactory<BucketRangeFacetedSearchFormSettings<?>, RangeFacetResult> {

    private final Http.Request httpRequest;
    private final BucketRangeFacetOptionViewModelFactory bucketRangeFacetOptionViewModelFactory;

    @Inject
    public BucketRangeFacetSelectorViewModelFactory(final I18nIdentifierResolver i18nIdentifierResolver, final Http.Request httpRequest,
                                                    final BucketRangeFacetOptionViewModelFactory bucketRangeFacetOptionViewModelFactory) {
        super(i18nIdentifierResolver);
        this.httpRequest = httpRequest;
        this.bucketRangeFacetOptionViewModelFactory = bucketRangeFacetOptionViewModelFactory;
    }

    protected final Http.Request getHttpRequest() {
        return httpRequest;
    }

    protected final BucketRangeFacetOptionViewModelFactory getBucketRangeFacetOptionViewModelFactory() {
        return bucketRangeFacetOptionViewModelFactory;
    }

    @Override
    protected FacetViewModel newViewModelInstance(final BucketRangeFacetedSearchFormSettings<?> settings, final RangeFacetResult facetResult) {
        return super.newViewModelInstance(settings, facetResult);
    }

    @Override
    public final FacetViewModel create(final BucketRangeFacetedSearchFormSettings<?> settings, final RangeFacetResult facetResult) {
        return super.create(settings, facetResult);
    }

    @Override
    protected final void initialize(final FacetViewModel viewModel, final BucketRangeFacetedSearchFormSettings<?> settings, final RangeFacetResult facetResult) {
        super.initialize(viewModel, settings, facetResult);
    }

    @Override
    protected void fillLimitedOptions(final FacetViewModel viewModel, final BucketRangeFacetedSearchFormSettings<?> settings, final RangeFacetResult facetResult) {
        final List<String> selectedValues = settings.getAllSelectedFieldValues(httpRequest);
        final Map<FacetRange<String>, RangeStats> rangeToStatsMap = mapRangeToStats(facetResult);
        final List<FacetOptionViewModel> options = new ArrayList<>();
        settings.getOptions()
                .forEach(option -> optionToFacetRange(option)
                        .map(rangeToStatsMap::get)
                        .filter(Objects::nonNull)
                        .ifPresent(rangeStats -> options.add(bucketRangeFacetOptionViewModelFactory.create(option, rangeStats, selectedValues))));
        viewModel.setLimitedOptions(options);
    }
}
