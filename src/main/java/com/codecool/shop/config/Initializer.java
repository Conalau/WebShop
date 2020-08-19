package com.codecool.shop.config;

import com.codecool.shop.dao.DaoImplementations;
import com.codecool.shop.dao.DataStore;
import com.codecool.shop.model.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataStore dataStore = DataStore.getInstance();

        //use parameter to select data container:
        // "DaoImplementations.DATABASE" for database storage
        // "DaoImplementations.MEMORY" for memory storage
        dataStore.SetDaoImplementation(DaoImplementations.MEMORY);

        if(dataStore.getDaoImplementation() == DaoImplementations.DATABASE) {
            //use parameter to select database:
            // "main_connection.properties" to use codecoolshop database
            // "test_connection.properties" to use test database
            dataStore.SetDatabase("main_connection.properties");

            //[for testing] delete database before use
            dataStore.getDatabaseManager().initDatabase();
        }

        //setting up suppliers
        Supplier prada = new Supplier("Prada", "Luxury fashion house, specializing in leather handbags, travel " +
                "accessories, shoes, ready-to-wear, perfumes ");
        Supplier bulgari = new Supplier("Bulgari", "Luxury brand known for its jewellery, watches, fragrances, " +
                "accessories and leather goods");
        dataStore.supplierDao.add(prada);
        dataStore.supplierDao.add(bulgari);
/*
        //setting up categories
        Category ring = new Category("Ring", "Jewelry", "Circular band, often set with gems, for wearing as an " +
                "ornament");
        Category necklace = new Category("Necklace", "Jewelry", "String of stones, beads, jewels, or the like, for " +
                "wearing");
        Category earrings = new Category("Earrings", "Jewelry", "Ornaments worn as accessories");
        dataStore.categoryDao.add(ring);
        dataStore.categoryDao.add(necklace);
        dataStore.categoryDao.add(earrings);

        //setting up products
        Date currentDate = new Date();
        Product product1 = new Product("Prada Sapphire and diamonds Rings", 250 , "USD", currentDate, "24 karate " +
                "white gold rings with sapphire and diamonds", "pic1.jpg", ring, prada);
        Product product2 = new Product("Bulgari Ruby Earrings", 500, "USD", currentDate, "Ruby inlaid earrings in " +
                "genuine 925 silver. Red tourmaline jewelry. The earrings are plated with 18 carat white gold.",
                "pic2.jpg", earrings, bulgari);
        Product product3 = new Product("Prada Blue crystal Necklace", 350, "USD", currentDate, "Simple and " +
                "fashionable design. Material: crystal, resin and metal alloy. Pendant dimensions: 2x2cm. Chain length: 38 cm.", "pic3.jpg", necklace, prada);
        Product product4 = new Product("Prada Emerald and white gold Earrings", 1000, "USD", currentDate, "18 karat " +
                "gold plated earrings. Contains 4 emeralds and white zirconium 292.", "pic4.jpg", earrings, prada);
        Product product5 = new Product("Prada Tourmaline and diamond Rings", 1780, "USD", currentDate, "18 karat " +
                "white gold rings with tourmaline in pink and green shades, plated with diamonds.", "pic5.jpg", ring,
                prada);
        Product product6 = new Product("Bulgari Ruby and white sapphire Necklace", 500 , "USD", currentDate, "Silver " +
                "necklace with Chatham ruby and white sapphire; modern jewelry with silver chain included and Argentium silver.", "pic6.jpg", necklace, bulgari);
//        Product product7 = new Product("Citrine Silver Necklace", 600, "USD", currentDate, "Silver necklace with " +
//                "citrine amber; modern jewelry with silver chain included and Argentium silver.", "pic7.jpg", necklace, bulgari);
        Product product8 = new Product("Bulgari Amethyst Silver Earrings", 900, "USD", currentDate, "Amethyst silver " +
                "earrings; Modern jewelry with purple gemstone from 2001, with Argentium silver.", "pic8.jpg", earrings, bulgari);
        Product product9 = new Product("Prada Topaz Silver Earrings", 600, "USD", currentDate, "Silver earrings with " +
                "turquoise blue topaz; modern jewelry with ecological stone, with Argentium silver.", "pic9.jpg", earrings, prada);
        Product product10 = new Product("Bulgari Amber Necklace", 550, "USD", currentDate, "Authentic pendant with " +
                "natural citrine (not created in the laboratory), cubic zirconia. Genuine 925 silver necklace plated with gold.", "pic10.jpg", necklace, bulgari);
        dataStore.productDao.add(product1);
        dataStore.productDao.add(product2);
        dataStore.productDao.add(product3);
        dataStore.productDao.add(product4);
        dataStore.productDao.add(product5);
        dataStore.productDao.add(product6);
//        dataStore.productDao.add(product7);
        dataStore.productDao.add(product8);
        dataStore.productDao.add(product9);
        dataStore.productDao.add(product10);

        //setting up users
        Address address1 = new Address("Romania", "Bistrita", "420070", "str. Fericirii, nr. 14");
        Address address2 = new Address("Poland", "Warsav", "430070", "Happiness str., no. 15");
        User user1 = new User("Tudor Dan", "tudor_ist@gmail.com", "1234", "1234567890", address1, null, UserStatus.SIGNED);
        User user2 = new User("Pop Ion", "pop_ion@gmail.com", "2345", "0123456789", address2, address2, UserStatus.SIGNED);
        dataStore.userDao.add(user1);
        dataStore.userDao.add(user2);
 */
    }
}
