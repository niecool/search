
package com.nie.lucene;

import com.nie.lucene.product.search.ProductSearcher;
import org.junit.Test;

public class ProductSearcherTest {

    @Test
    public void testSearcher(){
        ProductSearcher productSearcher = new ProductSearcher();
        productSearcher.searchIndex();
    }
}
