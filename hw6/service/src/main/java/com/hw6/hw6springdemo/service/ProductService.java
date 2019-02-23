package com.hw6.hw6springdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hw6.hw6springdemo.domain.Products;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryBuilder;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryResult;

@Service public class ProductService{  
	public List<Products> getProductsOdata(String destinationName) throws ODataException {
        ODataQueryResult  result = ODataQueryBuilder.withEntity("/V2/OData/OData.svc/","Products").
                select("ID","Name","Description").build().execute(destinationName);
        List<Map<String,Object>> listMap =  result.asListOfMaps();
        return  getProductList(listMap);
	}
	public List<Products>  getProductList ( List<Map<String,Object>> listMap) {
    List <Products> productsList = new ArrayList<>();
    listMap.forEach(item->{
        Products prod = new Products();
        prod.setId(Integer.parseInt(item.get("ID").toString()));
        prod.setName(item.get("Name").toString());
        prod.setDescr(item.get("Description").toString());
        productsList.add(prod);
    });
    return productsList;
}
} 