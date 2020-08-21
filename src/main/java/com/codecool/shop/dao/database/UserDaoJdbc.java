package com.codecool.shop.dao.database;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.Address;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoJdbc implements UserDao {
    private static UserDaoJdbc instance;
    private DatabaseManager databaseManager;

    private UserDaoJdbc() { }

    public static UserDaoJdbc getInstance() {
        if(instance == null) {
            instance = new UserDaoJdbc();
        }
        return instance;
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void add(User user) {
        String addressQuery = "INSERT INTO addresses (country, city, zipcode, home_address) " +
                "VALUES (?, ?, ?, ?) " +
                "RETURNING id";

        //insert billing if exists
        Address billing = user.getBilling();
        if(billing != null) {
            try (Connection conn = databaseManager.getConnection()) {
                // set all the prepared statement parameters
                PreparedStatement st = conn.prepareStatement(addressQuery);
                st.setString(1, billing.getCountry());
                st.setString(2, billing.getCity());
                st.setString(3, billing.getZipcode());
                st.setString(4, billing.getHomeAddress());

                // execute the prepared statement insert,
                // get id of inserted address, update billing
                st.execute();
                ResultSet rs = st.getResultSet();
                if (rs.next()) {
                    billing.setId(rs.getInt(1));
                }
                st.close();
            } catch (SQLException exception) {
                System.err.println("ERROR: Billing address add error => " + exception.getMessage());
            }
        }

        //insert shipping if exists
        //if shipping missing but billing present assume shipping = billing
        Address shipping = user.getShipping();
        if(shipping == null) {
            shipping = billing;
        }
        if(shipping != null) {
            try (Connection conn = databaseManager.getConnection()) {
                // set all the prepared statement parameters
                PreparedStatement st = conn.prepareStatement(addressQuery);
                st.setString(1, shipping.getCountry());
                st.setString(2, shipping.getCity());
                st.setString(3, shipping.getZipcode());
                st.setString(4, shipping.getHomeAddress());

                // execute the prepared statement insert,
                // get id of inserted address, update billing
                st.execute();
                ResultSet rs = st.getResultSet();
                if (rs.next()) {
                    shipping.setId(rs.getInt(1));
                }
                st.close();
            } catch (SQLException exception) {
                System.err.println("ERROR: Shipping address add error => " + exception.getMessage());
            }
        }

        String usersQuery = "INSERT INTO users (name, email, password, " +
                "phone_number, billing_id, shipping_id, user_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING id";

        //insert user
        try (Connection conn = databaseManager.getConnection()) {
            // set all the prepared statement parameters
            PreparedStatement st = conn.prepareStatement(usersQuery);
            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPassword());
            st.setString(4, user.getPhoneNumber());
            if(billing!=null) {
                st.setInt(5, billing.getId());
            } else {
                st.setInt(5, 0);
            }
            if(shipping!=null) {
                st.setInt(5, shipping.getId());
            } else {
                st.setInt(5, 0);
            }
            st.setString(7, user.getUserStatus().toString());

            // execute the prepared statement insert, get id of inserted user,
            // update parameter
            st.execute();
            ResultSet rs = st.getResultSet();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            st.close();
        } catch (SQLException exception) {
            System.err.println("ERROR: User add error => " + exception.getMessage());
        }
    }

    @Override
    public boolean isSignedUp(String email) {
        // TODO: 18.08.2020 user isSignedUp(email)
        return false;
    }

    @Override
    public User getAuthenticatedUser(String email, String password) {
        // TODO: 18.08.2020 user getAuthenticatedUser(email, pass)
        return null;
    }

    @Override
    public void remove(User user) {
        // TODO: 18.08.2020 remove(user)
    }

    @Override
    public void updateUser(User user, String name, String email, String password, String phone, Address billing, Address shipping) {
        // TODO: 18.08.2020 updateUser(user, ...)
    }

    @Override
    public void updateUserCart(User user, Cart cart) {
        // TODO: 18.08.2020 user updateUserCart(user, cart)
    }

    @Override
    public Cart getUserCart(User user) {
        // TODO: 18.08.2020 user getUserCart(user)
        return null;
    }
}
