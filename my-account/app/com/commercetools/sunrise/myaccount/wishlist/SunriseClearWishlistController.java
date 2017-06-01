package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.SunriseContentFormController;
import com.commercetools.sunrise.framework.controllers.WithExecutionFlow;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import io.sphere.sdk.client.ClientErrorException;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * This controller is used to view the current wishlist.
 */
public abstract class SunriseClearWishlistController extends SunriseContentFormController
        implements WithExecutionFlow<ShoppingList, ShoppingList>, WithRequiredWishlist {
    private final WishlistFinderBySession wishlistFinder;
    private final ClearWishlistControllerAction controllerAction;

    @Inject
    protected SunriseClearWishlistController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                             final WishlistFinderBySession wishlistFinder,
                                             final ClearWishlistControllerAction controllerAction) {
        super(contentRenderer, formFactory);
        this.wishlistFinder = wishlistFinder;
        this.controllerAction = controllerAction;
    }

    @EnableHooks
    @SunriseRoute(WishlistReverseRouter.CLEAR_WISHLIST_PROCESS)
    public CompletionStage<Result> process(final String languageTag) {
        return requireWishlist(this::processRequest);
    }

    @Override
    public CompletionStage<ShoppingList> executeAction(final ShoppingList wishlist) {
        return controllerAction.apply(wishlist);
    }

    public abstract CompletionStage<Result> handleSuccessfulAction(final ShoppingList output);

    @Override
    public WishlistFinder getWishlistFinder() {
        return wishlistFinder;
    }

    @Override
    public CompletionStage<Result> handleClientErrorFailedAction(final ShoppingList input, final ClientErrorException clientErrorException) {
        // No CTP call involved, this should never be called
        return handleGeneralFailedAction(clientErrorException);
    }
}
