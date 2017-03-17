package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.template.i18n.I18nIdentifierResolver;
import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettingsWithOptions;

public abstract class AbstractMultiOptionFacetViewModelFactory<M extends MultiOptionFacetViewModel, T extends FacetedSearchFormSettingsWithOptions<?>, F> extends AbstractFacetViewModelFactory<M, T, F> {

    protected AbstractMultiOptionFacetViewModelFactory(final I18nIdentifierResolver i18nIdentifierResolver) {
        super(i18nIdentifierResolver);
    }

    @Override
    protected void initialize(final M viewModel, final T settings, final F facetResult) {
        super.initialize(viewModel, settings, facetResult);
        fillMultiSelect(viewModel, settings, facetResult);
        fillMatchingAll(viewModel, settings, facetResult);
        fillLimitedOptions(viewModel, settings, facetResult);
    }

    protected void fillMultiSelect(final M viewModel, final T settings, final F facetResult) {
        viewModel.setMultiSelect(settings.isMultiSelect());
    }

    protected void fillMatchingAll(final M viewModel, final T settings, final F facetResult) {
        viewModel.setMatchingAll(settings.isMatchingAll());
    }

    protected abstract void fillLimitedOptions(final M viewModel, final T settings, final F facetResult);
}
