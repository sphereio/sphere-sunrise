package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.ControllerAction;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.RemoveWishlistLineItemFormData;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

@ImplementedBy(DefaultRemoveFromWishlistControllerAction.class)
public interface RemoveFromWishlistControllerAction extends ControllerAction, BiFunction<ShoppingList, RemoveWishlistLineItemFormData, CompletionStage<ShoppingList>> {
    @Override
    CompletionStage<ShoppingList> apply(ShoppingList shoppingList, RemoveWishlistLineItemFormData formData);
}
