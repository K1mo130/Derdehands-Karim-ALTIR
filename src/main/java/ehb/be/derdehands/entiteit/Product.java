package ehb.be.derdehands.entiteit;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String categorie;

    @NotBlank
    private String discription;

    @DecimalMin(value = "0.0")
    private double price;

    @ManyToOne
    @JoinColumn(name = "Persoon_Id", nullable = false)
    private Persoon persoon;

    public Product() {
    }

    public Product(int id, String name, String categorie, String discription, double price, Persoon persoon) {
        this.id = id;
        this.name = name;
        this.categorie = categorie;
        this.discription = discription;
        this.price = price;
        this.persoon = persoon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }
}


