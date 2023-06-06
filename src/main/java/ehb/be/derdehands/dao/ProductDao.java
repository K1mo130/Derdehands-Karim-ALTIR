package ehb.be.derdehands.dao;

import ehb.be.derdehands.entiteit.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product, Integer> {

}
