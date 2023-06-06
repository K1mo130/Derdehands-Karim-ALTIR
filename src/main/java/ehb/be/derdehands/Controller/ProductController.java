package ehb.be.derdehands.Controller;

import ehb.be.derdehands.dao.PersoonDao;
import ehb.be.derdehands.dao.ProductDao;
import ehb.be.derdehands.entiteit.Persoon;
import ehb.be.derdehands.entiteit.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ProductController {
    private ProductDao mProductDao;
    private PersoonDao mPersoonDao;

    @Autowired
    public ProductController(ProductDao mProductDao, PersoonDao mPersoonDao) {
        this.mProductDao = mProductDao;
        this.mPersoonDao = mPersoonDao;
    }

    @GetMapping({"/", "/index"})
    public String showIndex(ModelMap modelMap) {
        modelMap.put("producten", mProductDao.findAll());
        return "index";
    }

    @GetMapping("/about")
    public String showAbout(ModelMap modelMap) {
        return "about";
    }

    @ModelAttribute("savingProduct")
    public Product productForForm() {
        return new Product();
    }

    @GetMapping("/new")
    public String showNewProduct(ModelMap modelMap) {
        return "new";
    }

    @PostMapping("/index")
    public String insertNewProduct(@Valid @ModelAttribute("savingProduct") Product product,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/new";
        }

        mProductDao.save(product);
        return "redirect:/index";
    }

    @GetMapping("/details/{id}")
    public String showProductDetails(@PathVariable(value = "id") int id,
                                     ModelMap modelMap) {
        Optional<Persoon> persoon = mPersoonDao.findById(id);
        Optional<Product> product = mProductDao.findById(id);

        if (persoon.isPresent()) {
            modelMap.addAttribute("persoon", persoon.get());
            modelMap.addAttribute("product", product.get());
            return "details";
        }

        return "redirect:/index";
    }

}
