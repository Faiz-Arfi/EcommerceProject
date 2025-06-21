package com.projects.ecommerce.Constants;

import java.util.Set;

public class SortConstants {
    public static final Set<String> PRODUCT_SORT_FIELDS = Set.of("productName", "productCategory", "brand", "productPrice");
    public static final Set<String> DEALS_SORT_FIELDS = Set.of( "dealName", "heading", "category");
    public static final Set<String> COUPON_SORT_FIELDS = Set.of("couponCode", "heading", "category");
}
