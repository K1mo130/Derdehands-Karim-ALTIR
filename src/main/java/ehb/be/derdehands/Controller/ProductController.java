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

    @ModelAttribute("savingPersoon")
    public Persoon persoonForForm() {
        return new Persoon();
    }

    @GetMapping("/new")
    public String showNewProduct(ModelMap modelMap) {
        return "new";
    }

    @PostMapping("/index")
    public String insertNewProductAndNewPersoon(@Valid @ModelAttribute("savingProduct") Product product,
                                                BindingResult productBindingResult,
                                                @Valid @ModelAttribute("savingPersoon") Persoon persoon,
                                                BindingResult persoonBindingResult) {
        if (productBindingResult.hasErrors() || persoonBindingResult.hasErrors()) {
            return "/new";
        }

        Optional<Persoon> existingPersoon = mPersoonDao.findByEmail(persoon.getEmail());

        if (existingPersoon.isPresent()) {
            product.setPersoon(existingPersoon.get());
        } else {
            mPersoonDao.save(persoon);
            product.setPersoon(persoon);
        }

        mProductDao.save(product);

        return "redirect:/index";
    }

    @GetMapping("/details/{id}")
    public String showProductEnPersoonDetails(@PathVariable(value = "id") int id,
                                     ModelMap modelMap) {
        Optional<Persoon> persoonOptional = mPersoonDao.findById(id);
        Optional<Product> productOptional = mProductDao.findById(id);

        if (persoonOptional.isPresent() && productOptional.isPresent()) {
            modelMap.addAttribute("persoon", persoonOptional.get());
            modelMap.addAttribute("product", productOptional.get());
            return "details";
        }

        return "redirect:/index";
    }

}
