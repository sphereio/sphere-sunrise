package com.commercetools.sunrise.sessions.country;

import com.commercetools.sunrise.sessions.DataFromResourceStoringOperations;
import com.commercetools.sunrise.sessions.SessionStrategy;
import com.neovisionaries.i18n.CountryCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * Keeps the current country in session.
 */
@Singleton
public class DefaultCountryInSession extends DataFromResourceStoringOperations<CountryCode> implements CountryInSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryInSession.class);

    private final String countryCookieName;
    private final SessionStrategy session;

    @Inject
    public DefaultCountryInSession(final SessionStrategy session, final Configuration configuration) {
        this.countryCookieName = Optional.ofNullable(configuration.getString("session.country"))
                .map(cookieName -> {
                    LOGGER.warn("session.country is deprecated, use sunrise.localization.countryCookieName instead");
                    return cookieName;
                }).orElseGet(() -> configuration.getString("sunrise.localization.countryCookieName"));
        this.session = session;
    }

    @Override
    protected final Logger getLogger() {
        return LOGGER;
    }

    /**
     * @return cookie name to store the country
     * @deprecated use {@link #getCountryCookieName()} instead
     */
    @Deprecated
    protected final String getCountrySessionKey() {
        return getCountryCookieName();
    }

    protected final String getCountryCookieName() {
        return countryCookieName;
    }

    protected final SessionStrategy getSession() {
        return session;
    }

    @Override
    public Optional<CountryCode> findCountry() {
        return session.findValueByKey(countryCookieName).map(CountryCode::getByCode);
    }

    @Override
    public void store(@Nullable final CountryCode countryCode) {
        super.store(countryCode);
    }

    @Override
    public void remove() {
        super.remove();
    }

    @Override
    protected void storeAssociatedData(final CountryCode countryCode) {
        session.overwriteValueByKey(countryCookieName, countryCode.getAlpha2());
    }

    @Override
    protected void removeAssociatedData() {
        session.removeValueByKey(countryCookieName);
    }
}
